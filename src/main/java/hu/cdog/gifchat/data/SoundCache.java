package hu.cdog.gifchat.data;

import java.time.LocalDateTime;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Singleton;

import org.infinispan.Cache;
import org.infinispan.manager.CacheContainer;

import hu.cdog.gifchat.model.entities.UserMessage;

@Singleton
public class SoundCache {

	@Resource(lookup = "java:/infinispan/storychat")
	CacheContainer cacheContainer;

	Cache<LocalDateTime, UserMessage> soundCache = null;

	@PostConstruct
	public void init() {
		soundCache = cacheContainer.getCache("storychat-localcache");
	}
}
