package ar.edu.unq.epers.bichomon.backend.model.condicion;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.*;
import ar.edu.unq.epers.bichomon.backend.model.nivel.Nivel;
import org.junit.Test;
import org.junit.Before;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class CondicionCompuestaTest {

    private CondicionCompuesta condicion;

    @Mock
    private Bicho bicho;

    @Mock
    private BasadoEnEdad condEdad;

    @Mock
    private BasadoEnNivel condNivel;

    @Mock
    private BasadoEnEnergia condEnergia;

    @Mock
    private BasadoEnVictoria condVictoria;

    @Before
    public void setUp() throws Exception{
        MockitoAnnotations.initMocks(this);
        condicion = new CondicionCompuesta();

        condicion.agregarCondicion(condEdad);
        condicion.agregarCondicion(condNivel);
        condicion.agregarCondicion(condEnergia);
        condicion.agregarCondicion(condVictoria);

        when(condEdad.cumpleConLaCondicion(bicho)).thenReturn(true);
        when(condNivel.cumpleConLaCondicion(bicho)).thenReturn(true);
        when(condEnergia.cumpleConLaCondicion(bicho)).thenReturn(true);
    }

    @Test
    public void testAgregarCondicion(){
        assertEquals(4, condicion.cantidadDeCondiciones());
    }

    @Test
    public void testRemoverCondicion(){
        condicion.removerCondicion(condEdad);

        assertEquals(3, condicion.cantidadDeCondiciones());
    }

    @Test
    public void testCumpleTodasLasCondiciones(){
        when(condVictoria.cumpleConLaCondicion(bicho)).thenReturn(true);

        assertTrue(condicion.cumpleConLaCondicion(bicho));
    }

    @Test
    public void testNoCumpleTodasLasCondiciones(){
        when(condVictoria.cumpleConLaCondicion(bicho)).thenReturn(false);

        assertFalse(condicion.cumpleConLaCondicion(bicho));
    }

}