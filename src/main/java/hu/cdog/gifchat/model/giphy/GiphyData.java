package hu.cdog.gifchat.model.giphy;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GiphyData {

	private List<GipyImage> data;

	public List<GipyImage> getData() {
		return data;
	}

	public void setData(List<GipyImage> data) {
		this.data = data;
	}

}
