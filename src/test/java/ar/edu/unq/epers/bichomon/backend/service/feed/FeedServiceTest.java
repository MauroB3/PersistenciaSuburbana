package ar.edu.unq.epers.bichomon.backend.service.feed;

import ar.edu.unq.epers.bichomon.backend.dao.mongodb.EventoDAO;
import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import ar.edu.unq.epers.bichomon.backend.model.evento.Arribo;
import ar.edu.unq.epers.bichomon.backend.model.evento.Evento;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Dojo;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Guarderia;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Pueblo;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Ubicacion;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class FeedServiceTest {

    private EventoDAO eventoDAO;
    private FeedService feedService;

    private Entrenador entrenador;
    private Entrenador entrenador2;
    private Ubicacion guarderia;
    private Ubicacion pueblo;
    private Ubicacion dojo;
    private Especie especie;
    private Bicho bicho;

    @Before
    public void setUp() {
        this.eventoDAO = new EventoDAO();
        this.feedService = new FeedService(eventoDAO);
        this.guarderia = new Guarderia();
        this.guarderia.setNombre("Bichomon Day Care");
        this.pueblo = new Pueblo();
        this.pueblo.setNombre("Pueblo Paleta");
        this.dojo = new Dojo();
        this.dojo.setNombre("Dojo Karate");
        this.entrenador = new Entrenador("Mauro", guarderia);
        this.entrenador2 = new Entrenador("Gonza", guarderia);
        this.especie = new Especie("Pikachu");
        this.bicho = new Bicho(especie, entrenador);
    }


    @After
    public void cleanUp() {
        this.eventoDAO.deleteAll();
    }

    @Test
    public void guardarYRecuperarArribo() {
        this.feedService.guardarArribo(entrenador.nombre(), pueblo.getNombre(), guarderia.getNombre());

        List<Evento> eventoList = this.feedService.getEventosDeEntrenador(entrenador);
        assertEquals(1, eventoList.size());
        assertEquals("Arribo", eventoList.get(0).getTipo());

    }

    @Test
    public void guardarYRecuperarCaptura() {
        this.feedService.guardarCaptura(entrenador.nombre(), bicho.getEspecie().getNombre(), pueblo.getNombre());

        List<Evento> eventoList = this.feedService.getEventosDeEntrenador(entrenador);
        assertEquals(1, eventoList.size());
        assertEquals("Captura", eventoList.get(0).getTipo());
    }

    @Test
    public void guardarYRecuperarCoronacion() {
        this.feedService.guardarCoronacion(entrenador.nombre(), entrenador2.nombre(), dojo.getNombre());

        List<Evento> eventoList = this.feedService.getEventosDeEntrenador(entrenador);
        assertEquals(1, eventoList.size());
        assertEquals("Coronacion", eventoList.get(0).getTipo());
    }

    @Test
    public void guardarYRecuperarAbandono(){
        this.feedService.guardarAbandono(entrenador.nombre(), especie.getNombre(), dojo.getNombre());

        List<Evento> ls = this.feedService.getEventosDeEntrenador(entrenador);
        assertEquals(1, ls.size());
        assertEquals("Abandono", ls.get(0).getTipo());
    }

}
