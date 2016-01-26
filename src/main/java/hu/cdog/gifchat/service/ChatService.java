package hu.cdog.gifchat.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.websocket.EncodeException;
import javax.websocket.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hu.cdog.gifchat.data.dao.MessagesDao;
import hu.cdog.gifchat.model.dto.UserMessageDto;
import hu.cdog.gifchat.model.entities.UserMessage;
import hu.cdog.gifchat.model.giphy.GiphyImageContainer;
import hu.cdog.gifchat.service.azure.TranslatorService;
import hu.cdog.gifchat.service.giphy.GifGenerator;
import hu.cdog.gifchat.service.giphy.MessageTokinezer;
import hu.cdog.gifchat.service.giphy.strategies.LongestWordFirst;

@Stateless
public class ChatService {

	private static final Logger log = LoggerFactory.getLogger(ChatService.class);

	private static final String TRENDING_KW = "**trending**";
	private static final String RANDOM_KW = "**random**";

	@Inject
	MessagesDao messagesDao;

	@Inject
	GifGenerator gifGenerator;

	@Inject
	TranslatorService translatorService;

	@Inject
	ChatUsersService chatUsersService;

	public void newMessage(String message, String username) {
		if (message == null || message.isEmpty()) {
			return;
		}
		UserMessage newMessage = generateNewMessage(message, username);
		chatUsersService.updateUserDependentProperties(username);
		messagesDao.persist(newMessage);
		sendNewMessageToEveryone(newMessage);

	}

	public List<UserMessageDto> getLastMessages() {
		return map2Dto(reverse(messagesDao.getCurrentsMessages()));
	}

	public List<UserMessageDto> earlierThan(LocalDateTime currentTime) {
		List<UserMessage> earlierThan = messagesDao.earlierThan(currentTime);
		return map2Dto(reverse(earlierThan));
	}

	private void sendNewMessageToEveryone(UserMessage message) {
		UserMessageDto dto = new UserMessageDto(message);
		for (Session session : chatUsersService.getAllSessions()) {
			sendMessage(session, dto);
		}
	}

	private void sendMessage(Session s, UserMessageDto msg) {
		try {
			if (s != null && s.isOpen()) {
				s.getBasicRemote().sendObject(msg);
			} else {
				chatUsersService.removeUser(s);
			}
		} catch (IOException | EncodeException e) {
			log.error(e.getMessage(), e);
		}
	}

	private UserMessage generateNewMessage(String rawMessage, String username) {
		// translate to english
		String translatedMessage = null;
		try {
			translatedMessage = translatorService.translate(rawMessage);
		} catch (Exception ex) {
			log.warn(ex.getMessage());
			translatedMessage = rawMessage;
		}

		// tokenize the message
		MessageTokinezer possibleKeywords = new MessageTokinezer(translatedMessage, LongestWordFirst.get());
		GiphyImageContainer container = null;
		int iteration = 0;
		String keyword = null;

		while (container == null && iteration < 5) {
			String nextToken = possibleKeywords.getNextToken();
			try {
				// search for image based on keyword
				container = gifGenerator.searchGifForKeyword(nextToken);
			} catch (Exception e) {
				log.warn(e.getMessage(), e);
			}
			iteration++;
			// if found keyword save it and exit
			if (nextToken != null && container != null) {
				keyword = nextToken;
				log.debug("Found keyword '{}' for message '{}'", keyword, translatedMessage);
			} else {
				keyword = TRENDING_KW;
			}
		}
		// still nothing found pick a random gif
		if (container == null) {
			try {
				container = gifGenerator.pickRandomImage();
				keyword = RANDOM_KW;
				log.debug("Out of iteration choosing a random gif for message '{}'", translatedMessage);
			} catch (Exception e) {
				// TODO should add some random gif as a very last resort
				log.error(e.getMessage(), e);
			}
		}

		UserMessage gifMessage = new UserMessage(username, rawMessage, translatedMessage, keyword, container);
		return gifMessage;
	}

	private List<UserMessage> reverse(List<UserMessage> list) {
		Collections.reverse(list);
		return list;
	}

	private List<UserMessageDto> map2Dto(List<UserMessage> messages) {
		return messages.stream().map(l -> new UserMessageDto(l)).collect(Collectors.toList());
	}

}
