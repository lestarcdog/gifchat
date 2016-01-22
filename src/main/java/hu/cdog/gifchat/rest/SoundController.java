package hu.cdog.gifchat.rest;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.StreamingOutput;

import org.apache.commons.io.IOUtils;

@Path("/sound")
public class SoundController {

	@GET
	@Produces("audio/mpeg3")
	public StreamingOutput getSound(String soundId) {
		final InputStream sound = SoundController.class.getResourceAsStream("/valuska.mp3");
		return new StreamingOutput() {

			@Override
			public void write(OutputStream output) throws IOException, WebApplicationException {
				IOUtils.copy(sound, output);
				sound.close();
			}
		};
	}

}
