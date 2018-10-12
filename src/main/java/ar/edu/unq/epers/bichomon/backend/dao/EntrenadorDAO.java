package ar.edu.unq.epers.bichomon.backend.dao;

import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;

public interface EntrenadorDAO {

    public void guardar(Entrenador entrenador);

    public Entrenador recuperar(String nombre);

    public void actualizar(Entrenador entrenador);

}
