package it.todorestapp.siw.util;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

/**
 * Utility per costruire risposte HTTP JSON in modo uniforme.
 * @author Jacopo Orrù
 */
public class ResponseManager {
  
	private static final ObjectMapper mapper = new ObjectMapper();
	static {
		// Enable Java 8 date/time types (e.g., LocalDateTime)
		mapper.registerModule(new JavaTimeModule());
	}
	
	/**
	 * Invia una risposta di successo serializzando l'oggetto in JSON.
	 *
	 * @param resp response HTTP
	 * @param data payload da serializzare in JSON
	 * @throws IOException se fallisce la scrittura della risposta
	 */
    public static void sendJson(HttpServletResponse resp, Object data) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        // Scrive l'oggetto trasformato in stringa JSON direttamente nel corpo della risposta
        resp.getWriter().write(mapper.writeValueAsString(data));
    }

    /**
     * Invia una risposta di errore standard con messaggio JSON.
     *
     * @param resp response HTTP
     * @param statusCode codice di stato HTTP
     * @param message messaggio di errore
     * @throws IOException se fallisce la scrittura della risposta
     */
    public static void sendError(HttpServletResponse resp, int statusCode, String message) throws IOException {
        resp.setStatus(statusCode);
        resp.setContentType("application/json");
        resp.getWriter().write("{\"status\": \"error\", \"message\": \"" + message + "\"}");
    }
    
    /**
     * Conferma operazioni andate a buon fine senza dati (es. delete).
     *
     * @param resp response HTTP
     * @param statusCode codice di stato HTTP
     */
    public static void sendEmpty(HttpServletResponse resp, int statusCode) {
        resp.setStatus(statusCode);
    }
}
