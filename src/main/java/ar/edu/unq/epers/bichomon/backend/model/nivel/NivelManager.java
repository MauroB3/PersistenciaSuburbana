package ar.edu.unq.epers.bichomon.backend.model.entrenador;

import java.util.List;

public class NivelManager {

    private List<Nivel> niveles;
    private int factorNivel;

    public NivelManager(List<Nivel> niveles, int factorNivel){
        this.factorNivel = factorNivel;
        this.niveles = niveles;
    }

    private boolean between(int valorMinimo, int valorAVerificar, int valorMaximo){
        if(valorMinimo <= valorAVerificar && valorMaximo >= valorAVerificar){
            return true;
        }
        else {
            return false;
        }
    }

    public int factorNivel(){
        return this.factorNivel;
    }

    public int getNivel(int experiencia){
        int nivel = 0;
    private NivelManager(){

    }

        for(Nivel level : niveles) {
            if (this.between(level.getExpInicial(), experiencia, level.getExpFinal())){
                nivel = level.getNivel();
            }
        }

        return nivel;
    }

    public int capacidadMaximaDeBichos(int experiencia){
        int capacidad = 0;

        for(Nivel nivel : niveles) {
            if (this.between(nivel.getExpInicial(), experiencia, nivel.getExpFinal())){
                capacidad = nivel.getCapacidadMaximaDeBichos();
            }
        }

        return capacidad;
    }

    public void agregarNivel(Nivel nivel){
        niveles.add(nivel);
    }

    public void modificarNivel(int nivel, int expInicial, int expFinal, int cantBichos){
        for(Nivel level : niveles) {
            if (level.getNivel() == nivel){
                level.setCapacidadMaximaDeBichos(cantBichos);
                level.setExpInicial(expInicial);
                level.setExpFinal(expFinal);
            }
        }
    }
}