package ar.edu.unq.epers.bichomon.backend.model.duelo;

import java.util.ArrayList;

public class ResultadoCombate {
    private ArrayList<Ataque> ataques;

    public ResultadoCombate(ArrayList<Ataque> ataques){
        this.ataques = ataques;
    }

    public void addAtaque(Ataque ataque){
        this.ataques.add(ataque);
    }

    public float ataqueRecibido(int bicho){
        float daño = 0;
        for(Ataque ataque:this.ataques){
            if(bicho == ataque.atacado()){
                daño += ataque.daño();
            }
        }
        return daño;
    }
}
