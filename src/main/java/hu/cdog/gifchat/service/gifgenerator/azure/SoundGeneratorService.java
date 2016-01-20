package hu.cdog.gifchat.service.gifgenerator.azure;

import java.io.IOException;
import java.io.InputStream;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hu.cdog.gifchat.exception.GifChatException;
import hu.cdog.gifchat.service.WebClientService;

@Stateless
public class SoundGeneratorService {

	private static final Logger log = LoggerFactory.getLogger(SoundGeneratorService.class);

	private static final String SPEECH_URL = "https://speech.platform.bing.com/synthesize";
	private static final String OUTPUT_FORMAT = "riff-16khz-16bit-mono-pcm";
	private static final String CONTENT_TYPE = "application/ssml+xml";
	private static final String SSML_TEMPLATE = "<speak version='1.0' xml:lang='en-US'><voice xml:lang='en-US' xml:gender='Female' "
			+ "name='Microsoft Server Speech Text to Speech Voice (en-US, ZiraRUS)'>%s</voice></speak>";

	@EJB
	AzureTokenService tokenService;

	@EJB
	WebClientService webTargetService;

	public GeneratedSound generateSound(String message) {
		log.debug("Generating sound for {}", message);
		WebTarget target = webTargetService.createTarget(SPEECH_URL);

		Builder request = target.request().header("Content-Type", CONTENT_TYPE)
				.header("X-Microsoft-OutputFormat", OUTPUT_FORMAT)
				.header("Authorization", "Bearer " + tokenService.getSpeechToken()).header("User-Agent", "gifchat");
		String ssml;
		try {
			ssml = insertIntoTemplate(message);
			Response response = request.post(Entity.text(ssml));
			log.debug("Response {} : {}", response.getStatus(), response.getHeaders());

			InputStream audioStream = response.readEntity(InputStream.class);
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			IOUtils.copy(audioStream, output);

			return new GeneratedSound(output.toByteArray(), message);
		} catch (GifChatException gf) {
			return new GeneratedSound(null, message);
		} catch (IOException e) {
			log.error("Could not generate sound", e);
			return new GeneratedSound(null, message);
		}
	}

	private String insertIntoTemplate(String message) throws GifChatException {
		// remove non alphanumeric chars
		String sanitized = message.replaceAll("[^a-zA-Z0-9]", "");
		if (!sanitized.isEmpty()) {
			return String.format(SSML_TEMPLATE, message);
		} else {
			throw new GifChatException("Invalid message to make it sound from " + message);
		}
	}

	public static class GeneratedSound {
		private final byte[] content;
		private final boolean generated;
		private final String message;

		public GeneratedSound(byte[] content, String message) {
			this.content = content;
			this.generated = (content != null);
			this.message = message;
		}

		public byte[] getContent() {
			return content;
		}

		public boolean isGenerated() {
			return generated;
		}

		public String getMessage() {
			return message;
		}

	}

}
