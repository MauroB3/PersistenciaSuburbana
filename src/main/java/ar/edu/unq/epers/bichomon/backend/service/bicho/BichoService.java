package ar.edu.unq.epers.bichomon.backend.service.bicho;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.duelo.ResultadoCombate;

public interface BichoService {

    void crearBicho(Bicho bicho);

    Bicho buscar(String entrenador);

    void abandonar(String entrenador);

    ResultadoCombate duelo(String entrenador);

    boolean puedeEvolucionar(String entrenador, int bicho);

    Bicho evolucionar(String entrenador, int bicho);
    
}
