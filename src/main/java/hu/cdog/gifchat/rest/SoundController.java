package hu.cdog.gifchat.rest;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.StreamingOutput;

import org.apache.commons.io.IOUtils;

import hu.cdog.gifchat.GifChatConstants;
import hu.cdog.gifchat.exception.GifChatException;
import hu.cdog.gifchat.model.internal.GeneratedSound;
import hu.cdog.gifchat.service.SoundService;

@Path("/sound")
public class SoundController {

	@Inject
	SoundService soundService;

	@Context
	HttpServletRequest request;

	@GET
	@Path("/{id}")
	@Produces(GifChatConstants.WAV_MIME_TYPE)
	public StreamingOutput getSound(@PathParam(value = "id") Integer messageId) throws GifChatException {
		final GeneratedSound sound = soundService.getSound(messageId);
		return new StreamingOutput() {
			@Override
			public void write(OutputStream output) throws IOException, WebApplicationException {
				IOUtils.write(sound.getContent(), output);
			}
		};
	}

	@POST
	@Path("/makeSound")
	public String makeSoundOfMessage(Map<String, Object> params) throws GifChatException {
		Integer messageId = (Integer) params.get("id");
		if (messageId == null) {
			throw new GifChatException("Invalid parameter. Expected variable 'id'");
		}

		soundService.makeSound(messageId,
				(String) request.getSession().getAttribute(GifChatConstants.SESSION_USERNAME_ATT));
		return "ok";

	}

}
