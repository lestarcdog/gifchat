package hu.cdog.gifchat.rest;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.http.HttpStatus;

import hu.cdog.gifchat.GifChatConstants;
import hu.cdog.gifchat.exception.GifChatException;
import hu.cdog.gifchat.model.GifMessageDto;
import hu.cdog.gifchat.model.dto.UserCredentialDto;
import hu.cdog.gifchat.model.dto.UserMessageDto;
import hu.cdog.gifchat.service.ChatService;
import hu.cdog.gifchat.service.listener.CurrentLoggedUsers;

@Path("/messages")
public class MessageController {

	@Inject
	ChatService chatService;

	@Context
	HttpServletRequest request;

	@Context
	HttpServletResponse response;

	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response login(UserCredentialDto credentials) throws GifChatException {
		if (credentials.getUsername() == null || credentials.getUsername().isEmpty()) {
			return Response.status(HttpStatus.SC_BAD_REQUEST).build();
		}
		if (CurrentLoggedUsers.addUser(credentials.getUsername())) {
			request.getSession().setAttribute(GifChatConstants.SESSION_USERNAME_ATT, credentials.getUsername());
			return Response.ok().build();
		} else {
			throw new GifChatException("Username taken");
		}

	}

	@GET
	@Path("/all")
	@Produces(MediaType.APPLICATION_JSON)
	public List<GifMessageDto> all() {
		return chatService.getAll();
	}

	@GET
	@Path("/from")
	@Produces(MediaType.APPLICATION_JSON)
	public List<GifMessageDto> newMessages(@QueryParam(value = "usertime") Long userTime) {
		return chatService.getNewMessages(userTime);

	}

	@POST
	@Path("/new")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response newMessage(UserMessageDto message) {
		String username = (String) request.getSession().getAttribute(GifChatConstants.SESSION_USERNAME_ATT);
		if (username == null) {
			return Response.status(HttpStatus.SC_BAD_REQUEST).build();
		}
		chatService.newMessage(message.getMessage(), username);
		return Response.ok().build();
	}
}
