package hu.cdog.gifchat.memdb;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;

import hu.cdog.gifchat.model.GifMessage;

@Singleton
public class MemDb {

	private static final int MAX_SIZE = 100;

	private final List<GifMessage> messages = new LinkedList<>();

	public void add(GifMessage message) {
		if (messages.size() > MAX_SIZE) {
			messages.remove(0);
		}
		messages.add(message);
	}

	@Lock(LockType.READ)
	public List<GifMessage> getAll() {
		return Collections.unmodifiableList(messages);
	}
}
