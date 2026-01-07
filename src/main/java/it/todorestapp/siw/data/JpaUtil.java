package it.todorestapp.siw.data;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Utility per inizializzare e gestire l'EntityManagerFactory.
 * @pattern: singleton
 * @author Jacopo Orrù
 */
public class JpaUtil {
	
	private static EntityManagerFactory emf;

	
	/**
	 * Costruttore privato per evitare istanze.
	 */
	private JpaUtil() {}
	
	/**
	 * Restituisce la factory JPA (creandola se necessario).
	 *
	 * @return entity manager factory
	 */
	public static EntityManagerFactory getEntityManagerFactory() {
		if(emf == null)
		  emf = Persistence.createEntityManagerFactory("todo-unit");
		return emf;
	}
	
	/**
	 * Chiude la factory allo spegnimento dell'applicazione.
	 */
    public static void close() {
        if (emf != null) {
            emf.close();
        }
    }

} 
    
