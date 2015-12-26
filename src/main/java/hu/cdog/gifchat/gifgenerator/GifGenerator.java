package hu.cdog.gifchat.gifgenerator;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import hu.cdog.gifchat.model.giphy.GiphyData;

@Singleton
public class GifGenerator {

	private static final String GIPHY_URL = "http://api.giphy.com/v1/gifs/search?q=<<key>>&api_key=dc6zaTOxFJmzC";
	private static final String OPENSHIFT_DATA_DIR = "OPENSHIFT_DATA_DIR";
	private String outputDir;

	private Client client;

	@PostConstruct
	public void init() {
		client = ClientBuilder.newClient();
		String outputDir = System.getenv(OPENSHIFT_DATA_DIR);
		if (outputDir == null || outputDir.isEmpty()) {
			outputDir = "/tmp/";
		}
	}

	@PreDestroy
	public void destroy() {
		client.close();
	}

	public void downloadFirst(String keyword) throws JsonParseException, JsonMappingException, IOException {
		String url = GIPHY_URL.replace("<<key>>", keyword);
		Response result = client.target(url).request().get();
		String rawContent = result.readEntity(String.class);
		result.close();

		ObjectMapper mapper = new ObjectMapper();
		GiphyData gipyData = mapper.readValue(rawContent, GiphyData.class);
		URL gifUrl = new URL(gipyData.getData().get(0).getImages().getDownsized().getUrl());
		BufferedImage gif = ImageIO.read(gifUrl);
		ImageReader reader = ImageIO.getImageReadersBySuffix("gif").next();

		// reader.
		// System.out.println(gipyData.getData().get(0).getImages().getDownsized().getUrl());

	}

}
