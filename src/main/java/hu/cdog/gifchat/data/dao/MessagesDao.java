package hu.cdog.gifchat.data.dao;

import java.time.LocalDateTime;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.google.common.collect.ImmutableMap;

import hu.cdog.gifchat.GifChatConstants;
import hu.cdog.gifchat.model.entities.UserMessage;

@Stateless
public class MessagesDao extends AbstractDao {

	@PersistenceContext(unitName = "storychat")
	EntityManager em;

	private static final String GET_BEFORE_TIME = "SELECT u FROM UserMessage u WHERE u.sentTime < :beforeTime ORDER BY u.sentTime DESC";

	public List<UserMessage> getCurrentsMessages() {
		return query("SELECT u FROM UserMessage u", UserMessage.class, GifChatConstants.CURRENT_IMAGE_RETURN_LIMIT);
	}

	public List<UserMessage> earlierThan(LocalDateTime treshold) {
		return query(GET_BEFORE_TIME, UserMessage.class, ImmutableMap.of("beforeTime", treshold));
	}

	@Override
	protected EntityManager getEntityManager() {
		return em;
	}

}
