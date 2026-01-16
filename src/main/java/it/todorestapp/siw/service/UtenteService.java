package it.todorestapp.siw.service;

import it.todorestapp.siw.repository.UtenteRepository;

import java.time.LocalDateTime;
import java.util.List;

import it.todorestapp.siw.model.Todo;
import it.todorestapp.siw.model.Utente;

public class UtenteService {

	private UtenteRepository utente;
	
	public UtenteService() {
		this.utente = new UtenteRepository();
	}
	
	public Utente create(String nome,String cognome,LocalDateTime creationTimeStamp, LocalDateTime lastUpdateTimeStamp,List<Todo> todo) {
		return null; 
	}
}
