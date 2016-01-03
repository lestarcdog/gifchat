package hu.cdog.gifchat.model.dto;

import java.time.ZoneOffset;

import hu.cdog.gifchat.model.GifMessage;

public class GifMessageDto extends BaseDto {
	private String username;
	private String userText;
	private String gifUrl;
	private Long sentTime;

	public GifMessageDto() {
	}

	public GifMessageDto(GifMessage message) {
		this.username = message.getUsername();
		this.userText = message.getUserText();
		this.gifUrl = message.getGifUrl();
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

	public String getGifUrl() {
		return gifUrl;
	}

	public void setGifUrl(String gifUrl) {
		this.gifUrl = gifUrl;
	}

	public Long getSentTime() {
		return sentTime;
	}

	public void setSentTime(Long sentTime) {
		this.sentTime = sentTime;
	}

	@Override
	public String toString() {
		return "GifMessageDto [username=" + username + ", userText=" + userText + ", gifUrl=" + gifUrl + ", sentTime="
				+ sentTime + "]";
	}

}
