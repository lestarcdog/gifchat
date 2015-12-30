package hu.cdog.gifchat.exception;

public class GifChatException extends Exception {

	private static final long serialVersionUID = -4133573493885663937L;

	public GifChatException(String message) {
		super(message);
	}

	public GifChatException(String message, Throwable t) {
		super(message, t);
	}

}
