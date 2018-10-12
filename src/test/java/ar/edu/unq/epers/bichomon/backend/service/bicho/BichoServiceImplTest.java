package ar.edu.unq.epers.bichomon.backend.service.bicho;

import ar.edu.unq.epers.bichomon.backend.dao.impl.*;
import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.condicion.BasadoEnVictoria;
import ar.edu.unq.epers.bichomon.backend.model.condicion.Condicion;
import ar.edu.unq.epers.bichomon.backend.model.duelo.ResultadoCombate;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import ar.edu.unq.epers.bichomon.backend.model.especie.TipoBicho;
import ar.edu.unq.epers.bichomon.backend.model.nivel.Nivel;
import ar.edu.unq.epers.bichomon.backend.model.nivel.NivelManager;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Pueblo;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Ubicacion;
import ar.edu.unq.epers.bichomon.backend.service.Condicion.CondicionService;
import ar.edu.unq.epers.bichomon.backend.service.Condicion.CondicionServiceImpl;
import ar.edu.unq.epers.bichomon.backend.service.entrenador.EntrenadorService;
import ar.edu.unq.epers.bichomon.backend.service.especie.EspecieService;
import ar.edu.unq.epers.bichomon.backend.service.especie.EspecieServiceImpl;
import ar.edu.unq.epers.bichomon.backend.service.nivel.NivelServiceImpl;
import ar.edu.unq.epers.bichomon.backend.service.runner.SessionFactoryProvider;
import ar.edu.unq.epers.bichomon.backend.service.ubicacion.UbicacionServiceImp;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mock;

import static org.junit.Assert.*;

public class BichoServiceImplTest {

    private HibernateBichoDAO bichoDAO;

    private BichoServiceImpl service;

    private Bicho bicho;

    private Especie especie;

    private NivelManager manager;

    private Pueblo pueblo;

    private Entrenador entrenador;

    private Condicion condVic;

    @Before
    public void setUp(){
        bichoDAO = new HibernateBichoDAO();
        service = new BichoServiceImpl(bichoDAO);

        pueblo = new Pueblo();
        pueblo.setNombre("Sporeland");

        manager = new NivelManager(); /** No usar este constructor */
        condVic = new BasadoEnVictoria(5);
        especie = new Especie("Onix",TipoBicho.CHOCOLATE, condVic,257,300,446);
        entrenador = new Entrenador("Spore", manager, pueblo);
        bicho = new Bicho(especie, entrenador);
    }

    @Test
    public void crearBicho(){
        service.crearBicho(bicho);
    }


}