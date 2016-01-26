package hu.cdog.gifchat.service.azure;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hu.cdog.gifchat.service.WebClientService;

@Stateless
public class TranslatorService {

	private static final Logger log = LoggerFactory.getLogger(TranslatorService.class);

	private static final String TRANSLATE_URL = "http://api.microsofttranslator.com/V2/Ajax.svc/Translate";

	@EJB
	AzureTokenService tokenService;

	@EJB
	WebClientService webClient;

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public String translate(String msg) {
		log.debug("Translating {}", msg);
		if (tokenService.getTranslatorToken() != null) {
			WebTarget translate = webClient.createTarget(TRANSLATE_URL).queryParam("text", msg).queryParam("to", "en");

			Response response = translate.request()
					.header("Authorization", "Bearer " + tokenService.getTranslatorToken()).get();
			String translatedMessage = response.readEntity(String.class);

			log.debug(translatedMessage);
			if (response.getStatus() != 200) {
				return msg;
			}
			// TODO too empiric removal. it starts with a space and a double
			// quote and ends with one
			if (translatedMessage.endsWith("\"")) {
				return translatedMessage.substring(2, translatedMessage.length() - 1);
			} else {
				return translatedMessage;
			}
		} else {
			return msg;
		}

	}
}
