package hu.cdog.gifchat.websocket.transform;

import javax.websocket.EncodeException;
import javax.websocket.Encoder.Text;
import javax.websocket.EndpointConfig;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import hu.cdog.gifchat.model.dto.GifMessageDto;

public class GifMessageDtoEncoder implements Text<GifMessageDto> {

	private static final ObjectMapper mapper = new ObjectMapper();

	@Override
	public void init(EndpointConfig config) {

	}

	@Override
	public void destroy() {

	}

	@Override
	public String encode(GifMessageDto object) throws EncodeException {
		try {
			return mapper.writeValueAsString(object);
		} catch (JsonProcessingException e) {
			throw new EncodeException(object, e.getMessage());
		}
	}

}
