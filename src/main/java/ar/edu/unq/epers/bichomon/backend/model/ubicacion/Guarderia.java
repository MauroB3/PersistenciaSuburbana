package ar.edu.unq.epers.bichomon.backend.model.ubicacion;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import com.sun.org.apache.xpath.internal.operations.Bool;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Guarderia extends Ubicacion {


    @OneToMany(cascade= CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Bicho> bichosAbandonados = new HashSet<>();

    public Set<Bicho> getBichosAbandonados() {
        return this.bichosAbandonados;
    }

    @Override
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
        int numRandom = (int) Math.random() * (bichosAbandonados.size() - 1);
        Bicho bicho = bichosPosibles.get(numRandom);
        bichosAbandonados.remove(bicho);
        return bicho;
    }

    @Override
    public int getCantidadBichosAbandonados() {
        System.out.println("------------- CANTIDAD DE BICHOS ABANDONADOS: " + this.bichosAbandonados.size());
        return this.bichosAbandonados.size();
    }

}
