package ar.edu.unq.epers.bichomon.backend.model.duelo;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class ResultadoCombateTest {

    private Ataque ataque1;
    private Ataque ataque2;
    private Ataque ataque3;
    private ArrayList<Ataque> ataques;


    private ResultadoCombate resultado;

    @Before
    public void setUp(){
        ataques = new ArrayList<Ataque>();
        ataque1 = new Ataque(1,100,2);
        ataque2 = new Ataque(1,100.2,2);
        ataque3 = new Ataque(3,100.5,4);

        ataques.add(ataque1);
        ataques.add(ataque2);

        resultado = new ResultadoCombate(ataques);
    }

    @Test
    public void addAtaqueTest(){
        ataques.add(ataque3);
        assertEquals(3, ataques.size());
    }

    @Test
    public void ataqueRecibidoTest(){
        assertEquals(200.2, resultado.ataqueRecibido(1), 0);
    }
}