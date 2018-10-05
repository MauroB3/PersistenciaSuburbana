package ar.edu.unq.epers.bichomon.backend.dao;


import org.hibernate.Session;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Ubicacion;
import ar.edu.unq.epers.bichomon.backend.service.runner.Runner;

public class HibernateUbicacionDAO implements UbicacionDAO {

    @Override
    public void guardar(Ubicacion ubicacion) {
        Session session = Runner.getCurrentSession();
        session.save(ubicacion);
    }

    @Override
    public Ubicacion recuperar(String nombre) {
        Session session = Runner.getCurrentSession();
        return session.get(Ubicacion.class, nombre);
    }

}
