package hu.cdog.gifchat.memdb;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;

import hu.cdog.gifchat.model.GifMessage;

@Singleton
public class MemDb {

	private static final int MAX_SIZE = 100;

	private final List<GifMessage> messages = new LinkedList<>();
	private final List<String> gifUrls = new LinkedList<>();

	@PostConstruct
	public void init() {
		GifMessage msg = new GifMessage("Niki2", "itt a niki", "asdf");
		messages.add(msg);
		gifUrls.add(msg.getGifUrl());

		GifMessage msg2 = new GifMessage("Csabi", "szia niki", "fdsqer");
		messages.add(msg2);
		gifUrls.add(msg2.getGifUrl());

	}

	public void add(GifMessage message) {
		if (messages.size() > MAX_SIZE) {
			messages.remove(0);
			gifUrls.remove(0);
		}
		messages.add(message);
		gifUrls.add(message.getGifUrl());
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
