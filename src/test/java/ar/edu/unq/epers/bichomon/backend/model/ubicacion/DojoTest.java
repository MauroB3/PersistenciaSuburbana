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

public class DojoTest {

    private Dojo dojo;
    @Mock
    private Entrenador entrenador;

    @Mock
    private Bicho bicho1;

    @Mock
    private Bicho bicho2;

    @Mock
    private Especie especieBicho;

    @Mock
    private Especie especieRaiz;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        dojo = new Dojo();
        dojo.setCampeon(bicho1);
        when(bicho1.getEspecie()).thenReturn(especieBicho);
        when(especieBicho.getEspecieRaiz()).thenReturn(especieRaiz);
        when(entrenador.factorNivel()).thenReturn(1);
        when(entrenador.factorTiempo()).thenReturn(1);
    }

    @Test
    public void testGetYSetCampeonActual() {
        dojo.setCampeon(bicho1);
        assertEquals(dojo.getCampeon(), bicho1);
    }

    @Test
    public void testGetYSetCampeonNuevo() {
        dojo.setCampeon(bicho2);
        assertEquals(dojo.getCampeon(), bicho2);
    }

    @Test
    public void testEsDojo() {
        assertTrue(dojo.esDojo());
    }

    @Test
    public void testBusqueda() {
        dojo.setCampeon(bicho1);
        Especie especie = dojo.bichoEncontrado(entrenador).getEspecie();
        assertEquals(especie, especieRaiz);
    }
}