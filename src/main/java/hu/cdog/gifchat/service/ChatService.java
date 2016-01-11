package hu.cdog.gifchat.service;

import java.io.IOException;
import java.time.Instant;
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

import hu.cdog.gifchat.GifChatConstants;
import hu.cdog.gifchat.memdb.MemDb;
import hu.cdog.gifchat.model.GifMessage;
import hu.cdog.gifchat.model.dto.GifMessageDto;
import hu.cdog.gifchat.model.giphy.GifImageFormats;
import hu.cdog.gifchat.service.gifgenerator.GifGenerator;
import hu.cdog.gifchat.service.gifgenerator.MessageTokinezer;
import hu.cdog.gifchat.service.gifgenerator.strategies.LongestWordFirst;

@Stateless
public class ChatService {

	private static final Logger log = LoggerFactory.getLogger(ChatService.class);

	private static final String TRENDING_KW = "**trending**";

	@Inject
	MemDb memDb;

	@Inject
	GifGenerator gifGenerator;

	@Inject
	ChatUsersService chatUsersService;

	public List<GifMessageDto> getNewMessages(Long userTime) {
		LocalDateTime userTimeLd = LocalDateTime.ofInstant(Instant.ofEpochMilli(userTime), GifChatConstants.UTC_ZONE);
		List<GifMessage> filtered = memDb.getAll().stream().filter(l -> l.getSentTime().isAfter(userTimeLd))
				.collect(Collectors.toList());
		return map2Dto(filtered);
	}

	public void newMessage(String message, String username) {
		if (message == null || message.isEmpty()) {
			return;
		}
		GifMessage newMessage = generateAndSaveNewMessage(message, username);
		chatUsersService.updateUserDependentProperties(username);
		sendNewMessageToEveryone(newMessage);

	}

	public List<GifMessageDto> getLastMessages() {
		return map2Dto(memDb.getCurrents());
	}

	public List<GifMessageDto> earlierThan(LocalDateTime currentTime) {
		return map2Dto(memDb.earlierThan(currentTime));
	}

	private void sendNewMessageToEveryone(GifMessage message) {
		GifMessageDto dto = new GifMessageDto(message);
		for (Session session : chatUsersService.getAllSessions()) {
			sendMessage(session, dto);
		}
	}

	private void sendMessage(Session s, GifMessageDto msg) {
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

	private GifMessage generateAndSaveNewMessage(String message, String username) {
		MessageTokinezer possibleKeywords = new MessageTokinezer(message, LongestWordFirst.get());
		GifImageFormats gifFormats = null;
		int iteration = 0;
		String keyword = null;
		while (gifFormats == null && iteration < 5) {
			String nextToken = possibleKeywords.getNextToken();
			try {
				gifFormats = gifGenerator.randomGifForKeyword(nextToken, memDb.getOriginalGifUrls());
			} catch (Exception e) {
				log.warn(e.getMessage(), e);
			}
			iteration++;
			if (nextToken != null && gifFormats != null) {
				keyword = nextToken;
				log.debug("Found keyword '{}' for message '{}'", keyword, message);
			} else {
				keyword = TRENDING_KW;
			}
		}
		if (gifFormats == null) {
			try {
				gifFormats = gifGenerator.randomGifForKeyword(null, Collections.emptyList());
				keyword = TRENDING_KW;
				log.debug("Out of iteration choosing a trending for message '{}'", message);
			} catch (IOException e) {
				// TODO should add some random gif as last resort
				log.error(e.getMessage(), e);
			}
		}
		GifMessage gifMessage = new GifMessage(username, message, keyword, gifFormats);
		memDb.add(gifMessage);
		return gifMessage;
	}

	private List<GifMessageDto> map2Dto(List<GifMessage> messages) {
		return messages.stream().map(l -> new GifMessageDto(l)).collect(Collectors.toList());
	}

}
