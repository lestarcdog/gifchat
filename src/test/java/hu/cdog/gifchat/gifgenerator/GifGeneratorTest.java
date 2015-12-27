package hu.cdog.gifchat.gifgenerator;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.metadata.IIOMetadata;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

public class GifGeneratorTest {

	@Test
	public void test() throws JsonParseException, JsonMappingException, IOException {
		InputStream testInput = GifGeneratorTest.class.getResourceAsStream("/test.gif");
		String text = "anyáúőd";
		ImageReader reader = ImageIO.getImageReadersBySuffix("gif").next();
		reader.setInput(ImageIO.createImageInputStream(testInput));

		int num = reader.getNumImages(true);
		ImageWriter writer = ImageIO.getImageWriter(reader);
		IIOMetadata imageMetadata = reader.getImageMetadata(0);
		writer.setOutput(ImageIO.createImageOutputStream(new File("anyad.gif")));
		writer.prepareWriteSequence(imageMetadata);
		Font font = new Font("Times New Roman", Font.PLAIN, 35);

		for (int i = 0; i < num; i++) {

			BufferedImage originalImg = reader.read(i, null);
			Graphics2D graphics = originalImg.createGraphics();

			graphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
			graphics.setFont(font);

			Stroke original = graphics.getStroke();

			graphics.setStroke(new BasicStroke(4, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
			graphics.setColor(Color.WHITE);

			TextLayout txtlayout = new TextLayout(text, font, graphics.getFontRenderContext());
			FontMetrics metrics = graphics.getFontMetrics();

			AffineTransform tx = graphics.getTransform();
			tx.setToTranslation(100, 100);

			// graphics.draw(txtlayout.getOutline(tx));
			Rectangle2D bounds = txtlayout.getBounds();
			bounds.setFrame(100, 100 - bounds.getHeight(), bounds.getWidth(), bounds.getHeight());
			graphics.draw(bounds);

			graphics.setColor(Color.BLACK);
			graphics.setStroke(original);
			graphics.drawString(text, 100, 100);

			ImageWriteParam p = new ImageWriteParam(null);
			writer.writeToSequence(new IIOImage(originalImg, null, imageMetadata), p);

			graphics.dispose();

		}
		writer.endWriteSequence();
		writer.dispose();
		reader.dispose();

	}

	@Test
	public void test2() throws IOException {
		InputStream testInput = GifGeneratorTest.class.getResourceAsStream("/test.gif");
		String text = "anyáúőd";
		ImageReader reader = ImageIO.getImageReadersBySuffix("gif").next();
		reader.setInput(ImageIO.createImageInputStream(testInput));

		int num = reader.getNumImages(true);
		ImageWriter writer = ImageIO.getImageWritersByFormatName("jpg").next();
		IIOMetadata imageMetadata = reader.getImageMetadata(0);
		writer.setOutput(ImageIO.createImageOutputStream(new File("test2.jpg")));
		Font font = new Font("Times New Roman", Font.PLAIN, 35);

		BufferedImage originalImg = reader.read(1, null);
		Graphics2D graphics = originalImg.createGraphics();

		// graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
		// RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

		graphics.setFont(font);

		Stroke original = graphics.getStroke();

		graphics.setStroke(new BasicStroke(10, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
		graphics.setColor(Color.WHITE);
		TextLayout txtlayout = new TextLayout(text, font, graphics.getFontRenderContext());
		AffineTransform tx = graphics.getTransform();
		tx.setToTranslation(100, 100);

		// graphics.draw(txtlayout.getOutline(tx));
		graphics.fill(txtlayout.getOutline(tx));

		graphics.setColor(Color.BLACK);
		graphics.setStroke(original);
		// graphics.drawString(text, 100, 100);

		writer.write(originalImg);

		graphics.dispose();

		writer.dispose();
		reader.dispose();
	}

}
