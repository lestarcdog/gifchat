package hu.cdog.gifchat.rest.mappers;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import hu.cdog.gifchat.exception.GifChatException;
import hu.cdog.gifchat.model.dto.ExceptionDto;

@Provider
public class ExceptionHandler implements ExceptionMapper<GifChatException> {

	@Override
	public Response toResponse(GifChatException exception) {
		Entity<ExceptionDto> json = Entity.json(new ExceptionDto(exception));
		return Response.status(Response.Status.BAD_REQUEST).type(MediaType.APPLICATION_JSON_TYPE).entity(json).build();
	}

}
