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
    private Especie especie1;
    private Especie especie2;

    @Before
    public void crearEspecies() {
        especie1 = new Especie();
        especie2 = new Especie();

        TipoBicho tipo1 = TipoBicho.FUEGO;
        especie1.setNombre("Especie 1");
        especie1.setId(1);
        especie1.setTipo(tipo1);
        especie1.setPeso(30);
        especie1.setAltura(40);
        especie1.setCantidadBichos(7);

        TipoBicho tipo2 = TipoBicho.AGUA;
        especie2.setNombre("Especie 2");
        especie2.setId(2);
        especie2.setTipo(tipo2);
        especie2.setPeso(60);
        especie2.setAltura(80);
        especie2.setCantidadBichos(2);
    }

    @After
    public void borrarDatos() {
        dao.deleteAll();
    }

    @Test
    public void guardarYRecuperar() {
        dao.guardar(especie1);

        Especie otraEspecie = dao.recuperar(especie1.getNombre());
        assertEquals(otraEspecie.getNombre(), especie1.getNombre());
        assertEquals(otraEspecie.getId(), especie1.getId());
        assertEquals(otraEspecie.getTipo(), especie1.getTipo());
        assertEquals(otraEspecie.getPeso(), especie1.getPeso());
        assertEquals(otraEspecie.getAltura(), especie1.getAltura());
        assertEquals(otraEspecie.getCantidadBichos(), especie1.getCantidadBichos());
    }

    @Test
    public void recuperarTodasLasEspecies() {
        dao.guardar(especie1);
        dao.guardar(especie2);

        List<Especie> especies = dao.recuperarTodos();

        assertEquals(especies.size(), 2);
    }

    @Test
    public void actualizarEspecie() {
        dao.guardar(especie1);

        TipoBicho tipo1 = TipoBicho.FUEGO;
        Especie especie1Actualizada = new Especie();
        especie1Actualizada.setNombre("Especie 1");
        especie1Actualizada.setId(1);
        especie1Actualizada.setTipo(tipo1);
        especie1Actualizada.setPeso(30);
        especie1Actualizada.setAltura(40);
        especie1Actualizada.setCantidadBichos(10);

        dao.actualizar(especie1Actualizada);

        Especie otraEspecie = dao.recuperar("Especie 1");
        assertEquals(otraEspecie.getCantidadBichos(), especie1Actualizada.getCantidadBichos());


    }

}
