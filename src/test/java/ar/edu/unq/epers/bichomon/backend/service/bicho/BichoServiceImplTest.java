package ar.edu.unq.epers.bichomon.backend.service.bicho;

import ar.edu.unq.epers.bichomon.backend.dao.CampeonDAO;
import ar.edu.unq.epers.bichomon.backend.dao.impl.*;
import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.campeon.Campeon;
import ar.edu.unq.epers.bichomon.backend.model.condicion.BasadoEnVictoria;
import ar.edu.unq.epers.bichomon.backend.model.condicion.Condicion;
import ar.edu.unq.epers.bichomon.backend.model.duelo.Duelo;
import ar.edu.unq.epers.bichomon.backend.model.duelo.ResultadoCombate;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import ar.edu.unq.epers.bichomon.backend.model.especie.TipoBicho;
import ar.edu.unq.epers.bichomon.backend.model.experiencia.Experiencia;
import ar.edu.unq.epers.bichomon.backend.model.nivel.Nivel;
import ar.edu.unq.epers.bichomon.backend.model.nivel.NivelManager;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Dojo;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Guarderia;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Pueblo;
import ar.edu.unq.epers.bichomon.backend.service.condicion.CondicionService;
import ar.edu.unq.epers.bichomon.backend.service.condicion.CondicionServiceImpl;
import ar.edu.unq.epers.bichomon.backend.service.entrenador.EntrenadorService;
import ar.edu.unq.epers.bichomon.backend.service.especie.EspecieServiceImpl;
import ar.edu.unq.epers.bichomon.backend.service.experiencia.ExperienciaServiceImpl;
import ar.edu.unq.epers.bichomon.backend.service.mapa.MapaService;
import ar.edu.unq.epers.bichomon.backend.service.nivel.NivelServiceImpl;
import ar.edu.unq.epers.bichomon.backend.service.runner.SessionFactoryProvider;
import ar.edu.unq.epers.bichomon.backend.service.ubicacion.UbicacionServiceImp;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.cglib.core.Local;

import java.time.LocalDate;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BichoServiceImplTest {

    private HibernateExperienciaDAO experienciaDAO;

    private HibernateCampeonDAO campeonDAO;

    private HibernateBichoDAO bichoDAO;

    private HibernateEntrenadorDAO entrenadorDAO;

    private HibernateEspecieDAO especieDAO;

    private HibernateCondicionDAO condDAO;

    private HibernateUbicacionDAO ubiDAO;

    private CondicionService condService;

    private BichoServiceImpl bichoService;

    private UbicacionServiceImp ubicacionService;

    private EntrenadorService entrenadorService;

    private EspecieServiceImpl especieService;

    private ExperienciaServiceImpl experienciaService;

    private MapaService mapaService;

    private NivelServiceImpl nivelService;

    private HibernateNivelDAO nivelDAO;

    private Nivel nivel1;

    private Especie especie;

    private Especie especie2;

    private Especie especie3;

    private Pueblo pueblo;

    private Entrenador entrenador;

    private Entrenador entrenador2;

    private Condicion condVic;

    private Guarderia guarderia;

    @Before
    public void setUp(){
        experienciaDAO = new HibernateExperienciaDAO();
        experienciaService = new ExperienciaServiceImpl(experienciaDAO);
        campeonDAO = new HibernateCampeonDAO();
        ubiDAO = new HibernateUbicacionDAO();
        condDAO = new HibernateCondicionDAO();
        nivelService = new NivelServiceImpl(new HibernateNivelDAO());
        bichoDAO = new HibernateBichoDAO();
        entrenadorDAO = new HibernateEntrenadorDAO();
        entrenadorService = new EntrenadorService(entrenadorDAO, nivelService);
        especieDAO = new HibernateEspecieDAO();
        especieService = new EspecieServiceImpl(especieDAO);
        ubicacionService = new UbicacionServiceImp(ubiDAO);
        bichoService = new BichoServiceImpl(bichoDAO, entrenadorDAO, especieDAO, nivelService, ubiDAO, experienciaDAO);
        condService = new CondicionServiceImpl(condDAO);
        especieService = new EspecieServiceImpl(especieDAO);
        mapaService = new MapaService(ubiDAO, campeonDAO, entrenadorDAO);

        nivelDAO = new HibernateNivelDAO();
        nivelService = new NivelServiceImpl(nivelDAO);

        nivel1 = new Nivel(1,1,99);
        nivelService.crearNivel(nivel1);

        pueblo = new Pueblo();
        pueblo.setNombre("Sporeland");

        guarderia = new Guarderia();
        guarderia.setNombre("Una guarderia");

        condVic = new BasadoEnVictoria(5);
        especie = new Especie("Onix",TipoBicho.CHOCOLATE, condVic,257,300,9999999);
        especie2 = new Especie("Charmander", TipoBicho.FUEGO, condVic, 55, 75, 110);
        especie3 = new Especie(especie2,2,condVic,"Charmeleon",88,100,300);

        entrenador = new Entrenador("Spore", guarderia);
        entrenador.agregarExperiencia(10);
        entrenador2 = new Entrenador("Mauro", guarderia);
        entrenador2.agregarExperiencia(10);

        experienciaService.crearExperiencia("Combate", 10);
        experienciaService.crearExperiencia("Captura", 10);
        experienciaService.crearExperiencia("Evolucion", 5);
    }

    @After
    public void cleanUp(){
        //Destroy cierra la session factory y fuerza a que, la proxima vez, una nueva tenga
        //que ser creada.
        //
        //Al tener hibernate configurado con esto <property name="hibernate.hbm2ddl.auto">create-drop</property>
        //al crearse una nueva session factory todo el schema será destruido y creado desde cero.
        SessionFactoryProvider.destroy();
    }

    @Test
    public void crearBicho() {
        especieService.crearEspecie(especie);
        Bicho bicho1 = especieService.crearBicho("Onix", entrenador);
        bichoService.crearBicho(bicho1);

        assertEquals(9999999, bichoService.getBicho(bicho1.getID()).getEnergia());
    }

    @Test
    public void puedeEvolucionar(){
        especieService.crearEspecie(especie2);
        especieService.crearEspecie(especie3);
        Bicho bicho3 = especieService.crearBicho("Charmander",entrenador);
        bicho3.incrementarVictorias();
        bicho3.incrementarVictorias();
        bicho3.incrementarVictorias();
        bicho3.incrementarVictorias();
        bicho3.incrementarVictorias();
        bicho3.incrementarVictorias();
        bichoService.crearBicho(bicho3);
        condService.crearCondicion(condVic);
        Bicho bicho4 = especieService.crearBicho("Charmeleon", entrenador);
        bicho4.incrementarVictorias();
        bicho4.incrementarVictorias();
        bicho4.incrementarVictorias();
        bicho4.incrementarVictorias();
        bicho4.incrementarVictorias();
        bicho4.incrementarVictorias();
        bicho4.incrementarVictorias();
        bicho4.incrementarVictorias();
        bichoService.crearBicho(bicho4);

        assertFalse(bichoService.puedeEvolucionar("Spore", bicho4.getID()));
        assertTrue(bichoService.puedeEvolucionar("Spore", bicho3.getID()));
    }

    @Test
    public void evolucionar(){
        especieService.crearEspecie(especie2);
        especieService.crearEspecie(especie3);
        Bicho bicho3 = especieService.crearBicho("Charmander",entrenador);
        bicho3.incrementarVictorias();
        bicho3.incrementarVictorias();
        bicho3.incrementarVictorias();
        bicho3.incrementarVictorias();
        bicho3.incrementarVictorias();
        bicho3.incrementarVictorias();
        bichoService.crearBicho(bicho3);
        condService.crearCondicion(condVic);
        Bicho bicho4 = especieService.crearBicho("Charmeleon", entrenador);
        bicho4.incrementarVictorias();
        bicho4.incrementarVictorias();
        bicho4.incrementarVictorias();
        bicho4.incrementarVictorias();
        bicho4.incrementarVictorias();
        bicho4.incrementarVictorias();
        bicho4.incrementarVictorias();
        bicho4.incrementarVictorias();
        bichoService.crearBicho(bicho4);

        assertEquals(especie3.getNombre(), bichoService.evolucionar(entrenador.nombre(), bicho3.getID()).getEspecie().getNombre());
    }

    @Test
    public void abandonarBicho() {

        ubicacionService.crearUbicacion(guarderia);
        entrenadorService.guardar(entrenador);
        especieService.crearEspecie(especie);
        especieService.crearEspecie(especie2);
        Bicho bicho1 = especieService.crearBicho("Onix", entrenador);
        bichoService.crearBicho(bicho1);
        Bicho bicho2 = especieService.crearBicho("Onix", entrenador);
        bichoService.crearBicho(bicho2);
        ubicacionService.actualizarUbicacion(guarderia);
        entrenadorService.agregarBicho(entrenador, bicho1);
        entrenadorService.agregarBicho(entrenador, bicho2);

        bichoService.abandonar(entrenador.nombre(), bicho1.getID());

        assertEquals(1, entrenadorService.recuperar("Spore").cantidadBichos());
        assertEquals(1, ubicacionService.getUbicacion("Una guarderia").getCantidadBichosAbandonados());
        assertEquals(1, especieService.getEspecie("Onix").getPopularidad());
    }

    @Test
    public void adoptarBicho() {

        ubicacionService.crearUbicacion(guarderia);
        entrenadorService.guardar(entrenador);
        especieService.crearEspecie(especie);
        especieService.crearEspecie(especie2);
        Bicho bicho1 = especieService.crearBicho("Onix", entrenador);
        bichoService.crearBicho(bicho1);
        Bicho bicho2 = especieService.crearBicho("Onix", entrenador);
        bichoService.crearBicho(bicho2);
        Bicho bicho3 = especieService.crearBicho("Charmander", entrenador2);
        bichoService.crearBicho(bicho3);
        Bicho bicho4 = especieService.crearBicho("Charmander", entrenador2);
        bichoService.crearBicho(bicho4);
        ubicacionService.actualizarUbicacion(guarderia);
        entrenadorService.agregarBicho(entrenador, bicho1);
        entrenadorService.agregarBicho(entrenador, bicho2);
        entrenadorService.agregarBicho(entrenador2, bicho3);
        entrenadorService.agregarBicho(entrenador2, bicho4);
        bichoService.abandonar(entrenador2.nombre(), bicho4.getID());
        ubicacionService.actualizarUbicacion(guarderia);

        bichoService.buscar(entrenador.nombre());

        assertEquals(3, entrenadorService.recuperar(entrenador.nombre()).cantidadBichos());
        assertEquals(1, entrenadorService.recuperar(entrenador2.nombre()).cantidadBichos());
    }

    @Test
    public void duelo(){
        especieService.crearEspecie(especie);
        especieService.crearEspecie(especie3);
        Bicho bichoCampeon = especieService.crearBicho("Charmeleon", entrenador2);
        Bicho bichoRetador = especieService.crearBicho("Onix", entrenador);

        bichoService.crearBicho(bichoCampeon);
        bichoService.crearBicho(bichoRetador);

        Dojo dojo = new Dojo();
        dojo.setNombre("Cobra Kai");
        Campeon campeon = dojo.actualizarYRetornarCampeon(bichoCampeon,LocalDate.now());

        ubicacionService.crearUbicacion(dojo);
        mapaService.mover("Spore","Cobra Kai");
        mapaService.mover("Mauro", "Cobra Kai");

        ResultadoCombate resultado = bichoService.duelo("Spore", bichoRetador.getID());

        assertEquals(bichoRetador.getID(), resultado.getGanador().getID());
    }

}