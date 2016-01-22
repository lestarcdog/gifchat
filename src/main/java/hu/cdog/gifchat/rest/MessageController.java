package hu.cdog.gifchat.rest;

import java.time.Instant;
import java.time.LocalDateTime;
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

import hu.cdog.gifchat.GifChatConstants;
import hu.cdog.gifchat.exception.GifChatException;
import hu.cdog.gifchat.model.dto.UserCredentialDto;
import hu.cdog.gifchat.model.dto.UserMessageDto;
import hu.cdog.gifchat.model.dto.UserMessageScoreDto;
import hu.cdog.gifchat.service.ChatService;
import hu.cdog.gifchat.service.ChatUsersService;

@Path("/messages")
public class MessageController {

	@Inject
	ChatService chatService;

	@Inject
	ChatUsersService userService;

	@Context
	HttpServletRequest request;

	@Context
	HttpServletResponse response;

	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response login(UserCredentialDto credentials) throws GifChatException {
		if (credentials.getUsername() == null || credentials.getUsername().isEmpty()) {
			return Response.status(Response.Status.UNAUTHORIZED).build();
		}
		if (!userService.containsUser(credentials.getUsername())) {
			userService.addUser(credentials.getUsername());
			request.getSession().setAttribute(GifChatConstants.SESSION_USERNAME_ATT, credentials.getUsername());
			return Response.ok().build();
		} else {
			throw new GifChatException("Username already taken");
		}

	}

	@GET
	@Path("/lastMessages")
	@Produces(MediaType.APPLICATION_JSON)
	public List<UserMessageDto> lastMessages() {
		return chatService.getLastMessages();
	}

	@GET
	@Path("/earlierThan")
	@Produces(MediaType.APPLICATION_JSON)
	public List<UserMessageDto> earlierThan(@QueryParam(value = "ts") Long currentTimestamp) {
		return chatService.earlierThan(
				LocalDateTime.ofInstant(Instant.ofEpochMilli(currentTimestamp), GifChatConstants.UTC_ZONE));
	}

	@GET
	@Path("/from")
	@Produces(MediaType.APPLICATION_JSON)
	public List<UserMessageDto> newMessages(@QueryParam(value = "usertime") Long userTime) {
		return chatService.getNewMessages(userTime);

	}

	@GET
	@Path("/currentTopUsers")
	@Produces(MediaType.APPLICATION_JSON)
	public List<UserMessageScoreDto> getCurrentUsers() {
		return userService.getTopUsersMessageScore();
	}

	@POST
	@Path("/new")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response newMessage(UserMessageDto message) {
		String username = getLoggedInUser();
		if (username == null) {
			return Response.status(Response.Status.UNAUTHORIZED).build();
		}
		chatService.newMessage(message.getUserText(), username);
		return Response.ok().build();
	}

	private String getLoggedInUser() {
		return (String) request.getSession().getAttribute(GifChatConstants.SESSION_USERNAME_ATT);
	}
}
