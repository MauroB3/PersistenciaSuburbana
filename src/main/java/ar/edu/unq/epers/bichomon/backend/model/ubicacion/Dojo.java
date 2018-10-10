package ar.edu.unq.epers.bichomon.backend.model.ubicacion;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;

@Entity
public class Dojo extends Ubicacion {

    @OneToOne(cascade= CascadeType.ALL, fetch = FetchType.EAGER)
    private Campeon campeon;

    public Campeon getCampeon() {
        return this.campeon;
    }

    public void setCampeon(Bicho bicho, LocalDate fechaInicio) {
        if (campeon != null && campeon.getBicho() != bicho) {
            campeon.setFechaFin(fechaInicio);
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
