package ar.edu.unq.epers.bichomon.backend.dao;

import ar.edu.unq.epers.bichomon.backend.model.campeon.Campeon;

public interface CampeonDAO {

    Campeon getCampeon(String nombreDojo);

    Campeon getCampeonHistorico(String nombreUbicacion);

    void actualizarOGuardarCampeon(Campeon campeon);

}
