package hu.cdog.gifchat.service;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hu.cdog.gifchat.data.caches.SoundCache;
import hu.cdog.gifchat.data.dao.MessagesDao;
import hu.cdog.gifchat.exception.GifChatException;
import hu.cdog.gifchat.exceptions.ResourceNotFoundException;
import hu.cdog.gifchat.model.entities.UserMessage;
import hu.cdog.gifchat.model.internal.GeneratedSound;
import hu.cdog.gifchat.service.azure.SoundGeneratorService;

@Stateless
public class SoundService {

	private static final Logger log = LoggerFactory.getLogger(SoundService.class);

	@Inject
	SoundGeneratorService soundGeneratorService;

	@Inject
	MessagesDao messagesDao;

	@Inject
	SoundCache cache;

	public void makeSound(Integer messageId, String username) throws GifChatException {
		log.debug("Making sound for msg id {} for requesting user {}", messageId, username);
		Optional<UserMessage> find = messagesDao.find(messageId, UserMessage.class);
		UserMessage message = find.orElseThrow(() -> new ResourceNotFoundException(messageId));
		// already generated
		if (message.getSound().isGenerated()) {
			log.debug("Sound for {} is already generated", messageId);
			return;
		} else {
			// only posting user can generate sound
			if (!message.getUsername().equals(username)) {
				throw new GifChatException("Only self posted message sound generation is possible");
			}
			// generate the user typed text
			GeneratedSound newSound = soundGeneratorService.generateSound(message.getUserText());
			message.setSound(newSound);
			// put it in the cache
			cache.set(message.getId(), newSound);
		}
	}

	public GeneratedSound getSound(Integer messageId) throws GifChatException {
		GeneratedSound sound = cache.get(messageId);
		if (sound == null) {
			Optional<UserMessage> find = messagesDao.find(messageId, UserMessage.class);
			UserMessage message = find.orElseThrow(() -> new ResourceNotFoundException(messageId));
			sound = message.getSound();
			if (sound.isGenerated()) {
				return sound;
			} else {
				throw new GifChatException("Sound has not been made for message " + messageId);
			}
		} else {
			return sound;
		}
	}

}
