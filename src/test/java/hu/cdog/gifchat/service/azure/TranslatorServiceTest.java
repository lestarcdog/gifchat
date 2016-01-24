package hu.cdog.gifchat.service.azure;

import java.io.IOException;

import javax.ejb.TimerService;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import hu.cdog.gifchat.service.WebClientService;

@RunWith(MockitoJUnitRunner.class)
public class TranslatorServiceTest {

	@Mock
	TimerService ts;

	@Spy
	WebClientService webclientservice;

	@InjectMocks
	TranslatorService translatorService;

	@Test
	public void translateService() throws LineUnavailableException, UnsupportedAudioFileException, IOException {
		AzureTokenService tokenService = new AzureTokenService();
		tokenService.timerService = ts;
		tokenService.getNewApiTokenKeys(null);

		translatorService.tokenService = tokenService;
		String translate = translatorService.translate("hello én vagyok csaba");
		System.out.println(translate);
	}

}
