package hu.cdog.gifchat.websocket.transform;

import javax.websocket.EncodeException;
import javax.websocket.Encoder.Text;
import javax.websocket.EndpointConfig;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import hu.cdog.gifchat.model.websocket.WsActionDto;

public class WsActionEncoder implements Text<WsActionDto> {

	private static final ObjectMapper mapper = new ObjectMapper();

	@Override
	public void init(EndpointConfig config) {
		// TODO Auto-generated method stub

	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public String encode(WsActionDto object) throws EncodeException {
		try {
			return mapper.writeValueAsString(object);
		} catch (JsonProcessingException e) {
			throw new EncodeException(object, e.getMessage());
		}
	}

}
