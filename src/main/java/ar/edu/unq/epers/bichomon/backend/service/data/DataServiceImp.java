package ar.edu.unq.epers.bichomon.backend.service.data;

import ar.edu.unq.epers.bichomon.backend.dao.JDBCEspecieDAO;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import ar.edu.unq.epers.bichomon.backend.model.especie.TipoBicho;

import java.util.ArrayList;
import java.util.List;


public class DataServiceImp implements DataService {

    private JDBCEspecieDAO dao;

    public DataServiceImp(JDBCEspecieDAO dao) {
        this.dao = dao;
    }

    @Override
    public void deleteAll() {
        this.dao.deleteAll();
    }

    @Override
    public void crearSetDatosIniciales() {
        TipoBicho bicho1 = TipoBicho.FUEGO;
        TipoBicho bicho2 = TipoBicho.AGUA;
        Especie especie1 = new Especie(1, "Especie 1", bicho1);
        especie1.setPeso(25);
        especie1.setAltura(80);
        especie1.setCantidadBichos(5);
        Especie especie2 = new Especie(2, "Especie 2", bicho2);
        especie2.setPeso(50);
        especie2.setAltura(120);
        List<Especie> list = new ArrayList<>();
        list.add(especie1);
        list.add(especie2);

        for(Especie especie: list) {
            this.dao.guardar(especie);
        }
    }
}

