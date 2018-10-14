package ar.edu.unq.epers.bichomon.backend.dao.impl;

import ar.edu.unq.epers.bichomon.backend.dao.EntrenadorDAO;
import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.service.runner.Runner;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class HibernateEntrenadorDAO implements EntrenadorDAO {

    @Override
    public void guardar(Entrenador entrenador){
        Session session = Runner.getCurrentSession();
        session.save(entrenador);
    }

    @Override
    public Entrenador recuperar(String nombre){
        Session session = Runner.getCurrentSession();
        return session.get(Entrenador.class, nombre);
    }

    @Override
    public void actualizar(Entrenador entrenador) {
        Session session = Runner.getCurrentSession();
        session.update(entrenador);
    }

    public List<Entrenador> recuperarTodos() {
        Session session = Runner.getCurrentSession();

        String hql = "from Entrenador e";

        Query<Entrenador> query = session.createQuery(hql, Entrenador.class);

        return query.getResultList();
    }

    public void agregarBicho(String nombreEntrenador, Bicho bicho) {
        Session session = Runner.getCurrentSession();

        Entrenador entrenador = this.recuperar(nombreEntrenador);
        entrenador.agregarBicho(bicho);
        session.update(entrenador);
    }

}
