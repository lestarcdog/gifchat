package hu.cdog.gifchat.service.listener;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import hu.cdog.gifchat.GifChatConstants;

public class CurrentLoggedUsers implements HttpSessionListener {

	private static final Set<String> USERNAMES = new HashSet<>();;

	@Override
	public void sessionCreated(HttpSessionEvent se) {

	}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		String username = (String) se.getSession().getAttribute(GifChatConstants.SESSION_USERNAME_ATT);
		removeUser(username);
	}

	public static synchronized boolean addUser(String username) {
		return USERNAMES.add(username);
	}

	public static synchronized void removeUser(String username) {
		if (username != null) {
			USERNAMES.remove(username);
		}
	}

}
