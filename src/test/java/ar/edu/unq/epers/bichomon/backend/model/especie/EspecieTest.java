package ar.edu.unq.epers.bichomon.backend.model.especie;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.condicion.CondicionCompuesta;
import org.junit.Test;
import org.junit.Before;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class EspecieTest {

    private Especie especieRaiz;

    private Especie especieEvolucion1;

    private Especie especieEvolucion2;

    @Mock
    private CondicionCompuesta condicionEvolucion;

    @Mock
    private CondicionCompuesta condicionEvolucion2;

    @Mock
    private CondicionCompuesta condicionEvolucion3;

     @Before
     public void setUp() throws Exception{
        MockitoAnnotations.initMocks(this);
        especieRaiz = new Especie("Mogo", TipoBicho.FUEGO,condicionEvolucion, 50, 6, 100);
        especieEvolucion1 = new Especie(especieRaiz,2,condicionEvolucion2,"Gonza", 70, 8, 200);
        especieEvolucion2 = new Especie(especieRaiz, 3, condicionEvolucion3,"Mauro", 100, 10, 300);

        Bicho bicho1 = especieRaiz.crearBicho();
        Bicho bicho2 = especieEvolucion1.crearBicho();
    }

    @Test
    public void testGetNombre(){
        assertEquals("Mogo", especieRaiz.getNombre());
        assertEquals("Gonza", especieEvolucion1.getNombre());
        assertEquals("Mauro", especieEvolucion2.getNombre());
    }

    @Test
    public void testGetAltura(){
        assertEquals(50, especieRaiz.getAltura(),0);
        assertEquals(70, especieEvolucion1.getAltura(),0);
        assertEquals(100, especieEvolucion2.getAltura(),0);
    }

    @Test
    public void testGetPeso(){
        assertEquals(6,especieRaiz.getPeso(),0);
        assertEquals(8,especieEvolucion1.getPeso(),0);
        assertEquals(10,especieEvolucion2.getPeso(),0);
    }


    @Test
    public void testGetCondicion(){
        assertEquals(condicionEvolucion, especieRaiz.getCondicionDeEvolucion());
        assertEquals(condicionEvolucion2, especieEvolucion1.getCondicionDeEvolucion());
        assertEquals(condicionEvolucion3, especieEvolucion2.getCondicionDeEvolucion());
    }

    @Test
    public void testCantidadBichosCreados(){
        assertEquals(1,especieRaiz.getCantidadBichos());
        assertEquals(1, especieEvolucion1.getCantidadBichos());
    }


    @Test
    public void testEsSiguienteEvolucion(){
        assertTrue(especieEvolucion1.esSiguienteEvolucion(especieRaiz));
    }

    @Test
    public void testBuscarSiguienteEvolucion(){
        assertEquals(especieEvolucion2, especieEvolucion1.buscarSiguienteEvolucion());
    }
}