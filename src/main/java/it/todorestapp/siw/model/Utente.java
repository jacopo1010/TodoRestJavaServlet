package it.todorestapp.siw.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

@Entity
@Table(name ="Utenti")
public class Utente {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false, length = 30)
	private String nome;
	@Column(nullable = false, length = 30)
	private String cognome;
	@Column(nullable = false)
	private LocalDateTime creationTimeStamp;
	@Column(nullable = false)
	private LocalDateTime lastUpdateTimeStamp;
	private final String ruolo = "default";
	@OneToMany(mappedBy = "utente", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
	private List<Todo> todo;

	public Utente(String nome, String cognome,LocalDateTime creationTimeStamp,LocalDateTime lastUpdateTimeStamp) {
        this.nome = nome;
        this.cognome = cognome;
        this.creationTimeStamp = creationTimeStamp;
        this.lastUpdateTimeStamp = lastUpdateTimeStamp;
        this.todo = new ArrayList<Todo>();
	}

	public Utente() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
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

	public String getRuolo() {
		return ruolo;
	}

	public List<Todo> getTodo() {
		return todo;
	}

	public void setTodo(List<Todo> todo) {
		this.todo = todo;
	}

	@PrePersist
	protected void onPersist() {
		this.creationTimeStamp = LocalDateTime.now();
		this.lastUpdateTimeStamp = LocalDateTime.now();
	}

	@PreUpdate
	protected void onUpdate() {
		this.lastUpdateTimeStamp = LocalDateTime.now();
	}
	
	@Override 
	public boolean equals(Object o) {
		if(o == null || o.getClass() != this.getClass())
			return false;
		Utente that = (Utente) o;
		return this.id == that .getId();
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
