package ar.edu.unq.epers.bichomon.backend.model.condicion;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import org.junit.Test;
import org.junit.Before;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class BasadoEnEdadTest {

    private BasadoEnEdad condicion;

    @Mock
    private Bicho bichoMock;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        condicion = new BasadoEnEdad(LocalDate.now());
    }

    @Test
    public void cumpleConLaCondicionTrue() {
        when(bichoMock.getFechaDeCaptura()).thenReturn(LocalDate.of(2030,11,19));

        assertTrue(condicion.cumpleConLaCondicion(bichoMock));
    }

    @Test
    public void cumpleConLaCondicionFalse(){
        when(bichoMock.getFechaDeCaptura()).thenReturn(LocalDate.of(1996,04,25));

        assertFalse(condicion.cumpleConLaCondicion(bichoMock));
    }

    @Test
    public void getCondicionFecha() {
        assertEquals(LocalDate.now(), condicion.getCondicionFecha());
    }
}