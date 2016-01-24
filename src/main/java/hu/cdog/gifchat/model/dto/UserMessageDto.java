package hu.cdog.gifchat.model.dto;

import java.time.ZoneOffset;

import hu.cdog.gifchat.model.entities.UserMessage;

public class UserMessageDto extends BaseDto {
	private String username;
	private String userText;
	private String translatedMessage;
	private String keyword;
	private String gifFixedHeightUrl;
	private String gifOriginalUrl;
	private Integer gifOriginalWidth;
	private Integer gifOriginalHeight;
	private Long sentTime;

	public UserMessageDto() {
	}

	public UserMessageDto(UserMessage message) {
		this.username = message.getUsername();
		this.userText = message.getUserText();
		this.translatedMessage = message.getTranslatedMessage();
		this.keyword = message.getKeyword();
		this.gifFixedHeightUrl = message.getFixedHeightUrl();
		this.gifOriginalUrl = message.getOriginalImageUrl();
		this.gifOriginalWidth = message.getOriginalImageWidth();
		this.gifOriginalHeight = message.getOriginalImageHeight();
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

	public String getTranslatedMessage() {
		return translatedMessage;
	}

	public void setTranslatedMessage(String translatedMessage) {
		this.translatedMessage = translatedMessage;
	}

	public String getGifFixedHeightUrl() {
		return gifFixedHeightUrl;
	}

	public void setGifFixedHeightUrl(String gifFixedHeightUrl) {
		this.gifFixedHeightUrl = gifFixedHeightUrl;
	}

	public String getGifOriginalUrl() {
		return gifOriginalUrl;
	}

	public void setGifOriginalUrl(String gifOriginalUrl) {
		this.gifOriginalUrl = gifOriginalUrl;
	}

	public Integer getGifOriginalWidth() {
		return gifOriginalWidth;
	}

	public void setGifOriginalWidth(Integer gifOriginalWidth) {
		this.gifOriginalWidth = gifOriginalWidth;
	}

	public Integer getGifOriginalHeight() {
		return gifOriginalHeight;
	}

	public void setGifOriginalHeight(Integer gifOriginalHeight) {
		this.gifOriginalHeight = gifOriginalHeight;
	}

}
