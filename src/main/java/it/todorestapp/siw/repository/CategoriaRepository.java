package it.todorestapp.siw.repository;

import java.util.List;

import javax.persistence.TypedQuery;

import it.todorestapp.siw.model.*;

/**
 * Repository per l'entita {@link Categoria}.
 * @author Jacopo Orrù
 */
public class CategoriaRepository extends SimpleRepositoryImpl<Categoria> {

	/**
	 * Inizializza il repository sulla classe {@link Categoria}.
	 */
	public CategoriaRepository() {
		super(Categoria.class);
	}
    
	/**
	 * Cerca categorie per nome esatto.
	 *
	 * @param name nome della categoria
	 * @return lista di categorie
	 */
	public List<Categoria> findByName(String name){
		TypedQuery<Categoria> query = null;
	    query = this.getEntityManager().createQuery("SELEC C FROM Categoria WHERE nomeCategoria = :name", Categoria.class);
	    query.setParameter("name", name);
	    return query.getResultList();
	}
	
	
	/* RICORDA DI USARE CONCAT */
	/**
	 * Cerca categorie per keyword.
	 *
	 * @param keyword keyword di ricerca
	 * @return lista di categorie
	 */
	public List<Categoria> findByKeywork(String keyword){
		TypedQuery<Categoria> query = null;
		query = this.getEntityManager().createQuery("SELECT c FROM Categoria c WHERE nomeCategoria = :keyword", Categoria.class);
		query.setParameter("keyword", keyword);
		return query.getResultList();
	}
}
