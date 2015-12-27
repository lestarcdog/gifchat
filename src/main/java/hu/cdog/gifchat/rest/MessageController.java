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

import hu.cdog.gifchat.model.GifMessage;
import hu.cdog.gifchat.service.ChatService;

@Path("/messages")
public class MessageController {

	private static final String USERNAME = "username";

	@Inject
	ChatService chatService;

	@Context
	HttpServletRequest request;

	@Context
	HttpServletResponse response;

	@POST
	@Path("/login")
	public Response login(@QueryParam(USERNAME) String username) {
		if (username == null || username.isEmpty()) {
			return Response.status(HttpStatus.SC_BAD_REQUEST).build();
		}
		request.getSession().setAttribute(USERNAME, username);
		return Response.ok().build();
	}

	@GET
	@Path("/all")
	@Produces(MediaType.APPLICATION_JSON)
	public List<GifMessage> all() {
		return chatService.getAll();
	}

	@GET
	@Path("/from")
	@Produces(MediaType.APPLICATION_JSON)
	public List<GifMessage> newMessages(@QueryParam(value = "usertime") Long userTime) {
		return chatService.getNewMessages(userTime);

	}

	@POST
	@Path("/new")
	@Consumes(MediaType.TEXT_PLAIN)
	public Response newMessage(String message) {
		String username = (String) request.getSession().getAttribute(USERNAME);
		if (username == null) {
			return Response.status(HttpStatus.SC_BAD_REQUEST).build();
		}
		chatService.newMessage(message, username);
		return Response.ok().build();
	}
}
