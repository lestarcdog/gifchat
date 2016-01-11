package hu.cdog.gifchat.model.dto;

import java.time.ZoneOffset;

import hu.cdog.gifchat.model.GifMessage;

public class GifMessageDto extends BaseDto {
	private String username;
	private String userText;
	private String translatedMessage;
	private String keyword;
	private GifImageDto gifFixedHeight;
	private GifImageDto gifOriginal;
	private Long sentTime;

	public GifMessageDto() {
	}

	public GifMessageDto(GifMessage message) {
		this.username = message.getUsername();
		this.userText = message.getUserText();
		this.translatedMessage = message.getTranslatedMessage();
		this.keyword = message.getKeyword();
		this.gifFixedHeight = new GifImageDto(message.getFixedHeightImage());
		this.gifOriginal = new GifImageDto(message.getOriginalImage());
		this.sentTime = message.getSentTime().toInstant(ZoneOffset.UTC).toEpochMilli();
	}

	public String getUsername() {
		return username;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
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

	public Long getSentTime() {
		return sentTime;
	}

	public void setSentTime(Long sentTime) {
		this.sentTime = sentTime;
	}

	public GifImageDto getGifFixedHeight() {
		return gifFixedHeight;
	}

	public void setGifFixedHeight(GifImageDto gifFixedHeight) {
		this.gifFixedHeight = gifFixedHeight;
	}

	public GifImageDto getGifOriginal() {
		return gifOriginal;
	}

	public void setGifOriginal(GifImageDto gifOriginal) {
		this.gifOriginal = gifOriginal;
	}

	public String getTranslatedMessage() {
		return translatedMessage;
	}

	public void setTranslatedMessage(String translatedMessage) {
		this.translatedMessage = translatedMessage;
	}

}
