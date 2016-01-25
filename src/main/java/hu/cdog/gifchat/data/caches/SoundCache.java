package hu.cdog.gifchat.data.caches;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;

import org.infinispan.Cache;
import org.infinispan.manager.CacheContainer;

import hu.cdog.gifchat.model.internal.GeneratedSound;

@Singleton
@Lock(LockType.READ)
public class SoundCache {

	@Resource(lookup = "java:/infinispan/storychat")
	CacheContainer cacheContainer;

	// key: messageId value: sound
	Cache<Integer, GeneratedSound> soundCache = null;

	@PostConstruct
	public void init() {
		soundCache = cacheContainer.getCache("storychat-localcache");
	}

	public void set(Integer id, GeneratedSound sound) {
		soundCache.put(id, sound);
	}

	public GeneratedSound get(Integer id) {
		return soundCache.get(id);
	}

}
