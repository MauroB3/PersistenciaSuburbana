package ar.edu.unq.epers.bichomon.backend.service.feed;

import ar.edu.unq.epers.bichomon.backend.dao.EntrenadorDAO;
import ar.edu.unq.epers.bichomon.backend.dao.impl.UbicacionNeo4JDAO;
import ar.edu.unq.epers.bichomon.backend.dao.mongodb.EventoDAO;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.evento.*;
import ar.edu.unq.epers.bichomon.backend.service.entrenador.EntrenadorService;

import java.util.ArrayList;
import java.util.List;

public class FeedService {

    private EventoDAO eventoDAO;
    private EntrenadorService entrenadorService;
    private UbicacionNeo4JDAO ubicacionNeo4JDAO;

    public FeedService(EventoDAO eventoDAO, EntrenadorService entrenadorService, UbicacionNeo4JDAO ubicacionNeo4JDAO) {
        this.eventoDAO = eventoDAO;
        this.entrenadorService = entrenadorService;
        this.ubicacionNeo4JDAO = ubicacionNeo4JDAO;
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

    public List<Evento> feedEntrenador(String entrenador) {
        return this.eventoDAO.getEventosDeEntrenador(entrenador);
    }

    public List<Evento> feedUbicacion(String entrenador) {
        Entrenador entrenadorRec = this.entrenadorService.recuperar(entrenador);
        List<String> ubicaciones = new ArrayList<String>();
        ubicaciones.addAll(this.ubicacionNeo4JDAO.conectados(entrenadorRec.ubicacion().getNombre()));
        ubicaciones.add(entrenadorRec.ubicacion().getNombre());
        return this.eventoDAO.getEventosDeUbicaciones(ubicaciones);
    }

}
