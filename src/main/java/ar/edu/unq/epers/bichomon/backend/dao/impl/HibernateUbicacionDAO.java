package ar.edu.unq.epers.bichomon.backend.dao.impl;

import ar.edu.unq.epers.bichomon.backend.dao.UbicacionDAO;
import ar.edu.unq.epers.bichomon.backend.model.campeon.Campeon;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.*;
import ar.edu.unq.epers.bichomon.backend.service.runner.Runner;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;
import static java.time.temporal.ChronoUnit.DAYS;

public class HibernateUbicacionDAO implements UbicacionDAO {

    public void guardar(Ubicacion ubicacion){
        Session session = Runner.getCurrentSession();
        session.save(ubicacion);
    }

    public void actualizar(Ubicacion ubicacion) {
        Session session = Runner.getCurrentSession();
        session.update(ubicacion);
    }

    public void eliminar(Ubicacion ubicacion) {
        Session session = Runner.getCurrentSession();
        session.remove(ubicacion);
    }

    public Ubicacion recuperar(String nombreUbicacion) {
        Session session = Runner.getCurrentSession();
        return session.get(Ubicacion.class, nombreUbicacion);
    }

    public List<Ubicacion> recuperarTodos() {
        Session session = Runner.getCurrentSession();

        String hql = "from Ubicacion i";

        Query<Ubicacion> query = session.createQuery(hql, Ubicacion.class);
        return query.getResultList();
    }

    public int getCantidadEntrenadores(String nombreUbicacion) {
        Session session = Runner.getCurrentSession();

        return session.get(Ubicacion.class, nombreUbicacion).getPoblacion();
    }
}
