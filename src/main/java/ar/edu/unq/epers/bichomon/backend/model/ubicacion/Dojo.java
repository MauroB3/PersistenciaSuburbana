package ar.edu.unq.epers.bichomon.backend.model.ubicacion;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import java.time.LocalDate;
import java.util.ArrayList;

@Entity
public class Dojo extends Ubicacion {

    @Transient
    private Campeon campeon;

    public Campeon getCampeon() {
        return this.campeon;
    }

    public void setCampeon(Bicho bicho, LocalDate fechaInicio) {
        if (campeon != null && campeon.getBicho() != bicho) {
            Campeon campeonAnterior = campeon;
            campeonAnterior.setFechaFin(fechaInicio);
            campeon = new Campeon(bicho, fechaInicio, this);
        }
        else {
            campeon = new Campeon(bicho, fechaInicio, this);
        }

    }

    @Override
    public Boolean esDojo() {
        return true;
    }

    @Override
    public Bicho bichoEncontrado(Entrenador entrenador) {
        Especie especie = campeon.getBicho().getEspecie().getEspecieRaiz();
        return new Bicho(especie);
    }

}
