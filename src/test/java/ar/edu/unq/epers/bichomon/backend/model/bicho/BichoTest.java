package ar.edu.unq.epers.bichomon.backend.model.bicho;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import ar.edu.unq.epers.bichomon.backend.model.duelo.Ataque;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;

public class BichoTest {

    private Bicho bicho;

    private Bicho otroBicho;

    @Mock
    private Especie especieAgua;

    @Mock
    private Especie especieAguaEvolucion;

    @Mock
    private Entrenador entrenador;

    @Mock
    private Ataque ataque;


    @Before
    public void setUp() throws Exception{
        MockitoAnnotations.initMocks(this);
        bicho = new Bicho(especieAgua);
        otroBicho = new Bicho(especieAgua);


    }

    @Test
    public void testGetEspecie(){
        assertEquals(especieAgua, bicho.getEspecie());
    }

    @Test //Controlar
    public void testGetEnergia(){
        bicho.setEnergia(100);

        assertEquals(100,bicho.getEnergia());
    }

    @Test //Controlar
    public void testGetFechaDeCaptura(){
        bicho.setFechaDeCaptura(LocalDate.now());

        assertEquals(LocalDate.now(),bicho.getFechaDeCaptura());
    }

    @Test
    public void testGetVictorias(){
        bicho.incrementarVictorias();

        assertEquals(1,bicho.getVictorias());
    }

    @Test
    public void testGetEntrenador(){
        bicho.setEntrenador(entrenador);

        assertEquals(entrenador, bicho.getEntrenador());
    }

    @Test
    public void testEvolucionar(){
        when(especieAgua.buscarSiguienteEvolucion()).thenReturn(especieAguaEvolucion);

        bicho.evolucionar();
        assertEquals(especieAguaEvolucion, bicho.getEspecie());
    }

    @Test
    public void testPuedeEvolucionar(){
        when(especieAgua.puedeEvolucionar(bicho)).thenReturn(true);

        assertTrue(bicho.puedeEvolucionar());
    }

    @Test
    public void testNoPuedeEvolucionar(){
        when(especieAgua.puedeEvolucionar(bicho)).thenReturn(false);

        assertFalse(bicho.puedeEvolucionar());
    }

    @Test
    public void testAtacar(){

        when(ataque.atacado()).thenReturn(0);
        when(ataque.atacante()).thenReturn(0);

        assertEquals(ataque.atacado(),bicho.atacar(otroBicho).atacado());
        assertEquals(ataque.atacante(),bicho.atacar(otroBicho).atacante());
    }

    @Test
    public void testIncrementarEnergia(){
        int energiaInicial = bicho.getEnergia();
        bicho.incrementarEnergia();

        assertTrue(bicho.getEnergia() > energiaInicial);

    }
}