package it.todorestapp.siw.service;

import it.todorestapp.siw.repository.TodoRepository;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.EntityManager;

import it.todorestapp.siw.model.Categoria;
import it.todorestapp.siw.model.Todo;

/**
 * Service layer per la gestione dei todo.
 * @author Jacopo Orrù
 */
public class TodoService {

	private TodoRepository repository;

	/**
	 * Inizializza il repository dei todo.
	 */
	public TodoService() {
		this.repository = new TodoRepository();
	}

	/**
	 * Crea un nuovo todo.
	 *
	 * @param name nome del todo
	 * @param description descrizione
	 * @param creationTimeStamp timestamp di creazione
	 * @param lastUpdateTimeStamp timestamp ultimo aggiornamento
	 * @param completed stato di completamento
	 * @param categoria categoria associata
	 * @return todo persistito
	 */
	public Todo create(String name,String description,LocalDateTime creationTimeStamp,LocalDateTime lastUpdateTimeStamp,boolean completed, Categoria categoria) {
		EntityManager em = this.repository.getEntityManager();
		try {
			em.getTransaction().begin();
			Todo nuovo = new Todo(name,description,creationTimeStamp,lastUpdateTimeStamp,completed,categoria);
			Todo creato = this.repository.save(nuovo);
			em.getTransaction().commit();
			return creato;
		}catch(Exception e) {
			if(em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			throw new RuntimeException("Errore durante l'inserimento, controlla la connessione", e);
		}finally {
			em.close();
		}
	}

	/**
	 * Recupera tutti i todo.
	 *
	 * @return lista di todo o null se non trovati
	 */
	public List<Todo> findAll(){
		EntityManager em = this.repository.getEntityManager();
		try {
			em.getTransaction().begin();
			List<Todo> todoes = this.repository.findAll();
			if(todoes == null) {
				em.getTransaction().rollback();
				return null;
				
			}
			em.getTransaction().commit();
			return todoes;
		}catch(Exception e) {
			if(em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			throw new RuntimeException("Errore durante l'inserimento, controlla la connessione", e);
		}finally{
			em.close();
		}
	}

	/**
	 * Recupera un todo per id.
	 *
	 * @param id identificativo del todo
	 * @return todo trovato o null se assente
	 */
	public Todo findById(Long id) {
		EntityManager em = this.repository.getEntityManager();
		try {
			em.getTransaction().begin();
			Todo t = this.repository.findById(id);
			if(t == null) {
				em.getTransaction().rollback();
				return null;
				
			}
			em.getTransaction().commit();
			return t;
		}catch(Exception e) {
			if(em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			throw new RuntimeException("Errore durante l'inserimento, controlla la connessione", e);
		}finally {
			em.close();
		}
	}


	/**
	 * Aggiorna un todo esistente.
	 *
	 * @param t todo con i nuovi valori
	 * @return todo aggiornato o null se non trovato
	 */
	public Todo update(Todo t) {
		EntityManager em = this.repository.getEntityManager();
		try {
			em.getTransaction().begin();
			Todo DaAggiornare = this.repository.findById(t.getId());
			if(DaAggiornare == null) {
				em.getTransaction().rollback();
				return null;
			}
			DaAggiornare.setName(t.getName());
			DaAggiornare.setDescription(t.getDescription());
			DaAggiornare.setCreationTimeStamp(t.getCreationTimeStamp());
			DaAggiornare.setLastUpdateTimeStamp(t.getLastUpdateTimeStamp());
			DaAggiornare.setCompleted(t.isCompleted());
			DaAggiornare.setCategoria(t.getCategoria());
			Todo aggiornato = this.repository.save(DaAggiornare);
			em.getTransaction().commit();
			return aggiornato;

		}catch(Exception e) {
			if(em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			throw new RuntimeException("Errore durante l'inserimento, controlla la connessione", e);
		}finally {
			em.close();
		}
	}

	
	/**
	 * Cancella un todo per id.
	 *
	 * @param id identificativo del todo
	 * @return true se eliminato, false se non trovato
	 */
	public boolean deleteById(Long id) {
		EntityManager em = this.repository.getEntityManager();

		try {
			em.getTransaction().begin();
			Todo eliminare = this.repository.findById(id);
			if(eliminare == null) {
				em.getTransaction().rollback();
				return false;
			}
			this.repository.delete(eliminare);
			em.getTransaction().commit();
			return true;
		}catch(Exception e) {
			if(em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			throw new RuntimeException("Errore durante l'inserimento, controlla la connessione", e);
		}finally {

		}
	}

	 public boolean existById(Long id) {
   	  EntityManager em = this.repository.getEntityManager();
 	    try {
 	        em.getTransaction().begin();
 	        boolean trovato = this.repository.existingById(id);
 	        em.getTransaction().commit();
 	        return trovato;
 	    }catch(Exception e) {
 	    	if(em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
 	    	throw new RuntimeException("Errore durante l'inserimento, controlla la connessione", e);
 	    }finally {
 	    	em.close();
 	    }
   }
    

















}
