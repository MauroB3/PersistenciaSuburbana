package ar.edu.unq.epers.bichomon.backend.service.especie;

import ar.edu.unq.epers.bichomon.backend.dao.impl.HibernateEspecieDAO;
import ar.edu.unq.epers.bichomon.backend.model.condicion.Condicion;
import ar.edu.unq.epers.bichomon.backend.model.condicion.CondicionCompuesta;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import ar.edu.unq.epers.bichomon.backend.model.especie.TipoBicho;
import ar.edu.unq.epers.bichomon.backend.service.runner.SessionFactoryProvider;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class EspecieServiceImplTest {

    private EspecieServiceImpl service;
    private HibernateEspecieDAO hibernateEspecieDAO;
    private Condicion condicion;
    @Before
    public void setUp() {
        hibernateEspecieDAO = new HibernateEspecieDAO();
        service = new EspecieServiceImpl(hibernateEspecieDAO);
        condicion = new CondicionCompuesta();
    }


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
        Especie pikachu = new Especie(1,"Pikachu", TipoBicho.ELECTRICIDAD, condicion,55,99,100);
        this.service.crearEspecie(pikachu);

        assertEquals(pikachu.getNombre(), service.getEspecie(pikachu.getNombre()).getNombre());
    }

    @Test
    public void getAllEspecies() {
    }

    @Test
    public void crearBicho() {
    }
}