package it.todorestapp.siw.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import it.todorestapp.siw.model.Categoria;
import it.todorestapp.siw.service.CategoriaService;
import it.todorestapp.siw.util.ResponseManager;

/**
 * Servlet per la gestione delle richieste REST sulle categorie.
 * @author Jacopo Orrù
 */
@WebServlet("/api/categorie/*")
public class CategoriaController extends HttpServlet {

	private CategoriaService service;
	private ObjectMapper mapper;

	/**
	 * Inizializza servizio e mapper JSON.
	 */
	public CategoriaController() {
		this.mapper = new ObjectMapper();
		this.service = new CategoriaService();
	}

	/**
	 * Gestisce il recupero delle categorie (tutte o per id).
	 *
	 * @param req request HTTP
	 * @param resp response HTTP
	 * @throws ServletException se fallisce la gestione della richiesta
	 * @throws IOException se fallisce la scrittura della risposta
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// pathInfo contains what comes after the servlet path.
		String pathInfo = req.getPathInfo();
		try {
			if (pathInfo == null || pathInfo.equals("/")) {
				List<Categoria> categorie = this.service.findAll();
				if(categorie == null || categorie.isEmpty()) {
					ResponseManager.sendError(resp, 404, "Errore durante il caricamento dei dati dal db");
					return;
				}
				ResponseManager.sendJson(resp, categorie);
			} else {
				Long id = Long.parseLong(pathInfo.substring(1));
				Categoria c = this.service.findById(id);
				if (c != null) {
					ResponseManager.sendJson(resp, c);
				} else {
					ResponseManager.sendError(resp, 404, "Categoria non trovata");
				}
			}
		} catch (NumberFormatException e) {
			ResponseManager.sendError(resp, 400, "ID non valido");
		}
	}

	/**
	 * Crea una nuova categoria a partire dal JSON in input.
	 *
	 * @param req request HTTP
	 * @param resp response HTTP
	 * @throws ServletException se fallisce la gestione della richiesta
	 * @throws IOException se fallisce la lettura del body o la scrittura della risposta
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Categoria nuova = this.mapper.readValue(req.getInputStream(), Categoria.class);
		try {
			if(nuova == null) {
				ResponseManager.sendError(resp, 404, "Dati mancanti");
			}else {
				Categoria inserita = this.service.create(nuova.getNomeCategoria());

				if(this.service.existById(inserita.getId()) == true){
					ResponseManager.sendJson(resp, inserita);
				}else{
					ResponseManager.sendError(resp, 500, "Errore interno durante l'inserimento");
				}
			}
		}catch(Exception e) {
			ResponseManager.sendError(resp, 500, "Errore nel processing della richiesta: " + e.getMessage());
		}
	}

	/**
	 * Aggiorna una categoria esistente a partire dal JSON in input.
	 *
	 * @param req request HTTP
	 * @param resp response HTTP
	 * @throws ServletException se fallisce la gestione della richiesta
	 * @throws IOException se fallisce la lettura del body o la scrittura della risposta
	 */
	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Categoria aggiornata = this.mapper.readValue(req.getInputStream(), Categoria.class);
		try {
			if(aggiornata == null) {
				ResponseManager.sendError(resp, 401, "Errore non è arrivato nessun oggetto");
			}else {
				Categoria c = this.service.update(aggiornata);
				if(c != null)
					ResponseManager.sendJson(resp, c);
				else
					ResponseManager.sendError(resp, 401, "Oggetto non trovato!");
			}
		}catch(Exception e) {
			ResponseManager.sendError(resp, 500, "Errore nel processing della richiesta: " + e.getMessage());
		}
	}

	/**
	 * Cancella una categoria identificata dall'id nella path.
	 *
	 * @param req request HTTP
	 * @param resp response HTTP
	 * @throws ServletException se fallisce la gestione della richiesta
	 * @throws IOException se fallisce la scrittura della risposta
	 */
	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String pathInfo = req.getPathInfo();
		try {
			if(pathInfo == null || pathInfo.equals("/"))
				ResponseManager.sendError(resp, 401, "url sprovvista di id");
			else {
				Long id = Long.parseLong(pathInfo.substring(1));
				boolean cancellato = this.service.deleteById(id);
				if(cancellato == true) {
					ResponseManager.sendJson(resp, cancellato);
				}else {
					ResponseManager.sendError(resp, 500, "l'oggetto con " + id + " non è stato cancellato. Errore durante l'operazione di cancellazione");
				}
			}
		}catch(Exception e) {
			ResponseManager.sendError(resp, 500, "Errore nel processing della richiesta: " + e.getMessage());
		}
	}



}
