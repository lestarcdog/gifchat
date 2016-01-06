package hu.cdog.gifchat.service.listener;

import javax.enterprise.inject.spi.CDI;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hu.cdog.gifchat.GifChatConstants;
import hu.cdog.gifchat.service.ChatUsersService;

public class CurrentLoggedUsers implements HttpSessionListener {

	private static final Logger log = LoggerFactory.getLogger(CurrentLoggedUsers.class);

	private ChatUsersService userService = CDI.current().select(ChatUsersService.class).get();

	@Override
	public void sessionCreated(HttpSessionEvent se) {

	}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		String username = (String) se.getSession().getAttribute(GifChatConstants.SESSION_USERNAME_ATT);
		log.debug("Session destroy for user {}", username);
		userService.removeUser(username);
	}

}
