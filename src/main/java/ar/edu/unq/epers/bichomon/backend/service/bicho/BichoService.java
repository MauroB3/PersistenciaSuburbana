package ar.edu.unq.epers.bichomon.backend.service.bicho;

import ar.edu.unq.epers.bichomon.backend.dao.BichoDAO;
import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.service.runner.Runner;

public class BichoService {

    private BichoDAO bichoDAO;

    public BichoService(BichoDAO bichoDAO) {
        this.bichoDAO = bichoDAO;
    }

    public void crearBicho(Bicho bicho){
        Runner.runInSession( () -> {
            this.bichoDAO.guardar(bicho);
            return null;
        });
    }

}
