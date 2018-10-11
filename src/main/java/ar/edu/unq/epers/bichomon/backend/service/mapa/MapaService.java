package ar.edu.unq.epers.bichomon.backend.service.mapa;

import ar.edu.unq.epers.bichomon.backend.dao.CampeonDAO;
import ar.edu.unq.epers.bichomon.backend.dao.UbicacionDAO;
import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.campeon.Campeon;
import ar.edu.unq.epers.bichomon.backend.service.runner.Runner;
import ar.edu.unq.epers.bichomon.backend.service.ubicacion.UbicacionIncorrectaException;

public class MapaService {


    private UbicacionDAO ubicacionDAO;
    private CampeonDAO campeonDAO;

    public MapaService(UbicacionDAO ubicacionDAO, CampeonDAO campeonDAO) {
        this.ubicacionDAO = ubicacionDAO;
        this.campeonDAO = campeonDAO;
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
                return campeonDAO.getCampeon(dojo);
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
