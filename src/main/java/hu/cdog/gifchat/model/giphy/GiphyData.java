package hu.cdog.gifchat.model.giphy;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GiphyData {

	private List<GiphyImageContainer> data;

	public List<GiphyImageContainer> getData() {
		return data;
	}

	public void setData(List<GiphyImageContainer> data) {
		this.data = data;
	}

}
