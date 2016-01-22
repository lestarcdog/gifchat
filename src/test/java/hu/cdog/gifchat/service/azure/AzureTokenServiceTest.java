package hu.cdog.gifchat.service.azure;

import javax.ejb.TimerService;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import hu.cdog.gifchat.service.azure.AzureTokenService;

@RunWith(MockitoJUnitRunner.class)
public class AzureTokenServiceTest {

	@Mock
	TimerService timerService;

	@InjectMocks
	AzureTokenService tokenService;

	@Test
	public void acquireTokens() {
		tokenService.getNewApiTokenKeys(null);

		System.out.println(tokenService.getSpeechToken());
		System.out.println(tokenService.getTranslatorToken());
		Assert.assertNotNull(tokenService.getSpeechToken());
		Assert.assertNotNull(tokenService.getTranslatorToken());
	}

}
