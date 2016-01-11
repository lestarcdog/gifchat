package hu.cdog.gifchat.service.gifgenerator.translator;

import java.io.IOException;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerConfig;
import javax.ejb.TimerService;
import javax.inject.Inject;
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

	private static final String OAUTH_URL = "https://datamarket.accesscontrol.windows.net/v2/OAuth2-13";
	private static final Logger log = LoggerFactory.getLogger(TranslatorService.class);
	private static final ObjectMapper mapper = new ObjectMapper();
	public static final int OAUTH_RETRY_TIME = 6000;

	private static final String MS_TRANSLATE_PROP_FILE = "/mstranslate.properties";
	private static final String CLIENT_ID = "client_id";
	private static final String CLIENT_SECRET = "client_secret";
	private static final String SCOPE = "scope";
	private static final String GRANT_TYPE = "grant_type";

	private MsTranslateApiResponse accessToken = null;
	private final Client client = ClientBuilder.newClient();

	@Inject
	TimerService timerService;

	@PostConstruct
	public void wakeUp() {
		getNewApiTokenKey(null);
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
				long revalidateDuration = (long) Math.floor(accessToken.getExpires_in() * 0.9);
				timerService.createSingleActionTimer(revalidateDuration, tc);
			} else {
				// retry after one minute
				timerService.createSingleActionTimer(OAUTH_RETRY_TIME, tc);
			}
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
