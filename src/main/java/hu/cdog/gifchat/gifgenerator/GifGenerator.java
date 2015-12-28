package hu.cdog.gifchat.gifgenerator;

import java.io.IOException;
import java.util.List;
import java.util.Random;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import hu.cdog.gifchat.model.giphy.GiphyData;

@Singleton
public class GifGenerator {

	private static final String GIPHY_SEARCH_URL = "http://api.giphy.com/v1/gifs/search?q=<<key>>&api_key=dc6zaTOxFJmzC";
	private static final String GIPHY_TRENDING_URL = "http://api.giphy.com/v1/stickers/trending?api_key=dc6zaTOxFJmzC";
	private static final Integer DEFAULT_RANDOM = 25;
	private final Random random = new Random();

	private Client client;

	@PostConstruct
	public void init() {
		client = ClientBuilder.newClient();
	}

	@PreDestroy
	public void destroy() {
		client.close();
	}

	@Lock(LockType.READ)
	public String randomGifForKeyword(String keyword, List<String> gifUrls)
			throws JsonParseException, JsonMappingException, IOException {
		String url = null;
		if (keyword == null) {
			url = trendingUrl();
		} else {
			url = giphySearchUrl(keyword);
		}
		Response result = client.target(url).request().get();
		String rawContent = result.readEntity(String.class);
		result.close();

		ObjectMapper mapper = new ObjectMapper();
		GiphyData gipyData = mapper.readValue(rawContent, GiphyData.class);
		String returnUrl = null;

		// nothing has found for the keyword
		if (gipyData.getData().isEmpty()) {
			return null;
		}
		int maxrand = gipyData.getData().size();
		for (int i = 0; i < 5; i++) {
			returnUrl = gipyData.getData().get(random.nextInt(maxrand)).getImages().getDownsized().getUrl();
			if (!gifUrls.contains(returnUrl)) {
				break;
			}
		}
		return returnUrl;

	}

	private String trendingUrl() {
		return GIPHY_TRENDING_URL;
	}

	private static String getGiphyUrl(String keyword, Integer limit) {
		throw new UnsupportedOperationException();
	}

	private static String giphySearchUrl(String keyword) {
		return GIPHY_SEARCH_URL.replace("<<key>>", keyword);
	}

}
