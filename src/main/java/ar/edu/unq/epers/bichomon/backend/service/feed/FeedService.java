package ar.edu.unq.epers.bichomon.backend.service.feed;

import ar.edu.unq.epers.bichomon.backend.dao.mongodb.EventoDAO;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.evento.*;

import java.util.List;

public class FeedService {

    private EventoDAO eventoDAO;

    public FeedService(EventoDAO eventoDAO) {
        this.eventoDAO = eventoDAO;
    }

    public List<Evento> getEventosDeEntrenador(Entrenador entrenador) {
        return this.eventoDAO.getEventosDeEntrenador(entrenador.nombre());
    }

    public void guardarArribo(String entrenador, String ubicacionDestino, String ubicacionOrigen) {
        Evento eventoAGuardar = new Arribo(entrenador, ubicacionDestino, ubicacionOrigen);
        this.eventoDAO.save(eventoAGuardar);
    }

    public void guardarCaptura(String nombreEntrenador, String nombreEspecieBicho, String nombreUbicacionOrigen){
        Evento evento = new Captura(nombreEntrenador, nombreEspecieBicho, nombreUbicacionOrigen);
        this.eventoDAO.save(evento);
    }

    public void guardarCoronacion(String nombreEntrenadorCoronado, String nombreEntrenadorDescoronado, String nombreUbicacionOrigen){
        Evento evento = new Coronacion(nombreEntrenadorCoronado, nombreEntrenadorDescoronado, nombreUbicacionOrigen);
        this.eventoDAO.save(evento);
    }

    public void guardarAbandono(String nombreEntrenador, String nombreEspecieBichoAbandonado, String nombreUbicacionOrigen){
        Evento evento = new Abandono(nombreEntrenador, nombreEspecieBichoAbandonado, nombreUbicacionOrigen);
        this.eventoDAO.save(evento);
    }

}
