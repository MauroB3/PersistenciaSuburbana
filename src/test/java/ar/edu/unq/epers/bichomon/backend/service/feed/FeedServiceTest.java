package ar.edu.unq.epers.bichomon.backend.service.feed;

import ar.edu.unq.epers.bichomon.backend.dao.mongodb.EventoDAO;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.evento.Arribo;
import ar.edu.unq.epers.bichomon.backend.model.evento.Evento;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Guarderia;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Pueblo;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Ubicacion;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FeedServiceTest {

    private EventoDAO eventoDAO;
    private FeedService feedService;

    private Entrenador entrenador;
    private Ubicacion guarderia;
    private Ubicacion pueblo;

    @Before
    public void setUp() {
        this.eventoDAO = new EventoDAO();
        this.feedService = new FeedService(eventoDAO);
        this.guarderia = new Guarderia();
        this.guarderia.setNombre("Bichomon Day Care");
        this.pueblo = new Pueblo();
        this.pueblo.setNombre("Pueblo Paleta");
        this.entrenador = new Entrenador("Mauro", guarderia);
    }

    @After
    public void cleanUp() {
        this.eventoDAO.deleteAll();
    }

    @Test
    public void guardarYRecuperarArribo() {
        this.feedService.guardarArribo(entrenador.nombre(), pueblo.getNombre(), guarderia.getNombre());

        assertEquals(1, this.eventoDAO.getArribosDeEntrenador(entrenador.nombre()).size());
    }

}
