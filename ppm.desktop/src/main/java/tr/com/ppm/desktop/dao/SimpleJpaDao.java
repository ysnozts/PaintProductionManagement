package tr.com.ppm.desktop.dao;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.List;

/**
 * @author ykarabalkan
 */
@Repository
@Transactional
public class SimpleJpaDao implements JpaDao {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public <T> void persistEntity(T entity) {
		entityManager.persist(entity);
	}

	@Override
	public <T> void removeEntity(T entity) {
		entityManager.remove(entity);
	}

	@Override
	public <T> T mergeEntity(T entity) {
		return entityManager.merge(entity);
	}

	@Override
	public <T> void refreshEntity(T entity) {
		entityManager.refresh(entity);
	}

	@Override
	public CriteriaBuilder getCriteriaBuilder() {
		return entityManager.getCriteriaBuilder();
	}

	public <T> List<T> findByCriteriaQuery(CriteriaQuery<T> criteriaQuery) {
		return entityManager.createQuery(criteriaQuery).getResultList();
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> getEntityList(Class<T> entityClass) {
		CriteriaBuilder criteriaBuilder = getCriteriaBuilder();
		CriteriaQuery<T> query = (CriteriaQuery<T>) criteriaBuilder.createQuery();
		Root<T> from = query.from(entityClass);
		query.select(from);
		return entityManager.createQuery(query).getResultList();
	}
}
