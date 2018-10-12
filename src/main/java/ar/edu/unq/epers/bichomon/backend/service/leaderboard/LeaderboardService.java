package ar.edu.unq.epers.bichomon.backend.service.leaderboard;

import ar.edu.unq.epers.bichomon.backend.dao.CampeonDAO;
import ar.edu.unq.epers.bichomon.backend.model.campeon.Campeon;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.service.runner.Runner;

import java.util.*;

public class LeaderboardService {

    private CampeonDAO campeonDAO;

    public LeaderboardService(CampeonDAO campeonDAO) {
        this.campeonDAO = campeonDAO;
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

}
