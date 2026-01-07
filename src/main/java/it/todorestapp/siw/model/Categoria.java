package it.todorestapp.siw.model;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Entita JPA che rappresenta una categoria.
 * @author Jacopo Orrù
 */
@Entity
@Table(name ="Categorie")
public class Categoria {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nomeCategoria;

	/**
	 * Crea una categoria con nome.
	 *
	 * @param nomeCategoria nome della categoria
	 */
	public Categoria(String nomeCategoria) {
		this.nomeCategoria = nomeCategoria;
	}

	/**
	 * Costruttore vuoto richiesto da JPA.
	 */
	public Categoria() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNomeCategoria() {
		return nomeCategoria;
	}

	public void setNomeCategoria(String nomeCategoria) {
		this.nomeCategoria = nomeCategoria;
	}

	/**
	 * Confronta per uguaglianza basandosi sull'id.
	 *
	 * @param obj altro oggetto
	 * @return true se stesso tipo e stesso id
	 */
	@Override
	public boolean equals(Object obj) {
		if(obj == null || obj.getClass() != this.getClass())
			return false;
		Categoria that = (Categoria) obj;
		return this.id == that.getId();
	}

	/**
	 * Hash basato sull'id.
	 *
	 * @return hash code
	 */
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}



}
