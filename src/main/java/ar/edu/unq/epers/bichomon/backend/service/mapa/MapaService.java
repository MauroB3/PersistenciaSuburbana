package ar.edu.unq.epers.bichomon.backend.service.mapa;

import ar.edu.unq.epers.bichomon.backend.dao.CampeonDAO;
import ar.edu.unq.epers.bichomon.backend.dao.EntrenadorDAO;
import ar.edu.unq.epers.bichomon.backend.dao.UbicacionDAO;
import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.campeon.Campeon;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Ubicacion;
import ar.edu.unq.epers.bichomon.backend.service.runner.Runner;
import ar.edu.unq.epers.bichomon.backend.service.ubicacion.UbicacionIncorrectaException;

public class MapaService {


    private UbicacionDAO ubicacionDAO;
    private CampeonDAO campeonDAO;
    private EntrenadorDAO entrenadorDAO;

    public MapaService(UbicacionDAO ubicacionDAO, CampeonDAO campeonDAO, EntrenadorDAO entrenadorDAO) {
        this.ubicacionDAO = ubicacionDAO;
        this.campeonDAO = campeonDAO;
        this.entrenadorDAO = entrenadorDAO;
    }

    public void mover(String entrenador, String ubicacion) {
        Runner.runInSession( () -> {
            Entrenador entrenadorR = entrenadorDAO.recuperar(entrenador);
            Ubicacion ubicacionNueva = ubicacionDAO.recuperar(ubicacion);
            Ubicacion ubicacionAnterior = entrenadorR.ubicacion();
            entrenadorR.mover(ubicacionNueva);
            ubicacionDAO.actualizar(ubicacionNueva);
            ubicacionDAO.actualizar(ubicacionAnterior);
            entrenadorDAO.actualizar(entrenadorR);
            return null;
        });
    }

    public int cantidadEntrenadores(String ubicacion) {
        return Runner.runInSession( () -> {
            return ubicacionDAO.getCantidadEntrenadores(ubicacion);
        });
    }

    public Campeon campeon(String dojo) {
        return Runner.runInSession( () -> {
            Ubicacion ubicacion = ubicacionDAO.recuperar(dojo);
            if (ubicacion.esDojo()) {
                return ubicacion.getCampeon();
            } else {
                throw new UbicacionIncorrectaException(dojo, "dojo");
            }
        });
    }

    public Bicho campeonHistorico(String dojo) {
        return Runner.runInSession( () -> {
            if (ubicacionDAO.recuperar(dojo).esDojo()) {
                return campeonDAO.getCampeonHistorico(dojo).getBicho();
            } else {
                throw new UbicacionIncorrectaException(dojo, "dojo");
            }
        });
    }

}
