package ar.edu.unq.epers.bichomon.backend.model.ubicacion;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;


public class GuarderiaTest {

    private Guarderia guarderia = new Guarderia();

    @Mock
    private Bicho bicho1;

    @Mock
    private Bicho bicho2;

    @Mock
    private Bicho bicho3;

    @Mock
    private Entrenador entrenador1;

    @Mock
    private Entrenador entrenador2;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        when(entrenador1.factorNivel()).thenReturn(1);
        when(entrenador1.factorTiempo()).thenReturn(1);
        when(bicho1.getEntrenador()).thenReturn(entrenador1);
        when(bicho2.getEntrenador()).thenReturn(entrenador2);
        when(bicho3.getEntrenador()).thenReturn(entrenador2);
    }

    @Test
    public void testEsGuarderia() {
        assertTrue(guarderia.esGuarderia());
    }

    @Test
    public void testAbandonarYRecuperarBicho() {
        guarderia.abandonarBicho(bicho1);
        assertTrue(guarderia.getBichosAbandonados().contains(bicho1));
    }

    @Test
    public void testBuscarBicho() {
        guarderia.abandonarBicho(bicho1);
        guarderia.abandonarBicho(bicho2);
        guarderia.abandonarBicho(bicho3);
        assertEquals(3, guarderia.getBichosAbandonados().size());
        Bicho bichoEncontrado = guarderia.bichoEncontrado(entrenador1);
        assertEquals(2, guarderia.getBichosAbandonados().size());
    }


}