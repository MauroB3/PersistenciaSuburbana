package ar.edu.unq.epers.bichomon.backend.model.ubicacion;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

public class PuebloTest {

    private Pueblo pueblo;

    @Mock
    public Especie especie1;

    @Mock
    public Especie especie2;

    @Mock
    public Entrenador entrenador;

    @Before
    public void setUp() {
        pueblo = new Pueblo();
    }

    @Test
    public void testGetYSetPoblacion() {
        assertEquals(0, pueblo.getPoblacion());
        pueblo.sumarPoblacion();
        assertEquals(1, pueblo.getPoblacion());
        pueblo.restarPoblacion();
        assertEquals(0, pueblo.getPoblacion());
    }

    @Test
    public void testEsPueblo() {
        assertTrue(pueblo.esPueblo());
    }

    @Test
    public void testAgregarEspecie() {
        pueblo.agregarEspecie(especie1, 100);
        assertEquals(1, pueblo.getEspeciesQueHabitan().size());
    }

    @Test
    public void testEliminarEspecie() {
        pueblo.agregarEspecie(especie1, 50);
        pueblo.agregarEspecie(especie2, 50);
        pueblo.eliminarEspecie(especie1);
        assertEquals(1, pueblo.getEspeciesQueHabitan().size());
    }

    @Test
    public void testFactorPoblacion() {
        //Por defecto el dividendoFactorPoblacion es 100
        pueblo.sumarPoblacion();
        pueblo.sumarPoblacion();
        assertEquals(50, pueblo.getFactorPoblacion());
    }

    @Test
    public void testBichoEncontrado() {
        pueblo.agregarEspecie(especie1, 100);
        Bicho bichoEncontrado = pueblo.bichoEncontrado(entrenador);
        assertEquals(especie1, bichoEncontrado.getEspecie());
    }



}