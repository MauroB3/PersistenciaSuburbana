package ar.edu.unq.epers.bichomon.backend.service.bicho;

import ar.edu.unq.epers.bichomon.backend.dao.BichoDAO;
import ar.edu.unq.epers.bichomon.backend.dao.impl.HibernateBichoDAO;
import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.duelo.ResultadoCombate;
import ar.edu.unq.epers.bichomon.backend.service.runner.Runner;

public class BichoServiceImpl implements BichoService{

    private HibernateBichoDAO dao;

    public BichoServiceImpl(HibernateBichoDAO dao) {
        this.dao = dao;
    }

    @Override
    public void crearBicho(Bicho bicho){
        Runner.runInSession( () -> {
            this.dao.guardar(bicho);
            return null;
        });
    }

    @Override
    public Bicho buscar(String entrenador) {
        return null;
    }

    @Override
    public void abandonar(String entrenador) {

    }

    @Override
    public ResultadoCombate duelo(String entrenador) {
        return null;
    }

    public boolean puedeEvolucionar(String entrenador, int idBicho) {
        return Runner.runInSession(() -> {
            return dao.recuperar(idBicho).puedeEvolucionar();
        });
    }

    @Override
    public Bicho evolucionar(String entrenador, int bicho) {
        return null;
    }
}
