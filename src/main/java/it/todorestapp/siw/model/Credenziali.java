package it.todorestapp.siw.model;

import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Credenziali")
public class Credenziali {
 
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false, unique = true, length = 50)
	private String username;
	@Column(nullable = false, length = 16)
	private String password;
	@Column(nullable = false, unique = true, length = 50)
	private String email;
	@OneToOne(cascade = CascadeType.REMOVE)
	@Column(nullable = false)
	private Utente utente;
	 
	public Credenziali(String username,String password,String email,Utente utente) {
		this.username = username;
		this.password = password;
		this.email = email;
		this.utente = utente;
	}
	
	public Credenziali() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Utente getUtente() {
		return utente;
	}

	public void setUtente(Utente utente) {
		this.utente = utente;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj == null || this.getClass() != obj.getClass())
			return false;
		Credenziali that = (Credenziali) obj;
		return this.id == that.getId() && this.email.equals(that.getEmail()) 
				&& this.username.equals(that.getUsername());
	}

	@Override
	public int hashCode() {
		return Objects.hash(id,email,username);
	}
	
	
	
}
