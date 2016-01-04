package hu.cdog.gifchat.model.dto;

import java.time.ZoneOffset;

import hu.cdog.gifchat.model.GifMessage;

public class GifMessageDto extends BaseDto {
	private String username;
	private String userText;
	private GifImageDto gif;
	private Long sentTime;

	public GifMessageDto() {
	}

	public GifMessageDto(GifMessage message) {
		this.username = message.getUsername();
		this.userText = message.getUserText();
		this.gif = new GifImageDto(message.getGifImage());
		this.sentTime = message.getSentTime().toInstant(ZoneOffset.UTC).toEpochMilli();
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

	public GifImageDto getGif() {
		return gif;
	}

	public void setGif(GifImageDto gif) {
		this.gif = gif;
	}

	public Long getSentTime() {
		return sentTime;
	}

	public void setSentTime(Long sentTime) {
		this.sentTime = sentTime;
	}

	@Override
	public String toString() {
		return "GifMessageDto [username=" + username + ", userText=" + userText + ", gif=" + gif + ", sentTime="
				+ sentTime + "]";
	}

}
