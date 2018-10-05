package ar.edu.unq.epers.bichomon.backend.model.ubicacion;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import com.sun.org.apache.xpath.internal.operations.Bool;

import javax.persistence.OneToMany;
import java.util.ArrayList;

public class Guarderia extends Ubicacion {

    @OneToMany
    private ArrayList<Bicho> bichosAbandonados = new ArrayList<>();

    public ArrayList<Bicho> getBichosAbandonados() {
        return this.bichosAbandonados;
    }

    public void abandonarBicho(Bicho unBicho) {
        bichosAbandonados.add(unBicho);
    }

    private ArrayList<Bicho> getBichosPosiblesPara(Entrenador entrenador) {
        ArrayList<Bicho> bichosPosibles = new ArrayList<>();
        for(Bicho bicho : bichosAbandonados) {
            if(bicho.getEntrenador() != entrenador) {
                bichosPosibles.add(bicho);
            }
        }
        return bichosPosibles;
    }

    @Override
    public Boolean esGuarderia() {
        return true;
    }

    @Override
    public Bicho bichoEncontrado(Entrenador entrenador) {
        ArrayList<Bicho> bichosPosibles = getBichosPosiblesPara(entrenador);
        Bicho bicho = bichosPosibles.get((int) Math.random() * (bichosAbandonados.size() - 1));
        bichosAbandonados.remove(bicho);
        return bicho;
    }

}
