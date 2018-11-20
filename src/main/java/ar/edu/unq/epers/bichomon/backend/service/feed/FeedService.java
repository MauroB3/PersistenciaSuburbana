package ar.edu.unq.epers.bichomon.backend.service.feed;

import ar.edu.unq.epers.bichomon.backend.dao.mongodb.EventoDAO;
import ar.edu.unq.epers.bichomon.backend.model.evento.*;

public class FeedService {

    private EventoDAO eventoDAO;

    public FeedService(EventoDAO eventoDAO) {
        this.eventoDAO = eventoDAO;
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

}
