package ar.edu.unq.epers.bichomon.backend.dao;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;

public interface BichoDAO {

    void guardar(Bicho bicho);

    void actualizar(Bicho bicho);

    Bicho recuperar(int idBicho);

}
