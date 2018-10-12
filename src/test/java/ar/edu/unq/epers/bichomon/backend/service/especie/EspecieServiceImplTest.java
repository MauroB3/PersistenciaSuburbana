package ar.edu.unq.epers.bichomon.backend.service.especie;

import ar.edu.unq.epers.bichomon.backend.dao.BichoDAO;
import ar.edu.unq.epers.bichomon.backend.dao.impl.HibernateBichoDAO;
import ar.edu.unq.epers.bichomon.backend.dao.impl.HibernateEspecieDAO;
import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.condicion.Condicion;
import ar.edu.unq.epers.bichomon.backend.model.condicion.CondicionCompuesta;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import ar.edu.unq.epers.bichomon.backend.model.especie.TipoBicho;
import ar.edu.unq.epers.bichomon.backend.service.bicho.BichoService;
import ar.edu.unq.epers.bichomon.backend.service.runner.SessionFactoryProvider;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.nio.channels.Pipe;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class EspecieServiceImplTest {

    private EspecieServiceImpl service;
    private HibernateEspecieDAO hibernateEspecieDAO;
    private Condicion condicion;
    private BichoService bichoService;

    private Especie pikachu;

    private Especie charmander;

    private Especie squirtle;

    private ArrayList<Especie> especies;

    private Entrenador entrenador;

    @Before
    public void setUp() {
        bichoService = new BichoService(new HibernateBichoDAO());
        hibernateEspecieDAO = new HibernateEspecieDAO();
        service = new EspecieServiceImpl(hibernateEspecieDAO);
        condicion = new CondicionCompuesta();

        pikachu = new Especie("Pikachu", TipoBicho.ELECTRICIDAD, condicion,55,99,100);
        charmander = new Especie("Charmander", TipoBicho.FUEGO, condicion, 55, 187, 150);
        squirtle = new Especie("Squirtle", TipoBicho.AGUA, condicion, 55, 223, 100 );

        especies = new ArrayList<Especie>();

        //service.crearEspecie(pikachu);
    }

    /*
    @After
    public void cleanUp(){
            //Destroy cierra la session factory y fuerza a que, la proxima vez, una nueva tenga
            //que ser creada.
            //
            //Al tener hibernate configurado con esto <property name="hibernate.hbm2ddl.auto">create-drop</property>
            //al crearse una nueva session factory todo el schema será destruido y creado desde cero.
            SessionFactoryProvider.destroy();
    }

    @Test
    public void testCrearEspecieYGetEspecie() {
        assertEquals(pikachu.getNombre(), service.getEspecie("Pikachu").getNombre());
    }

    @Test(expected = EspecieNoExistente.class)
    public void testEspecieNoExistenteException(){
        assertEquals("Charmander", service.getEspecie("Charmander").getNombre());
    }

    @Test
    public void getAllEspecies() {
        especies.add(pikachu);
        assertEquals(especies.size(), service.getAllEspecies().size());
        service.crearEspecie(charmander);
        service.crearEspecie(squirtle);

        especies.add(charmander);
        especies.add(squirtle);
        assertEquals(especies.size(), service.getAllEspecies().size());
    }

    @Test
    public void crearBicho() {
        Bicho bicho = service.crearBicho("Pikachu");
        Bicho bicho2 = service.crearBicho("Pikachu");
        Bicho bicho3 = service.crearBicho("Pikachu");
        assertEquals(3, service.getEspecie("Pikachu").getCantidadBichos(),0);
    }

    @Test
    public void actualizarAltura(){
        Especie especie = service.getEspecie("Pikachu");
        especie.setAltura(60);
        service.actualizar(especie);

        assertEquals(60,service.getEspecie("Pikachu").getAltura(),0);
    }

    @Test
    public void populares(){

    }

    @Test
    public void impopulares(){

    }
    */

    @Test
    public void setUpBD(){
        service.crearEspecie(pikachu);
        service.crearEspecie(charmander);
        service.crearEspecie(squirtle);

        Bicho bicho1 = service.crearBicho("Pikachu", entrenador);
        Bicho bicho2 = service.crearBicho("Pikachu", entrenador);
        Bicho bicho3 = service.crearBicho("Pikachu", entrenador);
        Bicho bicho4 = service.crearBicho("Pikachu", entrenador);
        Bicho bicho5 = service.crearBicho("Pikachu", entrenador);

        Bicho bicho6 = service.crearBicho("Charmander", entrenador);
        Bicho bicho7 = service.crearBicho("Charmander", entrenador);
        Bicho bicho8 = service.crearBicho("Charmander", entrenador);

        Bicho bicho9 = service.crearBicho("Squirtle", entrenador);


        bicho1.serAbandonado();
        bicho7.serAbandonado();
        bicho9.serAbandonado();

        bichoService.crearBicho(bicho1);
        bichoService.crearBicho(bicho2);
        bichoService.crearBicho(bicho3);
        bichoService.crearBicho(bicho4);
        bichoService.crearBicho(bicho5);
        bichoService.crearBicho(bicho6);
        bichoService.crearBicho(bicho7);
        bichoService.crearBicho(bicho8);
        bichoService.crearBicho(bicho9);



        //assertEquals(2, service.populares().size());
    }

}