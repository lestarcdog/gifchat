package hu.cdog.gifchat.gifgenerator;

import java.io.IOException;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

public class GifGeneratorTest {

	@Test
	public void test() throws JsonParseException, JsonMappingException, IOException {
		GifGenerator gg = new GifGenerator();
		gg.init();

		gg.downloadFirst("cat");
	}

}
