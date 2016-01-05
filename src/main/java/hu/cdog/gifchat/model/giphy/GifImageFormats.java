package hu.cdog.gifchat.model.giphy;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GifImageFormats {
	private GifImage downsized;
	private GifImage fixed_height;
	private GifImage original;

	public GifImage getOriginal() {
		return original;
	}

	public void setOriginal(GifImage original) {
		this.original = original;
	}

	public GifImage getDownsized() {
		return downsized;
	}

	public void setDownsized(GifImage downsized) {
		this.downsized = downsized;
	}

	public GifImage getFixed_height() {
		return fixed_height;
	}

	public void setFixed_height(GifImage fixed_height) {
		this.fixed_height = fixed_height;
	}

}
