package it.todorestapp.siw.model;

import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

/**
 * Entita JPA che rappresenta un todo.
 */
@Entity
@Table(name = "Todo")
public class Todo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false, length = 50)
	private String name;
	@Column(nullable = true, length = 255)
	private String description;
	@Column(nullable = false)
	private LocalDateTime creationTimeStamp;
	@Column(nullable = false)
	private LocalDateTime lastUpdateTimeStamp;
	@Column(nullable = false)
	private boolean completed;
	@OneToOne(fetch = FetchType.EAGER)
	@Column(nullable = false)
	private Categoria categoria;
	@ManyToOne
	@JoinColumn(nullable = false, name = "utente_id")
	private Utente utente;

	/**
	 * Crea un todo con i valori iniziali.
	 *
	 * @param name nome del todo
	 * @param description descrizione
	 * @param creationTimeStamp timestamp di creazione
	 * @param lastUpdateTimeStamp timestamp ultimo aggiornamento
	 * @param completed stato di completamento
	 * @param categoria categoria associata
	 * @author Jacopo Orrù
	 */
	public Todo(String name,String description,LocalDateTime creationTimeStamp,LocalDateTime lastUpdateTimeStamp,boolean completed, Categoria categoria) {
		this.name = name;
		this.description = description;
		this.creationTimeStamp = creationTimeStamp;
		this.lastUpdateTimeStamp = lastUpdateTimeStamp;
		this.completed = completed;
		this.categoria = categoria;
	}


	/**
	 * Costruttore vuoto richiesto da JPA.
	 */
	public Todo() {

	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public LocalDateTime getCreationTimeStamp() {
		return creationTimeStamp;
	}


	public void setCreationTimeStamp(LocalDateTime creationTimeStamp) {
		this.creationTimeStamp = creationTimeStamp;
	}


	public LocalDateTime getLastUpdateTimeStamp() {
		return lastUpdateTimeStamp;
	}


	public void setLastUpdateTimeStamp(LocalDateTime lastUpdateTimeStamp) {
		this.lastUpdateTimeStamp = lastUpdateTimeStamp;
	}


	public boolean isCompleted() {
		return completed;
	}


	public void setCompleted(boolean completed) {
		this.completed = completed;
	}


	public Categoria getCategoria() {
		return categoria;
	}


	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}
    
	/**
	 * Imposta i timestamp prima del persist.
	 */
    @PrePersist
	protected void onPersist() {
		this.creationTimeStamp = LocalDateTime.now();
		this.lastUpdateTimeStamp = LocalDateTime.now();
	}
	
    /**
     * Aggiorna il timestamp prima dell'update.
     */
    @PreUpdate
    protected void onUpdate() {
    	this.lastUpdateTimeStamp = LocalDateTime.now();
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
		Todo that = (Todo) obj;
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
