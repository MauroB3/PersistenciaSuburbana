package ar.edu.unq.epers.bichomon.backend.dao.impl;

import ar.edu.unq.epers.bichomon.backend.dao.UbicacionDAO;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.*;
import ar.edu.unq.epers.bichomon.backend.service.runner.Runner;
import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.print.attribute.standard.ReferenceUriSchemesSupported;
import java.time.LocalDate;
import java.util.List;
import static java.time.temporal.ChronoUnit.DAYS;

public class HibernateUbicacionDAO implements UbicacionDAO {

    public void guardar(Ubicacion ubicacion){
        Session session = Runner.getCurrentSession();
        session.save(ubicacion);
    }

    public void actualizar(Ubicacion ubicacion) {
        Session session = Runner.getCurrentSession();
        session.save(ubicacion);
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

    public Campeon getCampeon(String nombreDojo) {
        Session session = Runner.getCurrentSession();

        return session.get(Dojo.class, nombreDojo).getCampeon();
    }

    public Campeon getCampeonHistorico(String nombreUbicacion) {
        Session session = Runner.getCurrentSession();

        String hql = "from Campeon i";

        Query<Campeon> query = session.createQuery(hql, Campeon.class);

        Campeon campeonHistorico = query.getSingleResult();

        for(Campeon campeon : query.getResultList()) {
            if(DAYS.between(campeonHistorico.getFechaInicio(), campeonHistorico.getFechaFin()) < DAYS.between(campeon.getFechaInicio(), campeon.getFechaFin())) {
                campeonHistorico = campeon;
            }
        }

        return campeonHistorico;

    }

}
