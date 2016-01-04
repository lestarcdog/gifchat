package hu.cdog.gifchat.model.dto;

import hu.cdog.gifchat.model.giphy.GifImage;

public class GifImageDto extends BaseDto {
	private String url;
	private Integer width;
	private Integer height;

	public GifImageDto() {

	}

	public GifImageDto(GifImage image) {
		this.url = image.getUrl();
		this.width = image.getWidth();
		this.height = image.getHeight();
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getWidth() {
		return width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}
}
