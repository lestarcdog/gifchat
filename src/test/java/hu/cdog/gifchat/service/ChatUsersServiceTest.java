package hu.cdog.gifchat.service;

import java.io.IOException;
import java.net.URI;
import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.websocket.CloseReason;
import javax.websocket.Extension;
import javax.websocket.MessageHandler;
import javax.websocket.MessageHandler.Partial;
import javax.websocket.MessageHandler.Whole;
import javax.websocket.RemoteEndpoint.Async;
import javax.websocket.RemoteEndpoint.Basic;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;

import org.junit.Assert;
import org.junit.Test;

import hu.cdog.gifchat.model.dto.UserMessageScoreDto;

public class ChatUsersServiceTest {

	@Test
	public void sortOfChatUsersMessageNumber() {
		ChatUsersService s = new ChatUsersService();
		s.addUser("jozsi");
		s.addUser("bela");

		s.updateUserDependentProperties("jozsi");
		s.updateUserDependentProperties("jozsi");
		s.updateUserDependentProperties("jozsi");
		s.updateUserDependentProperties("bela");

		List<UserMessageScoreDto> list = s.getTopUsersMessageScore();

		Assert.assertEquals("jozsi", list.get(0).getUsername());
		Assert.assertEquals(((Integer) 3), list.get(0).getCount());

		list = s.getLowestUsersMessageScore();

		Assert.assertEquals("bela", list.get(0).getUsername());
		Assert.assertEquals(((Integer) 1), list.get(0).getCount());

	}

	@Test
	public void removeUserBySession() {
		ChatUsersService s = new ChatUsersService();
		MockSession b = new MockSession();
		MockSession a = new MockSession();
		s.addSessionToUserOrCreateNew("a", a);
		s.addSessionToUserOrCreateNew("b", b);

		for (Session session : s.getAllSessions()) {
			if (session.equals(a)) {
				s.removeUser(session);
			}
		}

		Assert.assertEquals(1, s.getAllSessions().size());

	}

	public static class MockSession implements Session {

		@Override
		public WebSocketContainer getContainer() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void addMessageHandler(MessageHandler handler) throws IllegalStateException {
			// TODO Auto-generated method stub

		}

		@Override
		public <T> void addMessageHandler(Class<T> clazz, Whole<T> handler) {
			// TODO Auto-generated method stub

		}

		@Override
		public <T> void addMessageHandler(Class<T> clazz, Partial<T> handler) {
			// TODO Auto-generated method stub

		}

		@Override
		public Set<MessageHandler> getMessageHandlers() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void removeMessageHandler(MessageHandler handler) {
			// TODO Auto-generated method stub

		}

		@Override
		public String getProtocolVersion() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getNegotiatedSubprotocol() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<Extension> getNegotiatedExtensions() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public boolean isSecure() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean isOpen() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public long getMaxIdleTimeout() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public void setMaxIdleTimeout(long milliseconds) {
			// TODO Auto-generated method stub

		}

		@Override
		public void setMaxBinaryMessageBufferSize(int length) {
			// TODO Auto-generated method stub

		}

		@Override
		public int getMaxBinaryMessageBufferSize() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public void setMaxTextMessageBufferSize(int length) {
			// TODO Auto-generated method stub

		}

		@Override
		public int getMaxTextMessageBufferSize() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public Async getAsyncRemote() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Basic getBasicRemote() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getId() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void close() throws IOException {
			// TODO Auto-generated method stub

		}

		@Override
		public void close(CloseReason closeReason) throws IOException {
			// TODO Auto-generated method stub

		}

		@Override
		public URI getRequestURI() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Map<String, List<String>> getRequestParameterMap() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getQueryString() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Map<String, String> getPathParameters() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Map<String, Object> getUserProperties() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Principal getUserPrincipal() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Set<Session> getOpenSessions() {
			// TODO Auto-generated method stub
			return null;
		}

	}

}
