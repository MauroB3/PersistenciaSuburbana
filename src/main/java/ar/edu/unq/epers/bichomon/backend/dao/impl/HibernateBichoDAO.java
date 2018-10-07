package ar.edu.unq.epers.bichomon.backend.dao.impl;

import org.hibernate.Session;

import ar.edu.unq.epers.bichomon.backend.dao.BichoDAO;
import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.service.runner.Runner;

public class HibernateBichoDAO implements BichoDAO {

    @Override
    public void guardar(Bicho bicho){
        Session session = Runner.getCurrentSession();
        session.save(bicho);
    }

    @Override
    public Bicho recuperar(int id){
        Session session = Runner.getCurrentSession();
        return session.get(Bicho.class,id);
    }

}
