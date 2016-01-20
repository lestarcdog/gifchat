package hu.cdog.gifchat.service.gifgenerator.translator;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.ejb.TimerService;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import hu.cdog.gifchat.service.WebClientService;
import hu.cdog.gifchat.service.gifgenerator.azure.AzureTokenService;
import hu.cdog.gifchat.service.gifgenerator.azure.TranslatorService;

@RunWith(MockitoJUnitRunner.class)
public class TranslatorServiceTest {

	@Mock
	TimerService ts;

	@Mock
	AzureTokenService tokenService;

	@Spy
	WebClientService webclientservice;

	@InjectMocks
	TranslatorService translatorService;

	@Test
	public void soundService() throws LineUnavailableException, UnsupportedAudioFileException, IOException {
		String url = "https://speech.platform.bing.com/synthesize";

		Client c = ClientBuilder.newClient();
		WebTarget target = c.target(url);
		Builder request = target.request().header("Content-Type", "application/ssml+xml")
				.header("X-Microsoft-OutputFormat", "riff-16khz-16bit-mono-pcm")
				.header("Authorization", "Bearer " + tokenService.getSpeechToken()).header("User-Agent", "gifchat");

		String ssml = "<speak version='1.0' xml:lang='en-US'><voice xml:lang='en-US' xml:gender='Female' name='Microsoft Server Speech Text to Speech Voice (en-US, ZiraRUS)'>You thai bitch i love your tits</voice></speak>";
		Response response = request.post(Entity.text(ssml));
		System.out.println(response.getStatus() + " : " + response.getHeaders());

		InputStream audio = response.readEntity(InputStream.class);

		IOUtils.copy(audio, new FileOutputStream("hang.wav"));

	}

}
