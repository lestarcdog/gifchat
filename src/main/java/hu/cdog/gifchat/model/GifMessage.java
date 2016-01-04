package hu.cdog.gifchat.model;

import java.time.LocalDateTime;

import hu.cdog.gifchat.GifChatConstants;
import hu.cdog.gifchat.model.giphy.GifImage;

public class GifMessage {
	private String username;
	private String userText;
	private GifImage gifImage;
	private final LocalDateTime sentTime;

	public GifMessage() {
		sentTime = LocalDateTime.now(GifChatConstants.UTC_ZONE);
	}

	public LocalDateTime getSentTime() {
		return sentTime;
	}

	public GifMessage(String username, String userText, GifImage image) {
		this.username = username;
		this.userText = userText;
		this.gifImage = image;
		sentTime = LocalDateTime.now(GifChatConstants.UTC_ZONE);
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUserText() {
		return userText;
	}

	public void setUserText(String userText) {
		this.userText = userText;
	}

	public GifImage getGifImage() {
		return gifImage;
	}

	public void setGifImage(GifImage gifImage) {
		this.gifImage = gifImage;
	}

}
