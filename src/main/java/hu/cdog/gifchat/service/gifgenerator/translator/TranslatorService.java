package hu.cdog.gifchat.service.gifgenerator.translator;

import java.io.IOException;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerConfig;
import javax.ejb.TimerService;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

@Singleton
public class TranslatorService {

	private static final Logger log = LoggerFactory.getLogger(TranslatorService.class);

	private static final String OAUTH_URL = "https://datamarket.accesscontrol.windows.net/v2/OAuth2-13";
	private static final String TRANSLATE_URL = "http://api.microsofttranslator.com/V2/Ajax.svc/Translate";
	private static final ObjectMapper mapper = new ObjectMapper();
	public static final int OAUTH_RETRY_TIME = 6000;

	private static final String MS_TRANSLATE_PROP_FILE = "/mstranslate.properties";
	private static final String CLIENT_ID = "client_id";
	private static final String CLIENT_SECRET = "client_secret";
	private static final String SCOPE = "scope";
	private static final String GRANT_TYPE = "grant_type";

	private MsTranslateApiResponse accessToken = null;
	private final Client client = ClientBuilder.newClient();

	@Resource
	TimerService timerService;

	@PostConstruct
	public void wakeUp() {
		getNewApiTokenKey(null);
	}

	@PreDestroy
	public void destroy() {
		if (client != null) {
			client.close();
		}
	}

	@Timeout
	public void getNewApiTokenKey(Timer timer) {
		try {
			log.debug("Trying to acquire token from {}", OAUTH_URL);
			Properties msTranslate = new Properties();
			msTranslate.load(TranslatorService.class.getResourceAsStream(MS_TRANSLATE_PROP_FILE));

			WebTarget target = client.target(OAUTH_URL);

			Form form = new Form();
			form.param("client_id", msTranslate.getProperty(CLIENT_ID));
			form.param("client_secret", msTranslate.getProperty(CLIENT_SECRET));
			form.param("scope", msTranslate.getProperty(SCOPE));
			form.param("grant_type", msTranslate.getProperty(GRANT_TYPE));

			Response response = target.request().post(Entity.form(form));
			String readEntity = response.readEntity(String.class);

			accessToken = mapper.readValue(readEntity, MsTranslateApiResponse.class);
			log.debug("Token successfully acquired");
		} catch (IOException e) {
			log.error(e.getMessage(), e);
			accessToken = null;

		} finally {
			TimerConfig tc = new TimerConfig();
			tc.setPersistent(false);
			// if access token available read the expiration time
			if (accessToken != null) {
				// a little bit before the end
				long revalidateDuration = (long) Math.floor(accessToken.getExpires_in() * 1000 * 0.95);
				log.debug("Ticket expires after {} secs and revalidate ticket after {} ms", accessToken.getExpires_in(),
						revalidateDuration);
				timerService.createSingleActionTimer(revalidateDuration, tc);
			} else {
				// retry after one minute
				timerService.createSingleActionTimer(OAUTH_RETRY_TIME, tc);
			}
		}
	}

	@Lock(LockType.READ)
	public String translate(String msg) {
		log.debug("Translating {}", msg);
		if (accessToken != null) {
			WebTarget translate = client.target(TRANSLATE_URL).queryParam("text", msg).queryParam("to", "en");

			Response response = translate.request().header("Authorization", "Bearer " + accessToken.getAccess_token())
					.get();
			String translatedMessage = response.readEntity(String.class);
			log.debug(translatedMessage);
			if (response.getStatus() != 200) {
				return msg;
			}
			return translatedMessage;
		} else {
			return msg;
		}

	}

	public static class MsTranslateApiResponse {
		private String access_token;
		private String token_type;
		private Long expires_in;
		private String scope;

		public String getAccess_token() {
			return access_token;
		}

		public void setAccess_token(String access_token) {
			this.access_token = access_token;
		}

		public String getToken_type() {
			return token_type;
		}

		public void setToken_type(String token_type) {
			this.token_type = token_type;
		}

		public Long getExpires_in() {
			return expires_in;
		}

		public void setExpires_in(Long expires_in) {
			this.expires_in = expires_in;
		}

		public String getScope() {
			return scope;
		}

		public void setScope(String scope) {
			this.scope = scope;
		}

		@Override
		public String toString() {
			return "MsTranslateApiResponse [access_token=" + access_token + ", token_type=" + token_type
					+ ", expires_in=" + expires_in + ", scope=" + scope + "]";
		}

	}
}
