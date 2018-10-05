package ar.edu.unq.epers.bichomon.backend.model.ubicacion;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;

import javax.persistence.ManyToOne;
import java.time.LocalDate;
import java.util.ArrayList;

public class Dojo extends Ubicacion {

    @ManyToOne
    private Campeon campeon;

    private ArrayList<Campeon> campeones = new ArrayList();

    public Bicho getCampeon() {
        return this.campeon.getBicho();
    }

    public void setCampeon(Bicho bicho) {
        if (campeon != null && campeon.getBicho() != bicho) {
            Campeon campeonAnterior = campeon;
            campeonAnterior.setFechaFin(LocalDate.now());
            campeones.add(campeon);
            campeon = new Campeon(bicho, LocalDate.now());
        }
        else {
            campeon = new Campeon(bicho, LocalDate.now());
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
