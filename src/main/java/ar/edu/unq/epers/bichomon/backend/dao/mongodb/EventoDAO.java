package ar.edu.unq.epers.bichomon.backend.dao.mongodb;

import ar.edu.unq.epers.bichomon.backend.model.evento.Arribo;
import ar.edu.unq.epers.bichomon.backend.model.evento.Evento;
import org.jongo.Aggregate.ResultsIterator;

import java.util.List;

public class EventoDAO extends GenericMongoDAO<Evento> {

	public EventoDAO() {
		super(Evento.class);
	}

	public List<Evento> getEventosDeEntrenador(String entrenadorNombre) {
		return this.find("{ entrenador: # }", entrenadorNombre);
	}

}
