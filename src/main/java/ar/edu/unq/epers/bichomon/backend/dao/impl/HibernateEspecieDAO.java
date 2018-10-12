package ar.edu.unq.epers.bichomon.backend.dao.impl;

import ar.edu.unq.epers.bichomon.backend.dao.EspecieDAO;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import ar.edu.unq.epers.bichomon.backend.service.runner.Runner;
import jdk.internal.util.xml.impl.ReaderUTF16;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.hibernate.transform.Transformers;

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

    public List<Especie> populares(){
        Session session = Runner.getCurrentSession();

        String hql = "SELECT count(distinct e.nombre) FROM Especie e JOIN Bicho b WHERE b.estaAbandonado = False";

        Query<Especie> query = session.createQuery(hql,Especie.class);
        return query.getResultList();
    }

    public Especie especieLider() {
        Session session = Runner.getCurrentSession();

        //String sql = "select nombre, count(nombre) as cantidad from Especie as e join (select b.especie_nombre from Bicho as b join Campeon as c on b.id = c.id_bicho) as r on r.especie_nombre = e.nombre group by e.nombre order by cantidad desc";

        String hql = "select e from Campeon c join c.bicho as b join b.especie as e group by e order by count(b) desc";

        Query<Especie> query = session.createQuery(hql, Especie.class);
        query.setMaxResults(1);
        return query.getSingleResult();

    }
}
