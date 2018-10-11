package ar.edu.unq.epers.bichomon.backend.dao;

import ar.edu.unq.epers.bichomon.backend.model.campeon.Campeon;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Ubicacion;

import java.util.List;

public interface UbicacionDAO {

    void guardar(Ubicacion ubicacion);

    void actualizar(Ubicacion ubicacion);

    void eliminar(Ubicacion ubicacion);

    Ubicacion recuperar(String nombre);

    List<Ubicacion> recuperarTodos();

    int getCantidadEntrenadores(String nombreUbicacion);

}
