package hu.cdog.gifchat.memdb;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;

import hu.cdog.gifchat.GifChatConstants;
import hu.cdog.gifchat.model.GifMessage;

@Singleton
public class MemDb {

	private final List<GifMessage> messages = new LinkedList<>();
	private final List<String> gifUrls = new LinkedList<>();

	public void add(GifMessage message) {
		if (messages.size() >= GifChatConstants.MAX_SIZE) {
			messages.remove(0);
			gifUrls.remove(0);
		}
		// set sent time
		message.setSentTimeNow();
		messages.add(message);
		gifUrls.add(message.getOriginalImage().getUrl());
	}

	@Lock(LockType.READ)
	public List<GifMessage> getCurrents() {
		if (messages.size() < GifChatConstants.CURRENT_IMAGE_RETURN_LIMIT + 1) {
			return Collections.unmodifiableList(messages);
		} else {
			int inclusiveAlso = messages.size() + 1;
			return Collections.unmodifiableList(
					messages.subList(inclusiveAlso - GifChatConstants.CURRENT_IMAGE_RETURN_LIMIT, inclusiveAlso));
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
	public List<GifMessage> earlierThan(LocalDateTime current) {
		GifMessage dummy = new GifMessage();
		dummy.setSentTime(current);
		int foundIdx = Collections.binarySearch(messages, dummy, Comparator.comparing(GifMessage::getSentTime));
		if (foundIdx > -1) {
			// Exclusive the current
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
	public List<GifMessage> getAll() {
		return Collections.unmodifiableList(messages);
	}

	@Lock(LockType.READ)
	public List<String> getOriginalGifUrls() {
		return gifUrls;
	}
}
