package ar.edu.unq.epers.bichomon.backend.dao.impl;

import ar.edu.unq.epers.bichomon.backend.dao.EspecieDAO;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import ar.edu.unq.epers.bichomon.backend.service.runner.Runner;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class HibernateEspecieDAO implements EspecieDAO {

    public void guardar(Especie especie){
        Session session = Runner.getCurrentSession();
        session.save(especie);
    }

    public void actualizar(Especie especie){
        Session session = Runner.getCurrentSession();
        session.update(especie);
    }

    public void deleteAll(){
        Session session = Runner.getCurrentSession();

        String hql = "delete from Especie";

        Query<Especie> query = session.createQuery(hql, Especie.class);
    }

    public Especie recuperar(String nombreEspecie){
        Session session = Runner.getCurrentSession();
        return session.get(Especie.class, nombreEspecie);
    }

    public List<Especie> recuperarTodos(){
        Session session = Runner.getCurrentSession();

        String hql = "from Especie";

        Query<Especie> query = session.createQuery(hql, Especie.class);
        return query.getResultList();
    }
}
