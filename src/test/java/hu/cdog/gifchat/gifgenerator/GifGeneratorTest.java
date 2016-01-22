package hu.cdog.gifchat.gifgenerator;

import java.io.IOException;
import java.util.Collections;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import hu.cdog.gifchat.model.giphy.GifImageFormats;
import hu.cdog.gifchat.service.giphy.GifGenerator;

public class GifGeneratorTest {

	public static GifGenerator generator;

	@BeforeClass
	public static void init() {
		generator = new GifGenerator();
		generator.init();
	}

	public static void close() {
		generator.destroy();
	}

	@Test
	public void getRandomGifCat() throws JsonParseException, JsonMappingException, IOException {
		GifImageFormats formats = generator.searchGifForKeyword("cat", Collections.emptyList());

		Assert.assertNotNull(formats);
	}

	@Test
	public void getTrendingForNullKeyword() throws JsonParseException, JsonMappingException, IOException {
		GifImageFormats formats = generator.searchGifForKeyword(null, Collections.emptyList());

		Assert.assertNotNull(formats);
	}

	@Test
	public void getNullForUnExistendKeyword() throws JsonParseException, JsonMappingException, IOException {
		GifImageFormats formats = generator.searchGifForKeyword("catasdffefewsaAwef fsda FEW aggastyán",
				Collections.emptyList());

		Assert.assertNull(formats);
	}

}
