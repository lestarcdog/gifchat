package hu.cdog.gifchat.websocket;

import javax.servlet.http.HttpSession;
import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;

import hu.cdog.gifchat.GifChatConstants;

public class GifChatWebSocketConfig extends ServerEndpointConfig.Configurator {

	@Override
	public void modifyHandshake(ServerEndpointConfig sec, HandshakeRequest request, HandshakeResponse response) {
		HttpSession httpSession = (HttpSession) request.getHttpSession();
		String username = (String) httpSession.getAttribute(GifChatConstants.SESSION_USERNAME_ATT);
		sec.getUserProperties().put(GifChatConstants.SESSION_USERNAME_ATT, username);
		super.modifyHandshake(sec, request, response);
	}

}
