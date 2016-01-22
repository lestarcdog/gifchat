package hu.cdog.gifchat.model.giphy;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GiphyData {

	private List<GipyImageContainer> data;

	public List<GipyImageContainer> getData() {
		return data;
	}

	public void setData(List<GipyImageContainer> data) {
		this.data = data;
	}

}
