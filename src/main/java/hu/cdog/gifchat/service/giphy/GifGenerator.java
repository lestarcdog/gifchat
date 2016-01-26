package hu.cdog.gifchat.service.giphy;

import java.io.IOException;
import java.util.List;
import java.util.Random;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import hu.cdog.gifchat.model.giphy.GiphyData;
import hu.cdog.gifchat.model.giphy.GiphyImageContainer;

@Singleton
public class GifGenerator {

	private static final Logger log = LoggerFactory.getLogger(GifGenerator.class);

	private static final String GIPHY_SEARCH_URL = "http://api.giphy.com/v1/gifs/search?q=<<key>>&limit=10&api_key=dc6zaTOxFJmzC";
	private static final String GIPHY_TRENDING_URL = "http://api.giphy.com/v1/gifs/trending?limit=50&api_key=dc6zaTOxFJmzC";
	private static final String GIPHY_RANDOM = "http://api.giphy.com/v1/gifs/random?limit=100&api_key=dc6zaTOxFJmzC";
	private static final String GIPHY_TRANSLATE = "http://api.giphy.com/v1/gifs/translate?s=<<key>>&limit=10&api_key=dc6zaTOxFJmzC";
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
	 *            contained in the list. the original urls.
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	@Lock(LockType.READ)
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public GiphyImageContainer searchGifForKeyword(String keyword)
			throws JsonParseException, JsonMappingException, IOException {
		String url = null;
		if (keyword == null) {
			log.debug("Searching trending gif because null keyword");
			url = giphyTrendingUrl();
		} else {
			log.debug("Searching gif for keyword: {}", keyword);
			url = giphySearchUrl(keyword);
		}

		GiphyData giphyData = fetchGifsFromUrl(url);

		// nothing has found for the keyword
		if (giphyData.getData().isEmpty()) {
			return null;
		}

		return selectRandomImage(giphyData.getData());

	}

	/**
	 * Picks a random gif image at last
	 * 
	 * @return not null random image
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonParseException
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public GiphyImageContainer pickRandomImage() throws JsonParseException, JsonMappingException, IOException {
		return fetchGifsFromUrl(GIPHY_RANDOM).getData().get(0);
	}

	private GiphyData fetchGifsFromUrl(String url) throws JsonParseException, JsonMappingException, IOException {
		Response result = client.target(url).request().get();
		String rawContent = result.readEntity(String.class);
		result.close();

		ObjectMapper mapper = new ObjectMapper();
		return mapper.readValue(rawContent, GiphyData.class);
	}

	private GiphyImageContainer selectRandomImage(List<GiphyImageContainer> images) {
		return images.get(random.nextInt(images.size()));
	}

	private String giphyTrendingUrl() {
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
