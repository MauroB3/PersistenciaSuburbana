package ar.edu.unq.epers.bichomon.backend.service.mapa;

import ar.edu.unq.epers.bichomon.backend.dao.UbicacionDAO;
import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Campeon;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Ubicacion;
import ar.edu.unq.epers.bichomon.backend.service.runner.Runner;
import ar.edu.unq.epers.bichomon.backend.service.ubicacion.UbicacionIncorrectaException;

public class MapaService {


    private UbicacionDAO ubicacionDAO;

    public MapaService(UbicacionDAO ubicacionDAO) {
        this.ubicacionDAO = ubicacionDAO;
    }

    public void mover(String entrenador, String ubicacion) {

    }

    public int cantidadEntrenadores(String ubicacion) {
        return Runner.runInSession( () -> {
            return ubicacionDAO.getCantidadEntrenadores(ubicacion);
        });
    }

    public Campeon campeon(String dojo) {
        return Runner.runInSession( () -> {
            if (ubicacionDAO.recuperar(dojo).esDojo()) {
                return ubicacionDAO.getCampeon(dojo);
            } else {
                throw new UbicacionIncorrectaException(dojo, "dojo");
            }
        });
    }

    public Bicho campeonHistorico(String dojo) {
        return Runner.runInSession( () -> {
            if (ubicacionDAO.recuperar(dojo).esDojo()) {
                return ubicacionDAO.getCampeonHistorico(dojo).getBicho();
            } else {
                throw new UbicacionIncorrectaException(dojo, "dojo");
            }
        });
    }

}
