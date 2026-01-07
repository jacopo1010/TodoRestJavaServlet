package it.todorestapp.siw.repository;

import java.util.List;

import javax.persistence.TypedQuery;

import it.todorestapp.siw.model.Todo;

/**
 * Repository per l'entita {@link Todo}.
 * @author Jacopo Orrù
 */
public class TodoRepository extends SimpleRepositoryImpl<Todo>{

	
	/**
	 * Inizializza il repository sulla classe {@link Todo}.
	 */
	public TodoRepository( ) {
		super(Todo.class);
	}

	/**
	 * Cerca todo per nome esatto.
	 *
	 * @param name nome del todo
	 * @return lista di todo
	 */
	public List<Todo> findByName(String name){
		TypedQuery<Todo> query = null;
		query = this.getEntityManager().createQuery("SELECT t FROM Todo t WHERE name = :name",Todo.class);
		query.setParameter("name", name);
		return query.getResultList();
	}

}
