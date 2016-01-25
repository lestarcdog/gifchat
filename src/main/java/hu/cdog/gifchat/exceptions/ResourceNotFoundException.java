package hu.cdog.gifchat.exceptions;

import hu.cdog.gifchat.exception.GifChatException;

public class ResourceNotFoundException extends GifChatException {

	private static final long serialVersionUID = -6555650466366306995L;

	public ResourceNotFoundException(Object id) {
		super("Resource not found " + id);
	}

}
