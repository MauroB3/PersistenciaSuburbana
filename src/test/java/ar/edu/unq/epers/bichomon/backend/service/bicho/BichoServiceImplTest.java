package ar.edu.unq.epers.bichomon.backend.service.bicho;

import ar.edu.unq.epers.bichomon.backend.dao.impl.*;
import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.condicion.BasadoEnVictoria;
import ar.edu.unq.epers.bichomon.backend.model.condicion.Condicion;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import ar.edu.unq.epers.bichomon.backend.model.especie.TipoBicho;
import ar.edu.unq.epers.bichomon.backend.model.nivel.NivelManager;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Guarderia;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Pueblo;
import ar.edu.unq.epers.bichomon.backend.service.condicion.CondicionService;
import ar.edu.unq.epers.bichomon.backend.service.condicion.CondicionServiceImpl;
import ar.edu.unq.epers.bichomon.backend.service.entrenador.EntrenadorService;
import ar.edu.unq.epers.bichomon.backend.service.especie.EspecieServiceImpl;
import ar.edu.unq.epers.bichomon.backend.service.nivel.NivelServiceImpl;
import ar.edu.unq.epers.bichomon.backend.service.ubicacion.UbicacionServiceImp;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BichoServiceImplTest {

    private HibernateBichoDAO bichoDAO;

    private HibernateEntrenadorDAO entrenadorDAO;

    private HibernateEspecieDAO especieDAO;

    private HibernateCondicionDAO condDAO;

    private CondicionService condService;

    private BichoServiceImpl bichoService;

    private UbicacionServiceImp ubicacionService;

    private EntrenadorService entrenadorService;

    private EspecieServiceImpl especieService;

    @Mock
    private NivelServiceImpl nivelService;

    @Mock
    private NivelManager manager;

    private Bicho bicho1;

    private Bicho bicho2;

    private Especie especie;

    private Especie especie2;

    private Especie especie3;

    private Pueblo pueblo;

    private Entrenador entrenador;

    private Condicion condVic;

    private Guarderia guarderia;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        when(nivelService.getNivelManager()).thenReturn(manager);
        when(manager.capacidadMaximaDeBichos(10)).thenReturn(10);

        condDAO = new HibernateCondicionDAO();
        nivelService = new NivelServiceImpl(new HibernateNivelDAO());
        bichoDAO = new HibernateBichoDAO();
        entrenadorDAO = new HibernateEntrenadorDAO();
        entrenadorService = new EntrenadorService(entrenadorDAO, nivelService);
        especieDAO = new HibernateEspecieDAO();
        ubicacionService = new UbicacionServiceImp(new HibernateUbicacionDAO());
        bichoService = new BichoServiceImpl(bichoDAO, entrenadorDAO, especieDAO, nivelService);
        condService = new CondicionServiceImpl(condDAO);
        especieService = new EspecieServiceImpl(especieDAO);

        pueblo = new Pueblo();
        pueblo.setNombre("Sporeland");

        guarderia = new Guarderia();
        guarderia.setNombre("Una guarderia");

        condVic = new BasadoEnVictoria(5);
        especie = new Especie("Onix",TipoBicho.CHOCOLATE, condVic,257,300,446);
        especie2 = new Especie("Charmander", TipoBicho.FUEGO, condVic, 55, 75, 110);
        especie3 = new Especie(especie2,2,condVic,"Charmeleon",88,100,300);
        entrenador = new Entrenador("Spore", guarderia);
        entrenador.agregarExperiencia(10);
        bicho1 = new Bicho(especie, entrenador);
        bicho2 = new Bicho(especie, entrenador);
    }

    @Test
    public void crearBicho(){
        bichoService.crearBicho(bicho1);

        assertEquals(446, bichoService.getBicho(1).getEnergia());
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


        assertFalse(bichoService.puedeEvolucionar("Spore", 4));
        assertTrue(bichoService.puedeEvolucionar("Spore", 2));
    }


    @Test
    public void abandonarBicho() {

        ubicacionService.crearUbicacion(guarderia);
        entrenadorService.guardar(entrenador);
        bichoService.crearBicho(bicho1);
        bichoService.crearBicho(bicho2);
        bichoService.abandonar(entrenador.nombre(), bicho1.getID());
        ubicacionService.actualizarUbicacion(guarderia);

        System.out.println("Aqui esta su resultado joven = " + entrenador.capacidadMaxima(manager));

        assertEquals(1, ubicacionService.getUbicacion("Una guarderia").getCantidadBichosAbandonados());
    }

}