package ar.edu.unq.epers.bichomon.backend.model.condicion;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import org.junit.Test;
import org.junit.Before;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class BasadoEnEnergiaTest {

    private BasadoEnEnergia condicion;

    @Mock
    private Bicho bicho;

    @Before
    public void setUp() throws Exception{
        MockitoAnnotations.initMocks(this);
        condicion = new BasadoEnEnergia(150);

        }

    @Test
    public void testGetEnergia(){
        assertEquals(150, condicion.getEnergia());
    }

    @Test
    public void testCumpleConLaCondicionTrue(){
        when(bicho.getEnergia()).thenReturn(160);

        assertTrue(condicion.cumpleConLaCondicion(bicho));
    }

    @Test
    public void testCumpleConLaCondicionFalse(){
        when(bicho.getEnergia()).thenReturn(40);

        assertFalse(condicion.cumpleConLaCondicion(bicho));
    }
}