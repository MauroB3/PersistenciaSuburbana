package ar.edu.unq.epers.bichomon.backend.service.leaderboard;

import ar.edu.unq.epers.bichomon.backend.dao.impl.*;
import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import ar.edu.unq.epers.bichomon.backend.model.nivel.NivelManager;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Dojo;
import ar.edu.unq.epers.bichomon.backend.service.bicho.BichoService;
import ar.edu.unq.epers.bichomon.backend.service.bicho.BichoServiceImpl;
import ar.edu.unq.epers.bichomon.backend.service.campeon.CampeonService;
import ar.edu.unq.epers.bichomon.backend.service.entrenador.EntrenadorService;
import ar.edu.unq.epers.bichomon.backend.service.nivel.NivelServiceImpl;
import ar.edu.unq.epers.bichomon.backend.service.ubicacion.UbicacionServiceImp;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.time.LocalDate;
import static org.junit.Assert.*;


public class EspecieLiderTest {

    private LeaderboardService leaderboardService;
    private HibernateCampeonDAO campeonDAO;
    private CampeonService campeonService;
    private BichoService bichoService;
    private UbicacionServiceImp ubicacionService;
    private EntrenadorService entrenadorService;
    private NivelServiceImpl nivelService;

    private Dojo dojo1;
    private Dojo dojo2;
    private Dojo dojo3;

    private Especie especie1;
    private Especie especie2;
    private Especie especie3;

    @Mock
    private NivelManager nivelManager;

    private Entrenador entrenador1;
    private Entrenador entrenador2;
    private Entrenador entrenador3;

    private Bicho bicho1;
    private Bicho bicho2;
    private Bicho bicho3;
    private Bicho bicho4;
    private Bicho bicho5;
    private Bicho bicho6;
    private Bicho bicho7;

    private LocalDate fechaInicio1 = LocalDate.of(2018,10,05);
    private LocalDate fechaInicio2 = LocalDate.of(2018,10,10);
    private LocalDate fechaInicio3 = LocalDate.of(2018,10,12);
    private LocalDate fechaInicio4 = LocalDate.of(2018,10,15);
    private LocalDate fechaInicio5 = LocalDate.of(2018,10,28);
    private LocalDate fechaInicio6 = LocalDate.of(2018,10,30);

    @Before
    public void setUp() {
        campeonDAO = new HibernateCampeonDAO();
        campeonService = new CampeonService(campeonDAO);
        leaderboardService = new LeaderboardService(campeonDAO, new HibernateEntrenadorDAO(), new HibernateEspecieDAO());
        bichoService = new BichoServiceImpl(new HibernateBichoDAO(), new HibernateEntrenadorDAO(), new HibernateEspecieDAO());
        ubicacionService = new UbicacionServiceImp(new HibernateUbicacionDAO());
        nivelService = new NivelServiceImpl(new HibernateNivelDAO());
        entrenadorService = new EntrenadorService(new HibernateEntrenadorDAO(), nivelService);


        dojo1 = new Dojo();
        dojo1.setNombre("Dojo 1");
        dojo2 = new Dojo();
        dojo2.setNombre("Dojo 2");
        dojo3 = new Dojo();
        dojo3.setNombre("Dojo 3");

        especie1 = new Especie("Pikachu");
        especie2 = new Especie("Charmander");
        especie3 = new Especie("Squirtle");

        entrenador1 = new Entrenador("entrenador1", nivelManager, dojo1);
        entrenador2 = new Entrenador("entrenador2", nivelManager, dojo2);
        entrenador3 = new Entrenador("entrenador3", nivelManager, dojo3);

        bicho1 = new Bicho(especie1, entrenador1);
        bicho2 = new Bicho(especie2, entrenador1);
        bicho3 = new Bicho(especie1, entrenador2);
        bicho4 = new Bicho(especie2, entrenador2);
        bicho5 = new Bicho(especie3, entrenador3);
        bicho6 = new Bicho(especie1, entrenador3);

        entrenador1.agregarBicho(bicho1);
        entrenador1.agregarBicho(bicho2);
        entrenador2.agregarBicho(bicho3);
        entrenador2.agregarBicho(bicho4);
        entrenador3.agregarBicho(bicho5);
        entrenador3.agregarBicho(bicho6);

        ubicacionService.crearUbicacion(dojo1);
        ubicacionService.crearUbicacion(dojo2);
        entrenadorService.guardar(entrenador1);
        entrenadorService.guardar(entrenador2);
        entrenadorService.guardar(entrenador3);
        campeonService.actualizarCampeon(dojo1.actualizarYRetornarCampeon(bicho1, fechaInicio1));
        ubicacionService.actualizarUbicacion(dojo1);
        campeonService.actualizarCampeon(dojo1.actualizarYRetornarCampeon(bicho2, fechaInicio2));
        ubicacionService.actualizarUbicacion(dojo1);
        campeonService.actualizarCampeon(dojo2.actualizarYRetornarCampeon(bicho3, fechaInicio3));
        ubicacionService.actualizarUbicacion(dojo2);
        campeonService.actualizarCampeon(dojo2.actualizarYRetornarCampeon(bicho4, fechaInicio4));
        ubicacionService.actualizarUbicacion(dojo2);
        campeonService.actualizarCampeon(dojo3.actualizarYRetornarCampeon(bicho5, fechaInicio5));
        ubicacionService.actualizarUbicacion(dojo3);
        campeonService.actualizarCampeon(dojo3.actualizarYRetornarCampeon(bicho6, fechaInicio6));
        ubicacionService.actualizarUbicacion(dojo3);

    }

    @Test
    public void testEspecieLider() {
        assertEquals("Pikachu", leaderboardService.especieLider().getNombre());
    }

}
