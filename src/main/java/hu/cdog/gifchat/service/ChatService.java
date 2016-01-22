package hu.cdog.gifchat.service;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.websocket.EncodeException;
import javax.websocket.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hu.cdog.gifchat.GifChatConstants;
import hu.cdog.gifchat.model.dto.UserMessageDto;
import hu.cdog.gifchat.model.entities.UserMessage;
import hu.cdog.gifchat.model.giphy.GifImageFormats;
import hu.cdog.gifchat.service.azure.TranslatorService;
import hu.cdog.gifchat.service.giphy.GifGenerator;
import hu.cdog.gifchat.service.giphy.MessageTokinezer;
import hu.cdog.gifchat.service.giphy.strategies.LongestWordFirst;

@Stateless
public class ChatService {

	private static final Logger log = LoggerFactory.getLogger(ChatService.class);

	private static final String TRENDING_KW = "**trending**";

	@Inject
	MemDbCache memDb;

	@Inject
	GifGenerator gifGenerator;

	@Inject
	TranslatorService translatorService;

	@Inject
	ChatUsersService chatUsersService;

	public List<UserMessageDto> getNewMessages(Long userTime) {
		LocalDateTime userTimeLd = LocalDateTime.ofInstant(Instant.ofEpochMilli(userTime), GifChatConstants.UTC_ZONE);
		List<UserMessage> filtered = memDb.getAll().stream().filter(l -> l.getSentTime().isAfter(userTimeLd))
				.collect(Collectors.toList());
		return map2Dto(filtered);
	}

	public void newMessage(String message, String username) {
		if (message == null || message.isEmpty()) {
			return;
		}
		UserMessage newMessage = generateAndSaveNewMessage(message, username);
		chatUsersService.updateUserDependentProperties(username);
		sendNewMessageToEveryone(newMessage);

	}

	public List<UserMessageDto> getLastMessages() {
		return map2Dto(memDb.getCurrents());
	}

	public List<UserMessageDto> earlierThan(LocalDateTime currentTime) {
		return map2Dto(memDb.earlierThan(currentTime));
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

	private UserMessage generateAndSaveNewMessage(String rawMessage, String username) {
		// translate to english
		String translatedMessage = translatorService.translate(rawMessage);

		// tokenize the message
		MessageTokinezer possibleKeywords = new MessageTokinezer(translatedMessage, LongestWordFirst.get());
		GifImageFormats gifFormats = null;
		int iteration = 0;
		String keyword = null;

		while (gifFormats == null && iteration < 5) {
			String nextToken = possibleKeywords.getNextToken();
			try {
				// search for image based on keyword
				gifFormats = gifGenerator.searchGifForKeyword(nextToken,
						memDb.getLastGifs(GifChatConstants.SEARCH_WITHIN_GIF_IMAGES_LIMIT));
			} catch (Exception e) {
				log.warn(e.getMessage(), e);
			}
			iteration++;
			// if found keyword save it and exit
			if (nextToken != null && gifFormats != null) {
				keyword = nextToken;
				log.debug("Found keyword '{}' for message '{}'", keyword, translatedMessage);
			} else {
				keyword = TRENDING_KW;
			}
		}
		// still nothing found pick a random gif
		if (gifFormats == null) {
			try {
				gifFormats = gifGenerator.pickRandomImage();
				keyword = TRENDING_KW;
				log.debug("Out of iteration choosing a random gif for message '{}'", translatedMessage);
			} catch (IOException e) {
				// TODO should add some random gif as very last resort
				log.error(e.getMessage(), e);
			}
		}

		UserMessage gifMessage = new UserMessage(username, rawMessage, translatedMessage, keyword, gifFormats);
		memDb.add(gifMessage);
		return gifMessage;
	}

	private List<UserMessageDto> map2Dto(List<UserMessage> messages) {
		return messages.stream().map(l -> new UserMessageDto(l)).collect(Collectors.toList());
	}

}
