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
import hu.cdog.gifchat.gifgenerator.GifGenerator;
import hu.cdog.gifchat.gifgenerator.MessageTokinezer;
import hu.cdog.gifchat.gifgenerator.strategies.LongestWordFirst;
import hu.cdog.gifchat.memdb.MemDb;
import hu.cdog.gifchat.model.GifMessage;
import hu.cdog.gifchat.model.dto.GifMessageDto;
import hu.cdog.gifchat.model.giphy.GifImage;

@Stateless
public class ChatService {

	private static final Logger log = LoggerFactory.getLogger(ChatService.class);

	@Inject
	MemDb memDb;

	@Inject
	GifGenerator gifGenerator;

	@Inject
	ChatUsersService chatUsersService;

	public List<GifMessageDto> getAll() {
		return map2Dto(memDb.getAll());
	}

	public List<GifMessageDto> getNewMessages(Long userTime) {
		LocalDateTime userTimeLd = LocalDateTime.ofInstant(Instant.ofEpochMilli(userTime), GifChatConstants.UTC_ZONE);
		List<GifMessage> filtered = memDb.getAll().stream().filter(l -> l.getSentTime().isAfter(userTimeLd))
				.collect(Collectors.toList());
		return map2Dto(filtered);
	}

	public void newMessage(String message, String username) {
		GifMessage newMessage = generateAndSaveNewMessage(message, username);
		chatUsersService.updateUserDependentProperties(username);
		sendNewMessageToEveryone(newMessage);

	}

	private void sendNewMessageToEveryone(GifMessage message) {
		GifMessageDto dto = new GifMessageDto(message);
		for (Session session : chatUsersService.getAllSessions()) {
			sendMessage(session, dto);
		}
	}

	private void sendMessage(Session s, GifMessageDto msg) {
		try {
			if (s.isOpen()) {
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
		GifImage gif = null;
		int iteration = 0;
		while (gif == null && iteration < 5) {
			String keyword = possibleKeywords.getNextToken();
			try {
				gif = gifGenerator.randomGifForKeyword(keyword, memDb.getGifUrls());
			} catch (Exception e) {
				log.warn(e.getMessage(), e);
			}
			iteration++;
		}
		if (gif == null) {
			try {
				gif = gifGenerator.randomGifForKeyword(null, Collections.emptyList());
			} catch (IOException e) {
				// TODO should add some random gif as last resort
				log.error(e.getMessage(), e);
			}
		}
		GifMessage gifMessage = new GifMessage(username, message, gif);
		memDb.add(gifMessage);
		return gifMessage;
	}

	private List<GifMessageDto> map2Dto(List<GifMessage> messages) {
		return messages.stream().map(l -> new GifMessageDto(l)).collect(Collectors.toList());
	}
}
