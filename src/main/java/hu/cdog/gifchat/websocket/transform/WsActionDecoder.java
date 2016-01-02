package hu.cdog.gifchat.websocket.transform;

import javax.websocket.DecodeException;
import javax.websocket.Decoder.Text;
import javax.websocket.EndpointConfig;

import hu.cdog.gifchat.model.websocket.WsActionDto;

public class WsActionDecoder implements Text<WsActionDto> {

	@Override
	public void init(EndpointConfig config) {

	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public WsActionDto decode(String s) throws DecodeException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean willDecode(String s) {
		// TODO Auto-generated method stub
		return false;
	}

}
