package hu.cdog.gifchat.model.dto;

import java.time.ZoneOffset;

import hu.cdog.gifchat.model.entities.UserMessage;

public class UserMessageDto extends BaseDto {
	private Integer id;
	private String username;
	private String userText;
	private String translatedMessage;
	private String keyword;
	private String gifFixedHeightUrl;
	private String gifOriginalUrl;
	private Integer gifOriginalWidth;
	private Integer gifOriginalHeight;
	private Long sentTime;
	private Boolean hasSound;

	public UserMessageDto() {
	}

	public UserMessageDto(UserMessage message) {
		this.id = message.getId();
		this.username = message.getUsername();
		this.userText = message.getUserText();
		this.translatedMessage = message.getTranslatedMessage();
		this.keyword = message.getKeyword();
		this.gifFixedHeightUrl = message.getFixedHeightUrl();
		this.gifOriginalUrl = message.getOriginalImageUrl();
		this.gifOriginalWidth = message.getOriginalImageWidth();
		this.gifOriginalHeight = message.getOriginalImageHeight();
		this.sentTime = message.getSentTime().toInstant(ZoneOffset.UTC).toEpochMilli();
		this.hasSound = message.getSound().isGenerated();
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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Boolean getHasSound() {
		return hasSound;
	}

	public void setHasSound(Boolean hasSound) {
		this.hasSound = hasSound;
	}

}
