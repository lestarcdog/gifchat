package hu.cdog.gifchat.model.giphy;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GifImageFormats {
	private GifImage downsized;
	private GifImage fixed_width_small;
	private GifImage fixed_height_small;

	public GifImage getDownsized() {
		return downsized;
	}

	public void setDownsized(GifImage downsized) {
		this.downsized = downsized;
	}

	public GifImage getFixed_width_small() {
		return fixed_width_small;
	}

	public void setFixed_width_small(GifImage fixed_width_small) {
		this.fixed_width_small = fixed_width_small;
	}

	public GifImage getFixed_height_small() {
		return fixed_height_small;
	}

	public void setFixed_height_small(GifImage fixed_height_small) {
		this.fixed_height_small = fixed_height_small;
	}

}
