package it.todorestapp.siw.repository;

import javax.persistence.TypedQuery;

import it.todorestapp.siw.model.Credenziali;
import it.todorestapp.siw.model.Utente;

public class CredenzialiRepository extends SimpleRepositoryImpl<Credenziali> {

	public CredenzialiRepository() {
		super(Credenziali.class);
	}

	public Utente findByUsername(String username) {
		TypedQuery<Credenziali> query = null;
		query = this.getEntityManager().createQuery("SELECT c FROM Credenziali c WHERE username = :username", Credenziali.class);
        query.setParameter("username", username);
        Credenziali c = query.getSingleResult();
        return c.getUtente();
	}
	
	public Utente findByEmail(String email) {
		TypedQuery<Credenziali> query = null;
		query = this.getEntityManager().createQuery("SELECT c FROM Credenziali c WHERE email = :email", Credenziali.class);
        query.setParameter("email", email);
        Credenziali c = query.getSingleResult();
        return c.getUtente();
	}
	
}
