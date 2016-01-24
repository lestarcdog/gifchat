package hu.cdog.gifchat.data.dao;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import hu.cdog.gifchat.model.entities.AbstractEntity;

public abstract class AbstractDao {

	public void persist(AbstractEntity object) {
		getEntityManager().persist(object);
	}

	public <T extends AbstractEntity> List<T> query(String jpql, Class<T> clazz, Map<String, Object> parameters,
			Integer limit) {
		TypedQuery<T> query = getEntityManager().createQuery(jpql, clazz);
		if (parameters != null) {
			parameters.entrySet().forEach(l -> query.setParameter(l.getKey(), l.getValue()));
		}
		if (limit != null) {
			query.setMaxResults(limit);
		}
		return query.getResultList();
	}

	public <T extends AbstractEntity> List<T> query(String jpql, Class<T> clazz, Map<String, Object> parameters) {
		return query(jpql, clazz, parameters, null);
	}

	public <T extends AbstractEntity> List<T> query(String jpql, Class<T> clazz, Integer limit) {
		return query(jpql, clazz, null, limit);
	}

	public <T extends AbstractEntity> List<T> query(String jpql, Class<T> clazz) {
		return query(jpql, clazz, null, null);
	}

	public <T extends AbstractEntity> Optional<T> find(Object id, Class<T> clazz) {
		return Optional.ofNullable(getEntityManager().find(clazz, id));
	}

	protected abstract EntityManager getEntityManager();
}
