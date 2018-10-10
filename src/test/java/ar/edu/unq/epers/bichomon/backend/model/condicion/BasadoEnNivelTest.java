package ar.edu.unq.epers.bichomon.backend.model.condicion;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.*;
import org.junit.Test;
import org.junit.Before;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class BasadoEnNivelTest {

    private BasadoEnNivel condicion;

    @Mock
    private Entrenador entrenador;

    @Mock
    private Bicho bicho;

    @Before
    public void setUp() throws Exception{
        MockitoAnnotations.initMocks(this);
        condicion = new BasadoEnNivel(5);

        when(bicho.getEntrenador()).thenReturn(entrenador);
        when(entrenador.getNivel()).thenReturn(10);
    }

    @Test
    public void testGetCondicionNivel(){
        assertEquals(5, condicion.getCondicionNivel());
    }

    @Test
    public void testCumpleConLaCondicionTrue(){
        assertTrue(condicion.cumpleConLaCondicion(bicho));
    }

    @Test
    public void testCumpleConLaCondicionFalse(){
        when(entrenador.getNivel()).thenReturn(4);

        assertFalse(condicion.cumpleConLaCondicion(bicho));
    }

}