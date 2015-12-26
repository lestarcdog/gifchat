package hu.cdog.gifchat.model.giphy;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GipyImage {
	private String type;
	private String id;
	private String url;
	private String bitly_gif_url;
	private String bitly_url;
	private String embed_url;
	private String username;
	private String source;
	private String rating;
	private String caption;
	private String content_url;

	private GifImageFormats images;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getBitly_gif_url() {
		return bitly_gif_url;
	}

	public void setBitly_gif_url(String bitly_gif_url) {
		this.bitly_gif_url = bitly_gif_url;
	}

	public String getBitly_url() {
		return bitly_url;
	}

	public void setBitly_url(String bitly_url) {
		this.bitly_url = bitly_url;
	}

	public String getEmbed_url() {
		return embed_url;
	}

	public void setEmbed_url(String embed_url) {
		this.embed_url = embed_url;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public String getContent_url() {
		return content_url;
	}

	public void setContent_url(String content_url) {
		this.content_url = content_url;
	}

	public GifImageFormats getImages() {
		return images;
	}

	public void setImages(GifImageFormats images) {
		this.images = images;
	}

}
