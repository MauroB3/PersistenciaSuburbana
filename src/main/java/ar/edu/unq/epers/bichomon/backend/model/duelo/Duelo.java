package ar.edu.unq.epers.bichomon.backend.model.duelo;


import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;

import java.util.ArrayList;

public class Duelo {

    private ResultadoCombate resultado = new ResultadoCombate(new ArrayList<Ataque>());
    private Bicho campeon;

    public void duelo(Bicho bicho){
        //this.campeon = bicho.getEntrenador().ubicacion().campeon();
        int turno = 0;
        while(this.verificarRetadorNoGana(this.campeon, bicho) && this.verificarRetadoNoGana(bicho, this.campeon, turno)){
            this.resultado.addAtaque(this.ejecutarTurno(bicho, turno));
            turno +=1;
        }
    }

    private boolean verificarRetadorNoGana(Bicho retado, Bicho  retador){
        if(this.esVencido(retado)){
            this.finalizarDuelo(retador, retado);
            return false;
        }
        else{
            return true;
        }
    }

    private boolean verificarRetadoNoGana(Bicho retado, Bicho  retador, int turno){
        if(this.esVencido(retador) || turno == 10){
            this.finalizarDuelo(retado, retador);
            return false;
        }
        else{
            return true;
        }
    }

    private boolean esVencido(Bicho unBicho){
        return resultado.ataqueRecibido(unBicho.getID()) > unBicho.getEnergia();
    }



    private void finalizarDuelo(Bicho bicho1 , Bicho bicho2){
        this.coronarCampeon(bicho1);
        bicho2.incrementarEnergia();
        bicho1.incrementarEnergia();
    }

    private void coronarCampeon(Bicho bicho){
        //bicho.getEntrenador().getUbicacion().setCampeon(bicho);
    }

    private Ataque ejecutarTurno(Bicho bicho, int turno){
        if(turno % 2 == 0 ){
            return bicho.atacar(this.campeon);
        }
        else{
            return this.campeon.atacar(bicho);
        }
    }
}