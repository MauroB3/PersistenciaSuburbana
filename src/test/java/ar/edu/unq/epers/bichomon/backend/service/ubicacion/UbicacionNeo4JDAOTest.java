package ar.edu.unq.epers.bichomon.backend.service.ubicacion;

import ar.edu.unq.epers.bichomon.backend.dao.impl.UbicacionNeo4JDAO;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Guarderia;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Ubicacion;
import org.junit.Before;
import org.junit.Test;

public class UbicacionNeo4JDAOTest {

    private UbicacionNeo4JDAO dao;

    @Before
    public void setUp() {
        this.dao = new UbicacionNeo4JDAO();
    }

    @Test
    public void crearUbicacion() {
        Ubicacion guarderia1 = new Guarderia();
        guarderia1.setNombre("Guarderia1");
        this.dao.crearUbicacion(guarderia1);

        Ubicacion guarderia2 = new Guarderia();
        guarderia2.setNombre("Guarderia2");
        this.dao.crearUbicacion(guarderia2);

    }

    @Test
    public void conectarUbicaciones() {
        Ubicacion guarderia1 = new Guarderia();
        guarderia1.setNombre("Guarderia1");
        this.dao.crearUbicacion(guarderia1);

        Ubicacion guarderia2 = new Guarderia();
        guarderia2.setNombre("Guarderia2");
        this.dao.crearUbicacion(guarderia2);

        this.dao.conectarUbicaciones(guarderia1, guarderia2, "tierra");

    }


}
