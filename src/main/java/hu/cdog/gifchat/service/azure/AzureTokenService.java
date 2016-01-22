package hu.cdog.gifchat.service.azure;

import java.io.IOException;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
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
public class AzureTokenService {

	private static final Logger log = LoggerFactory.getLogger(AzureTokenService.class);

	private static final String OAUTH_URL = "https://datamarket.accesscontrol.windows.net/v2/OAuth2-13";
	private static final ObjectMapper mapper = new ObjectMapper();
	public static final int OAUTH_RETRY_TIME = 12000;

	private static final String MS_TRANSLATE_PROP_FILE = "/mssecret.properties";
	private static final String CLIENT_ID_SPEECH = "client_id_speech";
	private static final String CLIENT_SECRET_SPEECH = "client_secret_speech";
	private static final String CLIENT_ID_TRANSLATE = "client_id_translate";
	private static final String CLIENT_SECRET_TRANSLATE = "client_secret_translate";
	private static final String SCOPE_TRANSLATE = "scope_translate";
	private static final String SCOPE_SPEECH = "scope_speech";
	private static final String GRANT_TYPE = "grant_type";

	private MsTranslateApiResponse translateToken = null;
	private MsTranslateApiResponse speechToken = null;

	// this is a big guy it can have its on client
	private final Client client = ClientBuilder.newClient();

	@Resource
	TimerService timerService;

	@PostConstruct
	public void wakeUp() {
		getNewApiTokenKeys(null);
	}

	@PreDestroy
	public void destroy() {
		if (client != null) {
			client.close();
		}
	}

	@Timeout
	public void getNewApiTokenKeys(Timer timer) {
		try {
			log.debug("Trying to acquire tokens from url {}", OAUTH_URL);
			Properties mssecrets = new Properties();
			mssecrets.load(AzureTokenService.class.getResourceAsStream(MS_TRANSLATE_PROP_FILE));

			fetchSpeechToken(mssecrets);
			fetchTranslateToken(mssecrets);
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		} finally {
			TimerConfig tc = new TimerConfig();
			tc.setPersistent(false);
			// if access token available read the expiration time
			if (translateToken != null && speechToken != null) {
				// a little bit before the end
				Long minTime = Math.min(translateToken.getExpires_in(), speechToken.getExpires_in());
				long revalidateDuration = (long) Math.floor(minTime * 1000 * 0.95);
				log.debug("Ticket expires after {} secs and revalidate ticket after {} ms", minTime,
						revalidateDuration);
				timerService.createSingleActionTimer(revalidateDuration, tc);
			} else {
				// retry after two minute
				log.info("Could not acquire one/or more tokens retrying again in {} seconds.", OAUTH_RETRY_TIME);
				timerService.createSingleActionTimer(OAUTH_RETRY_TIME, tc);
			}
		}

	}

	private void fetchTranslateToken(Properties properties) {
		log.debug("Acquire translate token");
		try {
			WebTarget target = client.target(OAUTH_URL);

			Form form = getRequestFormForScope(properties, CLIENT_ID_TRANSLATE, CLIENT_SECRET_TRANSLATE,
					SCOPE_TRANSLATE);

			Response response = target.request().post(Entity.form(form));
			String readEntity = response.readEntity(String.class);

			translateToken = mapper.readValue(readEntity, MsTranslateApiResponse.class);
			log.debug("Translate Token successfully acquired");
		} catch (IOException e) {
			log.error(e.getMessage(), e);
			translateToken = null;
		}

	}

	private void fetchSpeechToken(Properties properties) {
		log.debug("Acquire speech token");
		try {
			WebTarget target = client.target(OAUTH_URL);

			Form form = getRequestFormForScope(properties, CLIENT_ID_SPEECH, CLIENT_SECRET_SPEECH, SCOPE_SPEECH);

			Response response = target.request().post(Entity.form(form));
			String readEntity = response.readEntity(String.class);

			speechToken = mapper.readValue(readEntity, MsTranslateApiResponse.class);
			log.debug("Speech Token successfully acquired");
		} catch (IOException e) {
			log.error(e.getMessage(), e);
			speechToken = null;
		}

	}

	private Form getRequestFormForScope(Properties properties, String clientIdKey, String secretKey, String scopeKey) {
		Form form = new Form();
		form.param("client_id", properties.getProperty(clientIdKey));
		form.param("client_secret", properties.getProperty(secretKey));
		form.param("scope", properties.getProperty(scopeKey));
		form.param("grant_type", properties.getProperty(GRANT_TYPE));

		return form;
	}

	public String getSpeechToken() {
		if (speechToken != null) {
			return speechToken.getAccess_token();
		} else {
			return null;
		}
	}

	public String getTranslatorToken() {
		if (translateToken != null) {
			return translateToken.getAccess_token();
		} else {
			return null;
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
