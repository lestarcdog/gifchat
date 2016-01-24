package hu.cdog.gifchat.model.entities;

import java.time.LocalDateTime;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;

import hu.cdog.gifchat.GifChatConstants;
import hu.cdog.gifchat.model.entities.converters.LocalDateTimeConverter;
import hu.cdog.gifchat.model.giphy.GiphyImageContainer;
import hu.cdog.gifchat.model.internal.GeneratedSound;

@Entity
public class UserMessage extends AbstractEntity {

	@Id
	@SequenceGenerator(name = "USER_MESSAGE_SEQ", sequenceName = "USER_MESSAGE_SEQ")
	@GeneratedValue(generator = "USER_MESSAGE_SEQ", strategy = GenerationType.SEQUENCE)
	private Integer id;
	@Column(length = 50)
	private String username;
	@Column(length = 150)
	private String userText;
	private String translatedMessage;
	@Column(length = 30)
	private String keyword;
	private String gifImageId;

	private String fixedHeightUrl;
	private String originalImageUrl;

	private Integer originalImageWidth;
	private Integer originalImageHeight;

	@Convert(converter = LocalDateTimeConverter.class)
	private LocalDateTime sentTime;
	@Lob
	@Basic(fetch = FetchType.LAZY)
	private byte[] sound;

	public UserMessage() {
	}

	public UserMessage(String username, String userText, String translatedMessage, String keyword,
			GiphyImageContainer container) {
		this.username = username;
		this.userText = userText;
		this.translatedMessage = translatedMessage;
		this.keyword = keyword;
		this.fixedHeightUrl = container.getImages().getFixed_height().getUrl();
		this.originalImageUrl = container.getImages().getOriginal().getUrl();
		this.originalImageWidth = container.getImages().getOriginal().getWidth();
		this.originalImageHeight = container.getImages().getOriginal().getHeight();

		this.gifImageId = container.getId();
		sentTime = LocalDateTime.now(GifChatConstants.UTC_ZONE);
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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public GeneratedSound getSound() {
		return new GeneratedSound(sound, userText);
	}

	public void setSound(GeneratedSound sound) {
		this.sound = sound.getContent();
	}

	@Override
	public String toString() {
		return "GifMessage [username=" + username + ", userText=" + userText + ", sentTime=" + sentTime + "]";
	}

	public String getGifImageId() {
		return gifImageId;
	}

	public void setGifImageId(String gifImageId) {
		this.gifImageId = gifImageId;
	}

	public String getFixedHeightUrl() {
		return fixedHeightUrl;
	}

	public void setFixedHeightUrl(String fixedHeightUrl) {
		this.fixedHeightUrl = fixedHeightUrl;
	}

	public String getOriginalImageUrl() {
		return originalImageUrl;
	}

	public void setOriginalImageUrl(String originalImageUrl) {
		this.originalImageUrl = originalImageUrl;
	}

	public Integer getOriginalImageWidth() {
		return originalImageWidth;
	}

	public void setOriginalImageWidth(Integer originalImageWidth) {
		this.originalImageWidth = originalImageWidth;
	}

	public Integer getOriginalImageHeight() {
		return originalImageHeight;
	}

	public void setOriginalImageHeight(Integer originalImageHeight) {
		this.originalImageHeight = originalImageHeight;
	}

}
