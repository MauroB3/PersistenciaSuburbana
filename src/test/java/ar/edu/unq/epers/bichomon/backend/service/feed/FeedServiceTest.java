package ar.edu.unq.epers.bichomon.backend.service.feed;

import ar.edu.unq.epers.bichomon.backend.dao.UbicacionDAO;
import ar.edu.unq.epers.bichomon.backend.dao.impl.HibernateCampeonDAO;
import ar.edu.unq.epers.bichomon.backend.dao.impl.HibernateEntrenadorDAO;
import ar.edu.unq.epers.bichomon.backend.dao.impl.HibernateUbicacionDAO;
import ar.edu.unq.epers.bichomon.backend.dao.impl.UbicacionNeo4JDAO;
import ar.edu.unq.epers.bichomon.backend.dao.mongodb.EventoDAO;
import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import ar.edu.unq.epers.bichomon.backend.model.evento.Arribo;
import ar.edu.unq.epers.bichomon.backend.model.evento.Evento;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.*;
import ar.edu.unq.epers.bichomon.backend.service.entrenador.EntrenadorService;
import ar.edu.unq.epers.bichomon.backend.service.mapa.MapaService;
import ar.edu.unq.epers.bichomon.backend.service.runner.SessionFactoryProvider;
import ar.edu.unq.epers.bichomon.backend.service.ubicacion.UbicacionNeo4JDAOTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.when;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class FeedServiceTest {

    private EventoDAO eventoDAO;
    private FeedService feedService;
    private UbicacionNeo4JDAO ubicacionNeo4JDAO;
    private MapaService mapaService;

    private Entrenador entrenador;
    private Entrenador entrenador2;
    private Ubicacion guarderia;
    private Ubicacion pueblo;
    private Ubicacion dojo;
    private Ubicacion dojo2;
    private Especie especie;
    private Bicho bicho;

    @Mock
    private EntrenadorService entrenadorService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.eventoDAO = new EventoDAO();
        this.ubicacionNeo4JDAO = new UbicacionNeo4JDAO();
        this.feedService = new FeedService(eventoDAO, entrenadorService, ubicacionNeo4JDAO);
        this.mapaService = new MapaService(new HibernateUbicacionDAO(), new HibernateCampeonDAO(), new HibernateEntrenadorDAO(), ubicacionNeo4JDAO, feedService);
        this.guarderia = new Guarderia();
        this.guarderia.setNombre("Bichomon Day Care");
        this.pueblo = new Pueblo();
        this.pueblo.setNombre("Pueblo Paleta");
        this.dojo = new Dojo();
        this.dojo.setNombre("Dojo Karate");
        this.dojo2 = new Dojo();
        this.dojo2.setNombre("Dojo lejano");
        this.entrenador = new Entrenador("Mauro", guarderia);
        entrenador.setMonedas(100);
        this.entrenador2 = new Entrenador("Gonza", guarderia);
        entrenador2.setMonedas(100);
        this.especie = new Especie("Pikachu");
        this.bicho = new Bicho(especie, entrenador);

        mapaService.crearUbicacion(dojo);
        mapaService.crearUbicacion(dojo2);
        mapaService.crearUbicacion(guarderia);
        mapaService.crearUbicacion(pueblo);

        when(entrenadorService.recuperar(entrenador.nombre())).thenReturn(entrenador);
        when(entrenadorService.recuperar(entrenador2.nombre())).thenReturn(entrenador2);

    }

    @After
    public void cleanUp() {
        this.eventoDAO.deleteAll();
        SessionFactoryProvider.destroy();
        ubicacionNeo4JDAO.destroy();
    }

    @Test
    public void guardarYRecuperarArribo() {
        this.feedService.guardarArribo(entrenador.nombre(), pueblo.getNombre(), guarderia.getNombre());

        List<Evento> eventoList = this.feedService.feedEntrenador(entrenador.nombre());
        assertEquals(1, eventoList.size());
        assertEquals("Arribo", eventoList.get(0).getTipo());

    }

    @Test
    public void guardarYRecuperarCaptura() {
        this.feedService.guardarCaptura(entrenador.nombre(), bicho.getEspecie().getNombre(), pueblo.getNombre());

        List<Evento> eventoList = this.feedService.feedEntrenador(entrenador.nombre());
        assertEquals(1, eventoList.size());
        assertEquals("Captura", eventoList.get(0).getTipo());
    }

    @Test
    public void guardarYRecuperarCoronacion() {
        this.feedService.guardarCoronacion(entrenador.nombre(), entrenador2.nombre(), dojo.getNombre());

        List<Evento> eventoList = this.feedService.feedEntrenador(entrenador.nombre());
        assertEquals(1, eventoList.size());
        assertEquals("Coronacion", eventoList.get(0).getTipo());
    }

    @Test
    public void guardarYRecuperarAbandono(){
        this.feedService.guardarAbandono(entrenador.nombre(), especie.getNombre(), dojo.getNombre());
        List<Evento> ls = this.feedService.feedEntrenador(entrenador.nombre());
        assertEquals(1, ls.size());
        assertEquals("Abandono", ls.get(0).getTipo());
    }

    @Test
    public void feedEntrenadorTest() {
        entrenador.mover(dojo, 0);

        mapaService.conectar(dojo.getNombre(), guarderia.getNombre(), CostoCamino.tierra);
        mapaService.conectar(dojo.getNombre(), pueblo.getNombre(), CostoCamino.tierra);
        mapaService.conectar(pueblo.getNombre(), dojo2.getNombre(), CostoCamino.tierra);

        this.feedService.guardarCaptura(entrenador.nombre(), bicho.getEspecie().getNombre(), pueblo.getNombre());
        this.feedService.guardarAbandono(entrenador.nombre(), especie.getNombre(), dojo.getNombre());
        this.feedService.guardarArribo(entrenador.nombre(), pueblo.getNombre(), guarderia.getNombre());
        this.feedService.guardarAbandono(entrenador.nombre(), especie.getNombre(), dojo2.getNombre());
        this.feedService.guardarAbandono(entrenador2.nombre(), especie.getNombre(), dojo.getNombre());


        assertEquals(4, this.feedService.feedUbicacion(entrenador.nombre()).size());
    }

    @Test
    public void feedUbicacionTest() {
        entrenador.mover(dojo, 0);

        mapaService.conectar(dojo.getNombre(), guarderia.getNombre(), CostoCamino.tierra);
        mapaService.conectar(dojo.getNombre(), pueblo.getNombre(), CostoCamino.tierra);
        mapaService.conectar(pueblo.getNombre(), dojo2.getNombre(), CostoCamino.tierra);

        this.feedService.guardarCaptura(entrenador.nombre(), bicho.getEspecie().getNombre(), pueblo.getNombre());
        this.feedService.guardarAbandono(entrenador.nombre(), especie.getNombre(), dojo.getNombre());
        this.feedService.guardarArribo(entrenador.nombre(), pueblo.getNombre(), guarderia.getNombre());
        this.feedService.guardarAbandono(entrenador.nombre(), especie.getNombre(), dojo2.getNombre());

        assertEquals(3, this.feedService.feedUbicacion(entrenador.nombre()).size());

    }

}
