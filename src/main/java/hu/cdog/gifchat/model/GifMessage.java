package hu.cdog.gifchat.model;

import java.time.LocalDateTime;

import hu.cdog.gifchat.GifChatConstants;
import hu.cdog.gifchat.model.giphy.GifImage;
import hu.cdog.gifchat.model.giphy.GifImageFormats;

public class GifMessage {

	private String username;
	private String userText;
	private String translatedMessage;
	private String keyword;
	private GifImage fixedHeightImage;
	private GifImage originalImage;
	private LocalDateTime sentTime;

	public GifMessage() {
	}

	public GifMessage(String username, String userText, String translatedMessage, String keyword,
			GifImageFormats imageFormats) {
		this.username = username;
		this.userText = userText;
		this.translatedMessage = translatedMessage;
		this.keyword = keyword;
		this.fixedHeightImage = imageFormats.getFixed_height();
		this.originalImage = imageFormats.getOriginal();
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
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

	public GifImage getFixedHeightImage() {
		return fixedHeightImage;
	}

	public void setFixedHeightImage(GifImage fixedHeightImage) {
		this.fixedHeightImage = fixedHeightImage;
	}

	public GifImage getOriginalImage() {
		return originalImage;
	}

	public void setOriginalImage(GifImage originalImage) {
		this.originalImage = originalImage;
	}

	public LocalDateTime getSentTime() {
		return sentTime;
	}

	public void setSentTime(LocalDateTime sentTime) {
		this.sentTime = sentTime;
	}

	public void setSentTimeNowUtc() {
		this.sentTime = LocalDateTime.now(GifChatConstants.UTC_ZONE);
	}

	public String getTranslatedMessage() {
		return translatedMessage;
	}

	public void setTranslatedMessage(String translatedMessage) {
		this.translatedMessage = translatedMessage;
	}

	@Override
	public String toString() {
		return "GifMessage [username=" + username + ", userText=" + userText + ", sentTime=" + sentTime + "]";
	}

}
