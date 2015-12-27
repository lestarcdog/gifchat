package hu.cdog.gifchat.service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hu.cdog.gifchat.GifChatConstants;
import hu.cdog.gifchat.gifgenerator.GifGenerator;
import hu.cdog.gifchat.gifgenerator.MessageTokinezer;
import hu.cdog.gifchat.memdb.MemDb;
import hu.cdog.gifchat.model.GifMessage;

@Stateless
public class ChatService {

	private static final Logger log = LoggerFactory.getLogger(ChatService.class);

	@Inject
	MemDb memDb;

	@Inject
	GifGenerator gifGenerator;

	public List<GifMessage> getAll() {
		return memDb.getAll();
	}

	public List<GifMessage> getNewMessages(Long userTime) {
		LocalDateTime userTimeLd = LocalDateTime.ofInstant(Instant.ofEpochMilli(userTime), GifChatConstants.UTC_ZONE);
		List<GifMessage> filtered = memDb.getAll().stream().filter(l -> l.getSentTime().isAfter(userTimeLd))
				.collect(Collectors.toList());
		return filtered;
	}

	public void newMessage(String message, String username) {
		MessageTokinezer possibleKeywords = new MessageTokinezer(message);
		String gifUrl = null;
		int iteration = 0;
		while (gifUrl == null || iteration < 15) {
			String keyword = possibleKeywords.getNextToken();
			try {
				gifUrl = gifGenerator.randomGifForKeyword(keyword, memDb.getGifUrls());
			} catch (Exception e) {
				log.warn(e.getMessage(), e);
			}
			iteration++;
		}
		GifMessage gifMessage = new GifMessage(username, message, gifUrl);
		memDb.add(gifMessage);

	}
}
