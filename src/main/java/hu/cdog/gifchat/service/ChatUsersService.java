package hu.cdog.gifchat.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.ejb.Singleton;
import javax.websocket.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
public class ChatUsersService {

	private static final Logger log = LoggerFactory.getLogger(ChatUsersService.class);
	private final Map<String, ChatUser> users = new HashMap<>();

	public void removeUser(String username) {
		if (username == null) {
			return;
		}
		ChatUser remove = users.remove(username);
		try {
			if (remove != null && remove.getSession().isOpen()) {
				remove.getSession().close();
			}
		} catch (IOException e) {
			log.warn(e.getMessage(), e);
		}

	}

	public boolean containsUser(String username) {
		return users.containsKey(username);
	}

	public void addUser(String username) {
		Objects.requireNonNull(username);

		ChatUser chatUser = new ChatUser(username);
		users.put(username, chatUser);
	}

	public void updateUserMessageSentTimeWithNow(String username) {
		users.get(username).setLastSentMessage(LocalDateTime.now());
	}

	public void addSessionToUserOrCreateNew(String username, Session session) {
		Objects.requireNonNull(username);
		ChatUser chatUser = users.getOrDefault(username, new ChatUser(username));
		chatUser.setSession(session);

		users.put(username, chatUser);
	}

	public Session getSessionForUser(String username) {
		Objects.requireNonNull(username);
		return users.get(username).getSession();
	}

	public Set<Session> getAllSessions() {
		return Collections
				.unmodifiableSet(users.values().stream().map(l -> l.getSession()).collect(Collectors.toSet()));
	}

	public Set<String> currentUsers() {
		return users.keySet();
	}

	private static class ChatUser {
		private Session session;
		private final String username;
		private LocalDateTime lastSentMessage;

		public ChatUser(String username) {
			this.username = username;
		}

		public Session getSession() {
			return session;
		}

		public void setSession(Session session) {
			this.session = session;
		}

		public String getUsername() {
			return username;
		}

		public LocalDateTime getLastSentMessage() {
			return lastSentMessage;
		}

		public void setLastSentMessage(LocalDateTime lastSentMessage) {
			this.lastSentMessage = lastSentMessage;
		}

	}
}
