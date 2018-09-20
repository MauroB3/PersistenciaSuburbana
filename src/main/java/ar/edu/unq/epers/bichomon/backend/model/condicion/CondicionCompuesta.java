package ar.edu.unq.epers.bichomon.backend.model.condicion;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;

import java.util.ArrayList;

public class CondicionCompuesta extends Condicion {

    private ArrayList<Condicion> condiciones = new ArrayList<Condicion>();

    public void agregarCondicion(Condicion c){
        condiciones.add(c);
    }

    public void removerCondicion(Condicion c){
        condiciones.remove(c);
    }

    public int cantidadDeCondiciones(){
        return condiciones.size();
    }

    public Boolean cumpleConLaCondicion(Bicho bicho){
        Boolean cumple = true;
        for(Condicion cond : condiciones){
            cumple = cumple && cond.cumpleConLaCondicion(bicho);
        }
        return cumple;
    }

}
