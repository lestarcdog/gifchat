package hu.cdog.gifchat.gifgenerator;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.ImageWriter;
import javax.imageio.metadata.IIOMetadata;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

public class GifGeneratorTest {

	@Test
	public void test() throws JsonParseException, JsonMappingException, IOException {
		InputStream testInput = GifGeneratorTest.class.getResourceAsStream("/test.gif");
		String text = "anyad";
		ImageReader reader = ImageIO.getImageReadersBySuffix("gif").next();
		reader.setInput(ImageIO.createImageInputStream(testInput));

		System.out.println(reader.getNumImages(true));
		int num = reader.getNumImages(true);
		ImageWriter writer = ImageIO.getImageWriter(reader);
		IIOMetadata imageMetadata = reader.getImageMetadata(0);
		writer.setOutput(ImageIO.createImageOutputStream(new File("anyad.gif")));
		writer.prepareWriteSequence(imageMetadata);
		for (int i = 0; i < num; i++) {

			BufferedImage originalImg = reader.read(i, null);
			Graphics2D graphics = originalImg.createGraphics();
			graphics.drawString(text, 10.0f, 10.0f);
			writer.writeToSequence(new IIOImage(originalImg, null, imageMetadata), null);

		}
		writer.endWriteSequence();
		writer.dispose();
		reader.dispose();

	}

}
