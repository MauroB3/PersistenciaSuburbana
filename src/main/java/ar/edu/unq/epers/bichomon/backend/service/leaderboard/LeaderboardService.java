package ar.edu.unq.epers.bichomon.backend.service.leaderboard;

import ar.edu.unq.epers.bichomon.backend.dao.CampeonDAO;
import ar.edu.unq.epers.bichomon.backend.model.campeon.Campeon;
import ar.edu.unq.epers.bichomon.backend.service.runner.Runner;

import java.util.List;

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

}
