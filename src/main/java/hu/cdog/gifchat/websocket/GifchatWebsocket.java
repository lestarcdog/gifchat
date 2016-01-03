package hu.cdog.gifchat.websocket;

import javax.inject.Inject;
import javax.websocket.CloseReason;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hu.cdog.gifchat.GifChatConstants;
import hu.cdog.gifchat.model.websocket.WsActionDto;
import hu.cdog.gifchat.service.ChatUsersService;
import hu.cdog.gifchat.websocket.transform.GifMessageDtoEncoder;
import hu.cdog.gifchat.websocket.transform.WsActionDecoder;
import hu.cdog.gifchat.websocket.transform.WsActionEncoder;

/**
 * Todo introduce security
 * 
 * @author cdog
 *
 */
@ServerEndpoint(value = "/ws", decoders = { WsActionDecoder.class }, encoders = { WsActionEncoder.class,
		GifMessageDtoEncoder.class }, configurator = GifChatWebSocketConfig.class)
public class GifchatWebsocket {

	private static final Logger log = LoggerFactory.getLogger(GifchatWebsocket.class);

	@Inject
	ChatUsersService chatUsersService;

	@OnOpen
	public void onOpen(Session session, EndpointConfig config) {
		String username = (String) config.getUserProperties().get(GifChatConstants.SESSION_USERNAME_ATT);
		log.info("Open session for {} with session {}", username, session.toString());
		chatUsersService.addSessionToUserOrCreateNew(username, session);
	}

	@OnClose
	public void onClose(Session session, CloseReason closeReason) {
		String username = (String) session.getUserProperties().get(GifChatConstants.SESSION_USERNAME_ATT);
		log.info("Remove user {} with session {}", username, session.toString());
		chatUsersService.removeUser(username);
	}

	@OnMessage
	public void onMessage(WsActionDto action, Session s) {
		throw new UnsupportedOperationException("Do not send me anything you!! We call you");
	}
}
