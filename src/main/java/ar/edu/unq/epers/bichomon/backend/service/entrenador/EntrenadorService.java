package ar.edu.unq.epers.bichomon.backend.service.entrenador;

import ar.edu.unq.epers.bichomon.backend.dao.impl.HibernateEntrenadorDAO;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.service.runner.Runner;

public class EntrenadorService {

    private HibernateEntrenadorDAO entrenadorDAO;

    public EntrenadorService(HibernateEntrenadorDAO entrenadorDAO) {
        this.entrenadorDAO = entrenadorDAO;
    }

    public void guardar(Entrenador entrenador) {
        Runner.runInSession( () -> {
            entrenadorDAO.guardar(entrenador);
            return null;
        });
    }

    public Entrenador recuperar(String entrenador) {
        return Runner.runInSession(() -> {
            return entrenadorDAO.recuperar(entrenador);
        });
    }

    public void actualizar(Entrenador entrenador) {
        Runner.runInSession(() -> {
           entrenadorDAO.actualizar(entrenador);
           return null;
        });
    }
}
