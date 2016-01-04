package hu.cdog.gifchat.memdb;

import java.util.Collections;
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
		messages.add(message);
		gifUrls.add(message.getGifImage().getUrl());
	}

	@Lock(LockType.READ)
	public List<GifMessage> getAll() {
		return Collections.unmodifiableList(messages);
	}

	@Lock(LockType.READ)
	public List<String> getGifUrls() {
		return gifUrls;
	}
}
