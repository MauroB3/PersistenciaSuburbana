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
import ar.edu.unq.epers.bichomon.backend.service.entrenador.EntrenadorService;
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

    private BichoServiceImpl bichoService;

    private UbicacionServiceImp ubicacionService;

    private EntrenadorService entrenadorService;

    @Mock
    private NivelServiceImpl nivelService;

    @Mock
    private NivelManager manager;

    private Bicho bicho1;

    private Bicho bicho2;

    private Especie especie;

    private Pueblo pueblo;

    private Entrenador entrenador;

    private Condicion condVic;

    private Guarderia guarderia;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        when(nivelService.getNivelManager()).thenReturn(manager);
        when(manager.capacidadMaximaDeBichos(10)).thenReturn(10);

        nivelService = new NivelServiceImpl(new HibernateNivelDAO());
        bichoDAO = new HibernateBichoDAO();
        entrenadorDAO = new HibernateEntrenadorDAO();
        entrenadorService = new EntrenadorService(entrenadorDAO, nivelService);
        especieDAO = new HibernateEspecieDAO();
        ubicacionService = new UbicacionServiceImp(new HibernateUbicacionDAO());
        bichoService = new BichoServiceImpl(bichoDAO, entrenadorDAO, especieDAO, nivelService);

        pueblo = new Pueblo();
        pueblo.setNombre("Sporeland");

        guarderia = new Guarderia();
        guarderia.setNombre("Una guarderia");

        condVic = new BasadoEnVictoria(5);
        especie = new Especie("Onix",TipoBicho.CHOCOLATE, condVic,257,300,446);
        entrenador = new Entrenador("Spore", guarderia);
        entrenador.agregarExperiencia(10);
        bicho1 = new Bicho(especie, entrenador);
        bicho2 = new Bicho(especie, entrenador);
    }

    @Test
    public void crearBicho(){
        bichoService.crearBicho(bicho1);
    }

    @Test
    public void abandonarBicho() {
        ubicacionService.crearUbicacion(guarderia);
        entrenadorService.guardar(entrenador);
        bichoService.crearBicho(bicho1);
        bichoService.crearBicho(bicho2);
        bichoService.abandonar(entrenador.nombre(), bicho1.getID());
        ubicacionService.actualizarUbicacion(guarderia);

        assertEquals(1, ubicacionService.getUbicacion("Una guarderia").getCantidadBichosAbandonados());



    }

}