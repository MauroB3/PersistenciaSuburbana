package ar.edu.unq.epers.bichomon.backend.service.leaderboard;

import ar.edu.unq.epers.bichomon.backend.dao.CampeonDAO;
import ar.edu.unq.epers.bichomon.backend.dao.EntrenadorDAO;
import ar.edu.unq.epers.bichomon.backend.model.campeon.Campeon;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.service.runner.Runner;

import java.util.*;

public class LeaderboardService {

    private CampeonDAO campeonDAO;
    private EntrenadorDAO entrenadorDAO;

    public LeaderboardService(CampeonDAO campeonDAO, EntrenadorDAO entrenadorDAO) {
        this.campeonDAO = campeonDAO;
        this.entrenadorDAO = entrenadorDAO;
    }

    public List<Campeon> campeones() {
        return Runner.runInSession( () -> {
            return this.campeonDAO.recuperarTodos();
        });
    }

    public List<Entrenador> recuperarCampeonesActuales() {
        return Runner.runInSession( () -> {
            List<Campeon> campeonesAnteriores = this.campeonDAO.recuperarCampeonesAnteriores();

            List<Entrenador> entrenadoresConCampeonActual = new ArrayList<>();
            //Elimino los repetidos manteniendo el orden
            for(Campeon campeon : campeonesAnteriores) {
                if(!entrenadoresConCampeonActual.contains(campeon.getBicho().getEntrenador())) {
                    entrenadoresConCampeonActual.add(campeon.getBicho().getEntrenador());
                }
            }

            return entrenadoresConCampeonActual;
        });
    }

    public List<Entrenador> lideres() {
        return Runner.runInSession( () -> {
            List<Entrenador> entrenadores = this.entrenadorDAO.recuperarTodos();

            Collections.sort(entrenadores, new Comparator<Entrenador>() {
                @Override
                public int compare(Entrenador o1, Entrenador o2) {
                    return new Integer(o2.getPoderTotal()).compareTo(new Integer(o1.getPoderTotal()));
                }
            });

           return entrenadores;
        });
    }

}
