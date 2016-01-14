package hu.cdog.gifchat;

import java.time.ZoneId;

public class GifChatConstants {

	public static final ZoneId UTC_ZONE = ZoneId.of("GMT");
	public static final int _SENTENCE_SIZE = 4;
	public static final String SESSION_USERNAME_ATT = "username";
	public static final int MAX_SIZE = 100;
	public static final long NUM_USER_MESSAGE_SCORE = 5;
	public static final int CURRENT_IMAGE_RETURN_LIMIT = 5;

	public static final int SEARCH_WITHIN_GIF_IMAGES_LIMIT = 20;

	private GifChatConstants() {
	}
}
