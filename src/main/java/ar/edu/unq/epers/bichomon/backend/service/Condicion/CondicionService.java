package ar.edu.unq.epers.bichomon.backend.service.Condicion;

import ar.edu.unq.epers.bichomon.backend.model.condicion.Condicion;

public interface CondicionService {

    void crearCondicion(Condicion cond);

    Condicion getCondicion(int idCond);

}
