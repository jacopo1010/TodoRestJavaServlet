package it.todorestapp.siw.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import it.todorestapp.siw.model.Todo;
import it.todorestapp.siw.service.TodoService;
import it.todorestapp.siw.util.ResponseManager;

@WebServlet("/api/todo/*")
public class TodoController extends HttpServlet {
	//qua dovrò aggiungere il service per l'utente
	private TodoService service;
	private ObjectMapper mapper;

	public TodoController() {
		this.service = new TodoService();
		this.mapper = new ObjectMapper();
		this.mapper.registerModule(new JavaTimeModule());
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = req.getPathInfo();
		try {
			/*caso 1 dove il path è "/api/todo" o "/api/todo/" che prende tutti i todo*/
			if(path == null || path.equals("/")) {
				//fare refactoring quando implementerò l'utente poi con l'utente
				List<Todo> todoes = this.service.findAll();
				if(todoes == null)
					ResponseManager.sendError(resp, 500, "Erroe con il caricamento dei to-do");
				else
					ResponseManager.sendJson(resp, todoes);
			}else {
				Long id = Long.parseLong(path.substring(1));
				Todo cercato = this.service.findById(id);
				if(cercato == null)
					ResponseManager.sendError(resp, 401, "l'elemento cercato con id: " + id +" non è stato tovato" );
				else
					ResponseManager.sendJson(resp, cercato);
			} 
		}catch(Exception e) {
			ResponseManager.sendError(resp, 500, "Errore nel processing della richiesta: " + e.getMessage());
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Todo t = this.mapper.readValue(req.getInputStream(), Todo.class);
		try {
			if(t == null)
				ResponseManager.sendError(resp, 404, "Dati mancanti");
			else {
				Todo nuovo = this.service.create(t.getName(),t.getDescription() ,t.getCreationTimeStamp(),t.getLastUpdateTimeStamp(),t.isCompleted(),t.getCategoria());
				if(this.service.existById(nuovo.getId()) == true)
					ResponseManager.sendJson(resp, nuovo);
				else
					ResponseManager.sendError(resp, 500, "Errore interno durante l'inserimento");
			}
		}catch(Exception e) {
			ResponseManager.sendError(resp, 500, "Errore nel processing della richiesta: " + e.getMessage());
		}
	}

	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Todo t = this.mapper.readValue(req.getInputStream(), Todo.class);
		try {
			if(t == null) {
				ResponseManager.sendError(resp, 401, "Errore non è arrivato nessun oggetto");
			}else {
				Todo aggiornato = this.service.update(t);
				if(aggiornato == null)
					ResponseManager.sendError(resp, 401, "Oggetto non trovato!");
				else 
					ResponseManager.sendJson(resp, aggiornato);
			}
		}catch(Exception e) {
			ResponseManager.sendError(resp, 500, "Errore nel processing della richiesta: " + e.getMessage());
		}
	}

	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = req.getPathInfo();
		try {
			if(path == null || path.equals("/")) {
				ResponseManager.sendError(resp, 401, "passare codice id nella url");
			}else {
                Long id = Long.parseLong(path.substring(1));
                if(this.service.deleteById(id) == true)
                	ResponseManager.sendJson(resp, true);
                else
                	ResponseManager.sendError(resp, 401, "Oggetto non trovato!");
			}
		}catch(Exception e) {
			ResponseManager.sendError(resp, 500, "Errore nel processing della richiesta: " + e.getMessage());
		}
	}


}
