package ar.edu.unq.epers.bichomon.backend.model.condicion;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import org.junit.Test;
import org.junit.Before;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class BasadoEnVictoriaTest {

    private BasadoEnVictoria condicion;

    @Mock
    private Bicho bicho;

    @Before
    public void setUp() throws Exception{
        MockitoAnnotations.initMocks(this);
        condicion = new BasadoEnVictoria(10);


    }

    @Test
    public void testGetCondicionVictorias(){
        assertEquals(10, condicion.getCondicionVictorias());
    }

    @Test
    public void testCumpleConLaCondicionTrue(){
        when(bicho.getVictorias()).thenReturn(18);

        assertTrue(condicion.cumpleConLaCondicion(bicho));
    }

    @Test
    public void testCumpleConLaCondicionFalse(){
        when(bicho.getVictorias()).thenReturn(7);

        assertFalse(condicion.cumpleConLaCondicion(bicho));
    }
}