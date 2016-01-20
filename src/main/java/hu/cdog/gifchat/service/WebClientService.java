package hu.cdog.gifchat.service;

import java.util.Objects;

import javax.annotation.PreDestroy;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

@Singleton
public class WebClientService {

	private final Client client = ClientBuilder.newClient();

	@PreDestroy
	public void destroy() {
		if (client != null) {
			client.close();
		}
	}

	@Lock(LockType.READ)
	public WebTarget createTarget(String targetUrl) {
		Objects.requireNonNull(targetUrl);

		return client.target(targetUrl);
	}
}
