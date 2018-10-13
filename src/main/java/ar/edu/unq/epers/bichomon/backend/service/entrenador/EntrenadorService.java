package ar.edu.unq.epers.bichomon.backend.service.entrenador;

import ar.edu.unq.epers.bichomon.backend.dao.impl.HibernateEntrenadorDAO;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.nivel.NivelManager;

import ar.edu.unq.epers.bichomon.backend.service.nivel.NivelServiceImpl;
import ar.edu.unq.epers.bichomon.backend.service.runner.Runner;

public class EntrenadorService {

    private NivelManager nivelManager;

    private HibernateEntrenadorDAO entrenadorDAO;

    public EntrenadorService(HibernateEntrenadorDAO entrenadorDAO, NivelServiceImpl nivelService) {
        this.entrenadorDAO = entrenadorDAO;
        this.nivelManager = nivelService.getNivelManager();
    }

    public void guardar(Entrenador entrenador) {
        Runner.runInSession( () -> {
            entrenadorDAO.guardar(entrenador);
            return null;
        });
    }

    public Entrenador recuperar(String entrenador) {
        return Runner.runInSession(() -> {
            Entrenador entr = entrenadorDAO.recuperar(entrenador);
            entr.setNivelManager(this.nivelManager);
            return entr;
        });
    }

    public void actualizar(Entrenador entrenador) {
        Runner.runInSession(() -> {
           entrenadorDAO.actualizar(entrenador);
           return null;
        });
    }
}
