package ar.edu.unq.epers.bichomon.backend.service.mapa;

import ar.edu.unq.epers.bichomon.backend.dao.UbicacionDAO;
import ar.edu.unq.epers.bichomon.backend.dao.impl.HibernateUbicacionDAO;
import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Campeon;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Dojo;
import org.hibernate.CacheMode;
import org.junit.*;

import java.time.LocalDate;

import static org.junit.Assert.*;

public class MapaServiceTest {

    private MapaService mapaService;
    private UbicacionDAO ubicacionDAO;
    private Dojo dojo;
    private Bicho bicho1;
    private Bicho bicho2;
    private Bicho bicho3;
    private LocalDate fechaInicio1;
    private LocalDate fechaInicio2;
    private LocalDate fechaInicio3;


    @Before
    public void setUp() {
        ubicacionDAO = new HibernateUbicacionDAO();
        mapaService = new MapaService(ubicacionDAO);

        dojo = new Dojo();
        Especie especie1 = new Especie("Pikachu");
        Especie especie2 = new Especie("Charmander");
        Especie especie3 = new Especie("Squirtle");

        Bicho bicho1 = new Bicho(especie1);
        Bicho bicho2 = new Bicho(especie2);
        Bicho bicho3 = new Bicho(especie3);

        LocalDate fechaInicio1 = LocalDate.parse("05.10.2018");
        LocalDate fechaInicio2 = LocalDate.parse("10.10.2018");
        LocalDate fechaInicio3 = LocalDate.parse("12.10.2018");
    }

    @Test
    public void mover() {

    }

    @Test
    public void cantidadEntrenadores() {
        dojo.sumarPoblacion();
        ubicacionDAO.actualizar(dojo);
        assertEquals(1, ubicacionDAO.recuperar(dojo.getNombre()).getPoblacion());
    }

    @Test
    public void campeon() {

    }

    @Test
    public void campeonHistorico() {

    }
}