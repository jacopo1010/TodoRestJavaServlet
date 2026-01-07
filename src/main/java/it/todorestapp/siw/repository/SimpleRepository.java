package it.todorestapp.siw.repository;

import java.util.List;

import javax.persistence.EntityManager;

/**
 * Contratto base per operazioni CRUD su entita JPA.
 *
 * @param <T> tipo dell'entita gestita
 * @author Jacopo Orrù
 */
public interface SimpleRepository<T> {

	/**
	 * Restituisce un EntityManager pronto all'uso.
	 *
	 * @return entity manager
	 */
	public EntityManager getEntityManager();

	/**
	 * Imposta l'EntityManager da usare.
	 *
	 * @param em entity manager
	 */
	public void setEntityManager(EntityManager em);

	/**
	 * Salva una nuova entita.
	 *
	 * @param entity entita da salvare
	 * @return entita persistita
	 */
	public T save(T entity);

	/**
	 * Recupera tutte le entita.
	 *
	 * @return lista di entita
	 */
	public List<T> findAll();

	/**
	 * Recupera un'entita per id.
	 *
	 * @param id identificativo
	 * @return entita trovata o null
	 */
	public T findById(Long id);

	/**
	 * Elimina una entita.
	 *
	 * @param t entita da rimuovere
	 */
	public void delete(T t);

	/**
	 * Elimina tutte le entita del tipo.
	 */
	public void deleteAll();

	/**
	 * Conta il numero di entita.
	 *
	 * @return totale entita
	 */
	public int count();

	/**
	 * Verifica l'esistenza di un'entita per id.
	 *
	 * @param id identificativo
	 * @return true se esiste
	 */
	public boolean existingById(Long id);
}

