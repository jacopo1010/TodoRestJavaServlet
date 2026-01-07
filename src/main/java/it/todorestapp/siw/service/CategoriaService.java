package it.todorestapp.siw.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import it.todorestapp.siw.data.JpaUtil;
import it.todorestapp.siw.model.Categoria;
import it.todorestapp.siw.repository.CategoriaRepository;

/**
 * Service layer per la gestione delle categorie.
 */
public class CategoriaService {

	private CategoriaRepository categoriaRepository;

	/**
	 * Inizializza il repository delle categorie.
	 */
	public CategoriaService() {
		this.categoriaRepository = new CategoriaRepository();
	}

	/**
	 * Espone il repository corrente.
	 *
	 * @return repository categorie
	 */
	public CategoriaRepository getCategoriaRepository() {
		return categoriaRepository;
	}

	/**
	 * Crea una nuova categoria.
	 *
	 * @param nome nome della categoria
	 * @return categoria persistita
	 */
	public Categoria create(String nome) {
		EntityManager em  = this.categoriaRepository.getEntityManager();
		try {
			em.getTransaction().begin();
			Categoria c = new Categoria(nome);
			this.categoriaRepository.save(c);
			em.getTransaction().commit();
			em.close();
			return c;
		}catch (Exception e) {
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback(); // Importante: annulla se c'è errore
			}
			// Usiamo RuntimeException così non serve il 'throws' nella firma
			throw new RuntimeException("Errore durante l'inserimento, controlla la connessione", e);
		} finally {
			// Chiudiamo SOLO l'EntityManager, MAI la Factory!
			if (em.isOpen()) {
				em.close();
			}
		}
	}


	/**
	 * Cerca categorie con keyword esatta.
	 *
	 * @param keyword keyword da cercare
	 * @return lista di categorie
	 */
	public List<Categoria> findByKeyword(String keyword){
		EntityManager em  = this.categoriaRepository.getEntityManager();
		try {
			em.getTransaction().begin();
			List<Categoria> list = this.categoriaRepository.findByKeywork(keyword);
			em.getTransaction().commit();
			em.close();
			return list;
		}finally {
			if (em.isOpen()) {
				em.close();
			}
		}
	}

	/**
	 * Recupera tutte le categorie.
	 *
	 * @return lista di categorie
	 */
	public List<Categoria> findAll(){
		EntityManager em  = this.categoriaRepository.getEntityManager();
		try {
			em.getTransaction().begin();
			List<Categoria> list = this.categoriaRepository.findAll();
			em.getTransaction().commit();
			em.close();
			return list;
		}finally {
			if (em.isOpen()) {
				em.close();
			}
		}
	}


	/**
	 * Recupera una categoria per id.
	 *
	 * @param id identificativo della categoria
	 * @return categoria trovata o null se assente
	 */
	public Categoria findById(Long id){
		EntityManager em  = this.categoriaRepository.getEntityManager();
		try {
			em.getTransaction().begin();
			Categoria categoria = this.categoriaRepository.findById(id);
			em.getTransaction().commit();
			em.close();
			if(categoria != null)
				return categoria;
			else
				return null;
		}finally {
			if (em.isOpen()) {
				em.close();
			}
		}
	}

	/**
	 * Aggiorna i dati di una categoria.
	 *
	 * @param categoria categoria con i nuovi valori
	 * @return categoria aggiornata o null se non trovata
	 */
	public Categoria update(Categoria categoria) {
		EntityManager em  = this.categoriaRepository.getEntityManager();
		try {
			em.getTransaction().begin();
			Categoria c = this.categoriaRepository.findById(categoria.getId());
			if( c != null) {
				c.setNomeCategoria(categoria.getNomeCategoria());
				this.categoriaRepository.save(c);
				em.getTransaction().commit();
				em.close();
				return c;
			}
			em.getTransaction().rollback();
			return null;
		}catch (Exception e) {
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback(); // Importante: annulla se c'è errore
			}
			// Usiamo RuntimeException così non serve il 'throws' nella firma
			throw new RuntimeException("Errore durante l'inserimento, controlla la connessione", e);
		} finally {
			// Chiudiamo SOLO l'EntityManager, MAI la Factory!
			if (em.isOpen()) {
				em.close();
			}
		}
	}


	/**
	 * Cancella una categoria per id.
	 *
	 * @param id identificativo della categoria
	 * @return true se eliminata, false se non trovata
	 */
	public boolean deleteById(Long id) {
	    EntityManager em = categoriaRepository.getEntityManager();
	    try {
	        em.getTransaction().begin();

	        Categoria c = this.categoriaRepository.findById(id);
	        if (c == null) {
	            em.getTransaction().rollback();
	            return false;
	        }

	        this.categoriaRepository.delete(c);
	        em.getTransaction().commit();
	        return true;
	    } catch (Exception e) {
	        if (em.getTransaction().isActive()) em.getTransaction().rollback();
	        throw e;
	    } finally {
	        em.close();
	    }
	}

    public boolean existById(Long id) {
    	  EntityManager em = this.categoriaRepository.getEntityManager();
  	    try {
  	        em.getTransaction().begin();
  	        boolean trovato = this.categoriaRepository.existingById(id);
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
