package hu.cdog.gifchat.websocket.transform;

import java.io.IOException;

import javax.websocket.DecodeException;
import javax.websocket.Decoder.Text;
import javax.websocket.EndpointConfig;

import com.fasterxml.jackson.databind.ObjectMapper;

import hu.cdog.gifchat.model.websocket.WsActionDto;

public class WsActionDecoder implements Text<WsActionDto> {

	private static final ObjectMapper mapper = new ObjectMapper();

	@Override
	public void init(EndpointConfig config) {

	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public WsActionDto decode(String s) throws DecodeException {
		try {
			return mapper.readValue(s, WsActionDto.class);
		} catch (IOException e) {
			throw new DecodeException(s, e.getMessage());
		}
	}

	@Override
	public boolean willDecode(String s) {
		return true;
	}

}
