package ar.edu.unq.epers.bichomon.backend.model.ubicacion;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;

import javax.persistence.Entity;
import java.time.LocalDate;

@Entity
public class Campeon {


    private Bicho bicho;

    private LocalDate fechaInicio;

    private LocalDate fechaFin;

    public Campeon(Bicho unBicho, LocalDate fechaInicial) {
        fechaInicio = fechaInicial;
        bicho = unBicho;
    }

    public Bicho getBicho() {
        return bicho;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFinal) {
        fechaFin = fechaFinal;
    }

}
