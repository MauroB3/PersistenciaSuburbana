package ar.edu.unq.epers.bichomon.backend.dao.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import ar.edu.unq.epers.bichomon.backend.dao.impl.HibernateEspecieDAO;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import ar.edu.unq.epers.bichomon.backend.model.especie.TipoBicho;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ar.edu.unq.epers.bichomon.backend.dao.impl.*;
import ar.edu.unq.epers.bichomon.backend.model.bicho.*;
import ar.edu.unq.epers.bichomon.backend.service.runner.*;

import ar.edu.unq.epers.bichomon.backend.service.data.*;
import ar.edu.unq.epers.bichomon.backend.dao.*;

import java.util.List;


public class HibernateEspecieDAOTest {

    private HibernateEspecieDAO dao = new HibernateEspecieDAO();
    private Especie red = new Especie();
    private Especie amarillo = new Especie();
    private Especie green = new Especie();

    @Before
    public void crearEspecies() {
        red.setNombre("Rojomon");
        red.setTipo(TipoBicho.FUEGO);
        red.setAltura(180);
        red.setPeso(75);
        red.setCantidadBichos(10);
        red.setEnergiaIncial(100);
        red.setUrlFoto("/image/rojomon.jpg");

        amarillo.setNombre("Amarillomon");
        amarillo.setTipo(TipoBicho.ELECTRICIDAD);
        amarillo.setAltura(170);
        amarillo.setPeso(69);
        amarillo.setCantidadBichos(5);
        amarillo.setEnergiaIncial(300);
        amarillo.setUrlFoto("/image/amarillomon.png");

        green.setNombre("Verdemon");
        green.setTipo(TipoBicho.PLANTA);
        green.setAltura(150);
        green.setPeso(55);
        green.setCantidadBichos(3);
        green.setEnergiaIncial(6000);
        green.setUrlFoto("/image/verdemon.jpg");
    }

    /*
    @After
    public void borrarDatos() {
        dao.deleteAll();
    }
    */

    @After
    public void cleanup() {
        //Destroy cierra la session factory y fuerza a que, la proxima vez, una nueva tenga
        //que ser creada.
        //
        //Al tener hibernate configurado con esto <property name="hibernate.hbm2ddl.auto">create-drop</property>
        //al crearse una nueva session factory todo el schema será destruido y creado desde cero.
        SessionFactoryProvider.destroy();
    }

    @Test
    public void guardarYRecuperar() {
        dao.guardar(red);

        Especie otraEspecie = dao.recuperar(red.getNombre());
        assertEquals(otraEspecie.getNombre(), red.getNombre());
        assertEquals(otraEspecie.getTipo(), red.getTipo());
        assertEquals(otraEspecie.getPeso(), red.getPeso());
        assertEquals(otraEspecie.getAltura(), red.getAltura());
        assertEquals(otraEspecie.getCantidadBichos(), red.getCantidadBichos());
        assertEquals(otraEspecie.getEnergiaInicial(), red.getEnergiaInicial());
        assertEquals(otraEspecie.getUrlFoto(), red.getUrlFoto());
    }

    @Test
    public void recuperarTodasLasEspecies() {
        dao.guardar(red);
        dao.guardar(amarillo);

        List<Especie> especies = dao.recuperarTodos();

        assertEquals(especies.size(), 2);
    }

    @Test
    public void recuperarTodasEnOrdenAlfabetico() {
        dao.guardar(green);
        dao.guardar(red);
        dao.guardar(amarillo);

        List<Especie> especies = dao.recuperarTodos();
        assertEquals(especies.get(0).getNombre(), amarillo.getNombre());
        assertEquals(especies.get(1).getNombre(), red.getNombre());
        assertEquals(especies.get(2).getNombre(), green.getNombre());
    }

    /*
    @Test
    public void actualizarEspecie() {
        dao.guardar(red);

        TipoBicho tipo1 = TipoBicho.FUEGO;
        Especie especie1Actualizada = new Especie("Rojomon", TipoBicho.FUEGO);
        especie1Actualizada.setPeso(75);
        especie1Actualizada.setAltura(180);
        especie1Actualizada.setCantidadBichos(10);
        especie1Actualizada.setEnergiaIncial(100);
        especie1Actualizada.setUrlFoto("/image/rojomon.jpg");

        dao.actualizar(especie1Actualizada);

        Especie otraEspecie = dao.recuperar("Rojomon");
        assertEquals(otraEspecie.getCantidadBichos(), especie1Actualizada.getCantidadBichos());
    }
    */
}
