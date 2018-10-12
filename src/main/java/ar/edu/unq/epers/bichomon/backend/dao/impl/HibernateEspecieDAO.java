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
        int result = query.executeUpdate();
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

    public List<Especie> populares(){
        Session session = Runner.getCurrentSession();
        
        String hql = "from Especie e order by e.popularidad desc";

        Query<Especie> query = session.createQuery(hql,Especie.class);
        return query.setMaxResults(10).getResultList();
    }

    public List<Especie> impopulares(){
        Session session = Runner.getCurrentSession();

        String hql = "from Especie e order by e.popularidad asc";

        Query<Especie> query = session.createQuery(hql,Especie.class);
        return query.setMaxResults(10).getResultList();
    }

    public void incrementarPopularidad(String nombreEspecie){
        Session session = Runner.getCurrentSession();

        String hql = "update Especie e set e.popularidad = (e.popularidad +1) where e.nombre = " + nombreEspecie;

        Query<Especie> query = session.createQuery(hql, Especie.class);
        query.executeUpdate();
    }

    public void decrementarPopularidad(String nombreEspecie){
        Session session = Runner.getCurrentSession();

        String hql = "update Especie e set e.popularidad = (e.popularidad -1) where e.nombre = " + nombreEspecie;

        Query<Especie> query = session.createQuery(hql,Especie.class);
        int retult = query.executeUpdate();
    }

}
