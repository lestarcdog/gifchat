package hu.cdog.gifchat.service.gifgenerator;

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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import hu.cdog.gifchat.model.giphy.GifImageFormats;
import hu.cdog.gifchat.model.giphy.GiphyData;

@Singleton
public class GifGenerator {

	private static final Logger log = LoggerFactory.getLogger(GifGenerator.class);

	private static final String GIPHY_SEARCH_URL = "http://api.giphy.com/v1/gifs/search?q=<<key>>&limit=10&api_key=dc6zaTOxFJmzC";
	private static final String GIPHY_TRENDING_URL = "http://api.giphy.com/v1/gifs/translate?limit=50&api_key=dc6zaTOxFJmzC";
	private static final String GIPHY_RANDOM = "http://api.giphy.com/v1/stickers/trending?limit=50&api_key=dc6zaTOxFJmzC";
	private static final String GIPHY_TRANSLATE = "http://api.giphy.com/v1/stickers/trending?s=<<key>>&limit=10&api_key=dc6zaTOxFJmzC";
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

	/**
	 * Searches based on the keyword for a gif. If no message found returns
	 * null. On a null keyword returns a ternding gif.
	 * 
	 * @param keyword
	 *            can be null keyword to search for
	 * @param gifOriginalUrls
	 *            already posted gifs try to return a different which not
	 *            contained in the list
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	@Lock(LockType.READ)
	public GifImageFormats searchGifForKeyword(String keyword, List<String> gifOriginalUrls)
			throws JsonParseException, JsonMappingException, IOException {
		String url = null;
		if (keyword == null) {
			log.debug("Searching trending gif because null keyword");
			url = trendingUrl();
		} else {
			log.debug("Searching gif for keyword: {}", keyword);
			url = giphySearchUrl(keyword);
		}

		GiphyData giphyData = fetchGifsFromUrl(url);

		// nothing has found for the keyword
		if (giphyData.getData().isEmpty()) {
			return null;
		}

		GifImageFormats gif = null;
		int maxrand = giphyData.getData().size();
		for (int i = 0; i < 5; i++) {
			gif = giphyData.getData().get(random.nextInt(maxrand)).getImages();
			if (!gifOriginalUrls.contains(gif.getOriginal().getUrl())) {
				break;
			}
		}
		return gif;

	}

	/**
	 * Picks a random gif image at last
	 * 
	 * @return not null random image
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonParseException
	 */
	public GifImageFormats pickRandomImage() throws JsonParseException, JsonMappingException, IOException {
		return fetchGifsFromUrl(GIPHY_RANDOM).getData().get(0).getImages();
	}

	private GiphyData fetchGifsFromUrl(String url) throws JsonParseException, JsonMappingException, IOException {
		Response result = client.target(url).request().get();
		String rawContent = result.readEntity(String.class);
		result.close();

		ObjectMapper mapper = new ObjectMapper();
		return mapper.readValue(rawContent, GiphyData.class);
	}

	private String trendingUrl() {
		return GIPHY_TRENDING_URL;
	}

	@SuppressWarnings("unused")
	private static String giphySearchUrl(String keyword) {
		return GIPHY_SEARCH_URL.replace("<<key>>", keyword);
	}

	private static String giphyTranslateUrl(String keyword) {
		return GIPHY_TRANSLATE.replace("<<key>>", keyword);
	}

}
