package ar.edu.unq.epers.bichomon.backend.dao.impl;

import ar.edu.unq.epers.bichomon.backend.dao.CampeonDAO;
import ar.edu.unq.epers.bichomon.backend.model.campeon.Campeon;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.*;
import ar.edu.unq.epers.bichomon.backend.service.runner.Runner;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;
import static java.time.temporal.ChronoUnit.DAYS;

public class HibernateCampeonDAO implements CampeonDAO {


    public Campeon getCampeon(String nombreDojo) {
        Session session = Runner.getCurrentSession();

        return session.get(Dojo.class, nombreDojo).getCampeon();
    }

    public Campeon getCampeonHistorico(String nombreUbicacion) {
        Session session = Runner.getCurrentSession();

        String hql = "from Campeon i";

        Query<Campeon> query = session.createQuery(hql, Campeon.class);

        List<Campeon> campeones = query.getResultList();
        Campeon campeonHistorico = campeones.get(0);

        for(Campeon campeon : campeones) {
            if(campeon.getFechaFin() != null
                    && DAYS.between(campeonHistorico.getFechaInicio(), campeonHistorico.getFechaFin()) < DAYS.between(campeon.getFechaInicio(), campeon.getFechaFin())) {
                campeonHistorico = campeon;
            }
        }

        return campeonHistorico;

    }

    public void actualizarOGuardarCampeon(Campeon campeon) {
        Session session = Runner.getCurrentSession();
        session.saveOrUpdate(campeon);
    }

}
