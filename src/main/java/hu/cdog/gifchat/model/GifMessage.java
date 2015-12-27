package hu.cdog.gifchat.model;

import java.time.LocalDateTime;

import hu.cdog.gifchat.GifChatConstants;

public class GifMessage {
	private String username;
	private String userText;
	private String gifUrl;
	private final LocalDateTime sentTime;

	public GifMessage() {
		sentTime = LocalDateTime.now(GifChatConstants.UTC_ZONE);
	}

	public LocalDateTime getSentTime() {
		return sentTime;
	}

	public GifMessage(String username, String userText, String gifUrl) {
		this.username = username;
		this.userText = userText;
		this.gifUrl = gifUrl;
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

	public String getGifUrl() {
		return gifUrl;
	}

	public void setGifUrl(String gifUrl) {
		this.gifUrl = gifUrl;
	}

}
