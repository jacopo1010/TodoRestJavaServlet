package it.todorestapp.siw.repository;

import java.util.List;

import javax.persistence.EntityManager;

import it.todorestapp.siw.data.JpaUtil;

/**
 * Implementazione base del repository JPA.
 *
 * @param <T> tipo dell'entita gestita
 */
public class SimpleRepositoryImpl<T> implements SimpleRepository<T> {

	private EntityManager em;
	private Class<T> domainClass;
	
	/**
	 * Crea un repository per la classe fornita.
	 *
	 * @param domainClass classe dell'entita
	 */
	public  SimpleRepositoryImpl(Class<T> domainClass){
		this.domainClass = domainClass;
	}
	
	
	@Override
	/**
	 * {@inheritDoc}
	 */
	public EntityManager getEntityManager() {
		return this.em = JpaUtil.getEntityManagerFactory().createEntityManager();
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public void setEntityManager(EntityManager em) {
		this.em = em;
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public T save(T entity) {
		T persistEntity = entity;
		this.em.persist(persistEntity);
		return persistEntity;
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public List<T> findAll() {
		return this.em.createQuery("select o from " + this.domainClass.getName() + " o", this.domainClass).getResultList();
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public T findById(Long id) {
		 return this.em.find(this.domainClass, id);
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public void delete(T t) {
		this.em.remove(t);
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public void deleteAll() {
		this.em.createQuery("DELETE FROM" + this.domainClass.getName()).executeUpdate();
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public int count() {
		return (int)this.em.createQuery("SELECT COUNT(id) FROM" + this.domainClass.getName()).getSingleResult();
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public boolean existingById(Long id) {
	     return this.findById(id) != null;
	}

}
