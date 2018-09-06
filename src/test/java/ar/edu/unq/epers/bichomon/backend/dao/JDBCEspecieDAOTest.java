package ar.edu.unq.epers.bichomon.backend.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import ar.edu.unq.epers.bichomon.backend.model.especie.TipoBicho;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ar.edu.unq.epers.bichomon.backend.service.data.*;
import ar.edu.unq.epers.bichomon.backend.dao.*;

import java.util.List;


public class JDBCEspecieDAOTest {

    private JDBCEspecieDAO dao = new JDBCEspecieDAO();
    private Especie red = new Especie();
    private Especie amarillo = new Especie();

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
    }

    @After
    public void borrarDatos() {
        dao.deleteAll();
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
    public void actualizarEspecie() {
        dao.guardar(red);

        TipoBicho tipo1 = TipoBicho.FUEGO;
        Especie especie1Actualizada = new Especie(1, "Rojomon", TipoBicho.FUEGO);
        especie1Actualizada.setPeso(75);
        especie1Actualizada.setAltura(180);
        especie1Actualizada.setCantidadBichos(10);
        especie1Actualizada.setEnergiaIncial(100);
        especie1Actualizada.setUrlFoto("/image/rojomon.jpg");

        dao.actualizar(especie1Actualizada);

        Especie otraEspecie = dao.recuperar("Rojomon");
        assertEquals(otraEspecie.getCantidadBichos(), especie1Actualizada.getCantidadBichos());


    }

}
