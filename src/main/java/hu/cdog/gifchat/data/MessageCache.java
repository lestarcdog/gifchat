package hu.cdog.gifchat.data;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;

import hu.cdog.gifchat.GifChatConstants;
import hu.cdog.gifchat.model.entities.UserMessage;

@Singleton
public class MessageCache {

	private final List<UserMessage> messages = new ArrayList<>();
	private final List<String> gifUrls = new LinkedList<>();

	public void add(UserMessage message) {
		if (messages.size() >= GifChatConstants.MAX_SIZE) {
			messages.remove(0);
			gifUrls.remove(0);
		}
		// set sent time
		message.setSentTimeNowUtc();
		messages.add(message);
		gifUrls.add(message.getOriginalImage().getUrl());
	}

	@Lock(LockType.READ)
	public List<UserMessage> getCurrents() {
		if (messages.size() <= GifChatConstants.CURRENT_IMAGE_RETURN_LIMIT) {
			return Collections.unmodifiableList(messages);
		} else {
			int startFrom = messages.size() - GifChatConstants.CURRENT_IMAGE_RETURN_LIMIT;
			return Collections.unmodifiableList(messages.subList(startFrom, messages.size()));
		}

	}

	/**
	 * Returns the earlier messages that has the timestamp than the current
	 * timestamp. Exclusive the current message.
	 * 
	 * @param current
	 *            timestamp of the current
	 * @return
	 */
	@Lock(LockType.READ)
	public List<UserMessage> earlierThan(LocalDateTime current) {
		UserMessage dummy = new UserMessage();
		dummy.setSentTime(current);
		int foundIdx = Collections.binarySearch(messages, dummy,
				Comparator.comparing(l -> l.getSentTime().toInstant(ZoneOffset.UTC)));
		if (foundIdx > -1) {
			// already the first element
			if (foundIdx == 0) {
				return Collections.emptyList();
			}
			int sublistFrom = foundIdx - GifChatConstants.CURRENT_IMAGE_RETURN_LIMIT;
			if (sublistFrom < 0) {
				// send from the begininng
				return Collections.unmodifiableList(messages.subList(0, foundIdx));
			} else {
				// send the sublist
				return Collections.unmodifiableList(messages.subList(sublistFrom, foundIdx));
			}
		} else {
			throw new UnsupportedOperationException("Little harsh but should be found. Implement some fallback method");
		}
	}

	@Lock(LockType.READ)
	public List<UserMessage> getAll() {
		return Collections.unmodifiableList(messages);
	}

	/**
	 * Returns the last number {@code size} of urls.
	 * 
	 * @param size
	 *            can be null or any positive ingerger
	 * @return at most the size of the {@code size} elements
	 */
	@Lock(LockType.READ)
	public List<String> getLastGifs(Integer size) {
		int listSize = gifUrls.size();
		if (size == null || size == 0 || listSize < size) {
			return gifUrls;
		} else {
			return gifUrls.subList(gifUrls.size() - size, gifUrls.size());

		}

	}
}
