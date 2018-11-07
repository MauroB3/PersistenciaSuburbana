package ar.edu.unq.epers.bichomon.backend.service.mapa;

import ar.edu.unq.epers.bichomon.backend.dao.CampeonDAO;
import ar.edu.unq.epers.bichomon.backend.dao.EntrenadorDAO;
import ar.edu.unq.epers.bichomon.backend.dao.UbicacionDAO;
import ar.edu.unq.epers.bichomon.backend.dao.impl.UbicacionNeo4JDAO;
import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.campeon.Campeon;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Ubicacion;
import ar.edu.unq.epers.bichomon.backend.service.runner.Runner;
import ar.edu.unq.epers.bichomon.backend.service.ubicacion.UbicacionIncorrectaException;

import java.util.List;

public class MapaService {


    private UbicacionDAO ubicacionDAO;
    private CampeonDAO campeonDAO;
    private EntrenadorDAO entrenadorDAO;
    private UbicacionNeo4JDAO ubicacionNeo4JDAO;

    public MapaService(UbicacionDAO ubicacionDAO, CampeonDAO campeonDAO, EntrenadorDAO entrenadorDAO, UbicacionNeo4JDAO ubicacionNeo4JDAO) {
        this.ubicacionDAO = ubicacionDAO;
        this.campeonDAO = campeonDAO;
        this.entrenadorDAO = entrenadorDAO;
        this.ubicacionNeo4JDAO = ubicacionNeo4JDAO;
    }

    public void mover(String entrenador, String destino) {
        Runner.runInSession( () -> {
            Entrenador entrenadorR = entrenadorDAO.recuperar(entrenador);
            Ubicacion ubicacionNueva = ubicacionDAO.recuperar(destino);

            int costo = this.ubicacionNeo4JDAO.getCostoEntreUbicacionesLindantes(entrenadorR.ubicacion().getNombre(), destino);

            entrenadorR.mover(ubicacionNueva, costo);
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

    public void crearUbicacion(Ubicacion ubicacion) {
        Runner.runInSession( () -> {
            this.ubicacionDAO.guardar(ubicacion);
            this.ubicacionNeo4JDAO.crearUbicacion(ubicacion);

            return null;
        });
    }

    public void conectar(String ubicacion1, String ubicacion2, String tipoCamino) {
        Runner.runInSession( () -> {
            this.ubicacionNeo4JDAO.conectar(ubicacion1, ubicacion2, tipoCamino);

            return null;
        });
    }

    public List<Ubicacion> conectados(String nombreUbicacion, String tipoCamino) {
        return Runner.runInSession(() -> {

            List<String> nombresDeUbicaciones = this.ubicacionNeo4JDAO.conectados(nombreUbicacion, tipoCamino);

            return this.ubicacionDAO.recuperarTodos(nombresDeUbicaciones);
        });
    }

}
