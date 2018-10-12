package ar.edu.unq.epers.bichomon.backend.service.leaderboard;

import ar.edu.unq.epers.bichomon.backend.dao.CampeonDAO;
import ar.edu.unq.epers.bichomon.backend.dao.impl.HibernateBichoDAO;
import ar.edu.unq.epers.bichomon.backend.dao.impl.HibernateCampeonDAO;
import ar.edu.unq.epers.bichomon.backend.dao.impl.HibernateUbicacionDAO;
import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Dojo;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Guarderia;
import ar.edu.unq.epers.bichomon.backend.service.bicho.BichoService;
import ar.edu.unq.epers.bichomon.backend.service.campeon.CampeonService;
import ar.edu.unq.epers.bichomon.backend.service.ubicacion.UbicacionServiceImp;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.*;

public class LeaderboardServiceTest {

    private LeaderboardService leaderboardService;
    private HibernateCampeonDAO campeonDAO;
    private CampeonService campeonService;
    private BichoService bichoService;
    private UbicacionServiceImp ubicacionService;

    private Dojo dojo;

    private Especie especie1 = new Especie("Pikachu");
    private Especie especie2 = new Especie("Charmander");

    private Bicho bicho1 = new Bicho(especie1);
    private Bicho bicho2 = new Bicho(especie2);
    private Bicho bicho3 = new Bicho(especie2);

    private LocalDate fechaInicio1 = LocalDate.of(2018,10,05);
    private LocalDate fechaInicio2 = LocalDate.of(2018,10,10);
    private LocalDate fechaInicio3 = LocalDate.of(2018,10,12);

    @Before
    public void setUp() {
        campeonDAO = new HibernateCampeonDAO();
        campeonService = new CampeonService(campeonDAO);
        leaderboardService = new LeaderboardService(campeonDAO);
        bichoService = new BichoService(new HibernateBichoDAO());
        ubicacionService = new UbicacionServiceImp(new HibernateUbicacionDAO());

        dojo = new Dojo();
        dojo.setNombre("un dojo");
    }

    @Test
    public void campeones() {
        bichoService.crearBicho(bicho1);
        bichoService.crearBicho(bicho2);
        bichoService.crearBicho(bicho3);
        ubicacionService.crearUbicacion(dojo);
        campeonService.actualizarCampeon(dojo.actualizarYRetornarCampeon(bicho1, fechaInicio1));
        campeonService.actualizarCampeon(dojo.actualizarYRetornarCampeon(bicho2, fechaInicio2));
        campeonService.actualizarCampeon(dojo.actualizarYRetornarCampeon(bicho3, fechaInicio3));
        assertEquals(2, leaderboardService.campeones().size());

    }
}