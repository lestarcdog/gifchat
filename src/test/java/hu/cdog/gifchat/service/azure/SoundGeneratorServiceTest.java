package hu.cdog.gifchat.service.azure;

import java.io.IOException;

import javax.ejb.TimerService;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import hu.cdog.gifchat.service.WebClientService;
import hu.cdog.gifchat.service.azure.AzureTokenService;
import hu.cdog.gifchat.service.azure.SoundGeneratorService;
import hu.cdog.gifchat.service.azure.SoundGeneratorService.GeneratedSound;

@RunWith(MockitoJUnitRunner.class)
public class SoundGeneratorServiceTest {

	@Mock
	TimerService ts;

	AzureTokenService tokenService = new AzureTokenService();

	WebClientService webclientservice = new WebClientService();

	SoundGeneratorService soundService = new SoundGeneratorService();

	@Test
	public void generateSound() throws IOException {
		tokenService.timerService = ts;
		soundService.tokenService = tokenService;
		soundService.webTargetService = webclientservice;

		tokenService.getNewApiTokenKeys(null);

		GeneratedSound generateSound = soundService.generateSound("hülye köcsög");
		Assert.assertTrue(generateSound.isGenerated());
		// FileUtils.writeByteArrayToFile(new File("hang.wav"),
		// generateSound.getContent());

	}

}
