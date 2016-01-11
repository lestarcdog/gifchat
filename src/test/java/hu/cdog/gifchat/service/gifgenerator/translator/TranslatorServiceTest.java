package hu.cdog.gifchat.service.gifgenerator.translator;

import javax.ejb.TimerService;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TranslatorServiceTest {

	@Mock
	TimerService ts;

	@InjectMocks
	TranslatorService translatorService;

	@Test
	public void goodOauthKeyAccess() {
		String msg = "Hogy vagy Józsi?";
		translatorService.getNewApiTokenKey(null);
		String translate = translatorService.translate(msg);

		Assert.assertNotEquals(msg, translate);
	}

}
