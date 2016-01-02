package hu.cdog.gifchat.websocket.transform;

import javax.websocket.EncodeException;
import javax.websocket.Encoder.Text;
import javax.websocket.EndpointConfig;

import hu.cdog.gifchat.model.websocket.WsActionDto;

public class WsActionEncode implements Text<WsActionDto> {

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
		// TODO Auto-generated method stub
		return null;
	}

}
