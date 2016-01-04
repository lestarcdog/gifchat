package hu.cdog.gifchat.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.ejb.Singleton;
import javax.websocket.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hu.cdog.gifchat.GifChatConstants;
import hu.cdog.gifchat.model.dto.UserMessageScoreDto;

@Singleton
public class ChatUsersService {

	private static final Logger log = LoggerFactory.getLogger(ChatUsersService.class);
	private final Map<String, ChatUser> users = new HashMap<>();

	private static final Comparator<Map.Entry<String, ChatUser>> CHAT_USER_MESSAGE_NUMBER_COMPARATOR = (e1, e2) -> {
		Integer n1 = e1.getValue().getMessageNumber();
		Integer n2 = e2.getValue().getMessageNumber();

		return n1.compareTo(n2);
	};

	public void removeUser(String username) {
		removeUserFromList(username);
	}

	public void removeUser(Session s) {
		Optional<Entry<String, ChatUser>> findFirst = users.entrySet().stream()
				.filter(l -> l.getValue().session.equals(s)).findFirst();
		if (findFirst.isPresent()) {
			removeUserFromList(findFirst.get().getKey());
		}
	}

	private void removeUserFromList(String username) {
		if (username == null) {
			return;
		}
		ChatUser remove = users.remove(username);
		try {
			if (remove != null && remove.getSession() != null && remove.getSession().isOpen()) {
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

	public void updateUserDependentProperties(String username) {
		// update time
		ChatUser chatUser = users.get(username);
		chatUser.setLastSentMessage(LocalDateTime.now());
		chatUser.increaseMessageNumber();
	}

	public List<UserMessageScoreDto> getTopUsersMessageScore() {
		return sortUsersByScoreComparator(CHAT_USER_MESSAGE_NUMBER_COMPARATOR.reversed());
	}

	public List<UserMessageScoreDto> getLowestUsersMessageScore() {
		return sortUsersByScoreComparator(CHAT_USER_MESSAGE_NUMBER_COMPARATOR);
	}

	private List<UserMessageScoreDto> sortUsersByScoreComparator(Comparator<Map.Entry<String, ChatUser>> comparator) {
		return users.entrySet().stream().sorted(comparator).limit(GifChatConstants.NUM_USER_MESSAGE_SCORE)
				.map(entry -> new UserMessageScoreDto(entry.getKey(), entry.getValue().getMessageNumber()))
				.collect(Collectors.toList());
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
		private Integer messageNumber = 0;

		public Integer getMessageNumber() {
			return messageNumber;
		}

		public void increaseMessageNumber() {
			messageNumber++;
		}

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
