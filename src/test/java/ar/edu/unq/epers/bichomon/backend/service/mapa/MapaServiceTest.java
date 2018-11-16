package ar.edu.unq.epers.bichomon.backend.service.mapa;

import ar.edu.unq.epers.bichomon.backend.dao.BichoDAO;
import ar.edu.unq.epers.bichomon.backend.dao.impl.*;
import ar.edu.unq.epers.bichomon.backend.dao.mongodb.EventoDAO;
import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.campeon.NoHayCampeonHistoricoException;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import ar.edu.unq.epers.bichomon.backend.model.nivel.NivelManager;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.*;
import ar.edu.unq.epers.bichomon.backend.service.bicho.BichoServiceImpl;
import ar.edu.unq.epers.bichomon.backend.service.campeon.CampeonService;
import ar.edu.unq.epers.bichomon.backend.service.entrenador.EntrenadorService;
import ar.edu.unq.epers.bichomon.backend.service.feed.FeedService;
import ar.edu.unq.epers.bichomon.backend.service.nivel.NivelServiceImpl;
import ar.edu.unq.epers.bichomon.backend.service.runner.SessionFactoryProvider;
import ar.edu.unq.epers.bichomon.backend.service.ubicacion.UbicacionServiceImp;

import org.junit.*;
import org.mockito.Mock;

import java.time.LocalDate;

import static org.junit.Assert.*;

public class MapaServiceTest {

    private MapaService mapaService;
    private HibernateUbicacionDAO ubicacionDAO;
    private UbicacionServiceImp ubicacionService;
    private BichoDAO bichoDAO;
    private BichoServiceImpl bichoService;
    private HibernateCampeonDAO campeonDAO;
    private CampeonService campeonService;
    private HibernateEntrenadorDAO entrenadorDAO;
    private EntrenadorService entrenadorService;
    private UbicacionNeo4JDAO ubicacionNeo4JDAO;
    private FeedService feedService;
    private EventoDAO eventoDAO;

    private Dojo dojo;
    private Dojo dojo2;
    private Dojo dojo3;
    private Guarderia guarderia;
    private Guarderia guarderia2;
    private Guarderia guarderia3;

    private Especie especie1 = new Especie("Pikachu");
    private Especie especie2 = new Especie("Charmander");
    private Especie especie3 = new Especie("Squirtle");

    @Mock
    private NivelManager nivelManager;

    private Entrenador entrenador;

    private Bicho bicho1 = new Bicho(especie1, entrenador);
    private Bicho bicho2 = new Bicho(especie2, entrenador);
    private Bicho bicho3 = new Bicho(especie3, entrenador);

    private LocalDate fechaInicio1 = LocalDate.of(2018,10,05);
    private LocalDate fechaInicio2 = LocalDate.of(2018,10,10);
    private LocalDate fechaInicio3 = LocalDate.of(2018,10,12);

    private NivelServiceImpl nivelService;

    @Before
    public void setUp() {
        nivelService = new NivelServiceImpl(new HibernateNivelDAO());
        ubicacionDAO = new HibernateUbicacionDAO();
        ubicacionService = new UbicacionServiceImp(ubicacionDAO);
        campeonDAO = new HibernateCampeonDAO();
        campeonService = new CampeonService(campeonDAO);
        entrenadorDAO = new HibernateEntrenadorDAO();
        ubicacionNeo4JDAO = new UbicacionNeo4JDAO();
        eventoDAO = new EventoDAO();
        feedService = new FeedService(eventoDAO);
        bichoService = new BichoServiceImpl(new HibernateBichoDAO(), entrenadorDAO, new HibernateEspecieDAO(), nivelService, ubicacionDAO, new HibernateExperienciaDAO());
        mapaService = new MapaService(ubicacionDAO, campeonDAO, entrenadorDAO, ubicacionNeo4JDAO, feedService);
        entrenadorService = new EntrenadorService(entrenadorDAO,nivelService);

        dojo = new Dojo();
        dojo.setNombre("un dojo");

        dojo2 = new Dojo();
        dojo2.setNombre("otro dojo");

        dojo3 = new Dojo();
        dojo3.setNombre("tercer dojo");

        guarderia = new Guarderia();
        guarderia.setNombre("Una guarderia");

        guarderia2 = new Guarderia();
        guarderia2.setNombre("Otra guarderia");

        guarderia3 = new Guarderia();
        guarderia3.setNombre("tercer guarderia");

        entrenador = new Entrenador("entrenador", dojo);
    }

    @After
    public void cleanUp(){
        //Destroy cierra la session factory y fuerza a que, la proxima vez, una nueva tenga
        //que ser creada.
        //
        //Al tener hibernate configurado con esto <property name="hibernate.hbm2ddl.auto">create-drop</property>
        //al crearse una nueva session factory todo el schema será destruido y creado desde cero.
        SessionFactoryProvider.destroy();
        ubicacionNeo4JDAO.destroy();
        //this.eventoDAO.deleteAll();
    }

    @Test
    public void mover() {
        mapaService.crearUbicacion(dojo);
        mapaService.crearUbicacion(guarderia);
        ubicacionNeo4JDAO.conectar(dojo.getNombre(), guarderia.getNombre(), "tierra");
        entrenador.setMonedas(5);
        entrenadorService.guardar(entrenador);

        assertEquals(1, dojo.getPoblacion());
        assertEquals(5, entrenador.getMonedas());
        mapaService.mover("entrenador", "Una guarderia");
        assertEquals(1, ubicacionService.getUbicacion("Una guarderia").getPoblacion());
        assertEquals(0, ubicacionService.getUbicacion("Un dojo").getPoblacion());
        assertEquals(4, this.entrenadorService.recuperar(entrenador.nombre()).getMonedas());
        assertEquals("Una guarderia", this.entrenadorService.recuperar(entrenador.nombre()).ubicacion().getNombre());
    }

    @Test
    public void moverMasCorto() {
        mapaService.crearUbicacion(dojo);
        mapaService.crearUbicacion(dojo2);
        mapaService.crearUbicacion(dojo3);
        mapaService.crearUbicacion(guarderia);
        mapaService.crearUbicacion(guarderia2);
        mapaService.crearUbicacion(guarderia3);

        ubicacionNeo4JDAO.conectar(dojo.getNombre(), guarderia2.getNombre(), "tierra");
        ubicacionNeo4JDAO.conectar(dojo.getNombre(), dojo3.getNombre(), "aire");
        ubicacionNeo4JDAO.conectar(dojo.getNombre(), dojo3.getNombre(), "mar");
        ubicacionNeo4JDAO.conectar(guarderia2.getNombre(), guarderia3.getNombre(), "tierra");
        ubicacionNeo4JDAO.conectar(guarderia3.getNombre(), dojo2.getNombre(), "tierra");
        ubicacionNeo4JDAO.conectar(dojo2.getNombre(), guarderia.getNombre(), "tierra");
        ubicacionNeo4JDAO.conectar(dojo3.getNombre(), guarderia.getNombre(), "aire");


        entrenador.setMonedas(20);
        entrenadorService.guardar(entrenador);

        mapaService.moverMasCorto(entrenador.nombre(), guarderia.getNombre());

        assertEquals(13, entrenadorService.recuperar(entrenador.nombre()).getMonedas(),0);
    }

    @Test(expected = UbicacionMuyLejanaException.class)
    public void ubicacionMuyLejana() {
        mapaService.crearUbicacion(dojo);
        mapaService.crearUbicacion(dojo2);
        mapaService.crearUbicacion(guarderia);
        ubicacionNeo4JDAO.conectar(dojo.getNombre(), dojo2.getNombre(), "tierra");
        ubicacionNeo4JDAO.conectar(dojo2.getNombre(), guarderia.getNombre(), "tierra");
        entrenador.setMonedas(5);
        entrenadorService.guardar(entrenador);

        mapaService.mover("entrenador", "Una guarderia");
    }

    @Test(expected = CaminoMuyCostosoException.class)
    public void caminoMuyCostoso() {
        mapaService.crearUbicacion(dojo);
        mapaService.crearUbicacion(dojo2);
        ubicacionNeo4JDAO.conectar(dojo.getNombre(), dojo2.getNombre(), "aire");
        entrenador.setMonedas(1);
        entrenadorService.guardar(entrenador);

        mapaService.mover("entrenador", "otro dojo");
    }



    @Test
    public void cantidadEntrenadores() {
        dojo.sumarPoblacion();
        ubicacionService.crearUbicacion(dojo);
        assertEquals(2, mapaService.cantidadEntrenadores(dojo.getNombre()));
    }

    @Test
    public void campeon() {
        bichoService.crearBicho(bicho1);
        ubicacionService.crearUbicacion(dojo);
        campeonService.actualizarCampeon(dojo.actualizarYRetornarCampeon(bicho1, fechaInicio1));
        ubicacionService.actualizarUbicacion(dojo);
        assertEquals("Pikachu", mapaService.campeon("Un dojo").getBicho().getEspecie().getNombre());
    }

    @Test
    public void campeonHistorico() {
        ubicacionService.crearUbicacion(dojo);
        bichoService.crearBicho(bicho1);
        bichoService.crearBicho(bicho2);
        bichoService.crearBicho(bicho3);
        campeonService.actualizarCampeon(dojo.actualizarYRetornarCampeon(bicho1, fechaInicio1));
        ubicacionService.actualizarUbicacion(dojo);
        campeonService.actualizarCampeon(dojo.actualizarYRetornarCampeon(bicho2, fechaInicio2));
        ubicacionService.actualizarUbicacion(dojo);
        campeonService.actualizarCampeon(dojo.actualizarYRetornarCampeon(bicho3, fechaInicio3));
        ubicacionService.actualizarUbicacion(dojo);
        assertEquals("Pikachu", mapaService.campeonHistorico("Un dojo").getEspecie().getNombre());
    }

    @Test(expected = NoHayCampeonHistoricoException.class)
    public void noHayCampeonHistorico() {
        ubicacionService.crearUbicacion(dojo);
        ubicacionService.crearUbicacion(dojo2);
        bichoService.crearBicho(bicho1);
        bichoService.crearBicho(bicho2);
        bichoService.crearBicho(bicho3);
        campeonService.actualizarCampeon(dojo.actualizarYRetornarCampeon(bicho1, fechaInicio1));
        ubicacionService.actualizarUbicacion(dojo);
        campeonService.actualizarCampeon(dojo.actualizarYRetornarCampeon(bicho2, fechaInicio2));
        ubicacionService.actualizarUbicacion(dojo2);
        campeonService.actualizarCampeon(dojo.actualizarYRetornarCampeon(bicho3, fechaInicio3));
        ubicacionService.actualizarUbicacion(dojo2);

        assertEquals("Charmander", mapaService.campeonHistorico("otro dojo").getEspecie().getNombre());
        mapaService.campeonHistorico("Un dojo");
    }
}