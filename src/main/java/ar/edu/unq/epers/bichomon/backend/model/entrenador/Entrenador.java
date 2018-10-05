package ar.edu.unq.epers.bichomon.backend.model.entrenador;
import ar.edu.unq.epers.bichomon.backend.model.bicho.*;
import ar.edu.unq.epers.bichomon.backend.model.nivel.*;

import java.util.ArrayList;

public class Entrenador {
    /*
    private String nombre;
    private Ubicacion ubicacionActual;
    private int experiencia;
    private Nivel nivel;
    private ArrayList<Bicho> bichosCapturados = new ArrayList<Bicho>();

    public entrenador(){}


    public Bicho buscar(){
        Bicho bicho = ubicacionActual.buscar();
        bichosCapturados.add(bicho);
        return bicho;
        //Modificar en caso de que la busqueda no sea exitosa.
    }


    public void retarADuelo(){
        ubicacionActual.duelo();
        //Completar, hay que contemplar el caso en el que devuelva una exception.
    }

    public Boolean puedeCapturarbicho(){
        return bichosCapturados.size() < this.capacidadMaxima();
    }

    private int capacidadMaxima(){
        return nivel.capacidadMaxima();
    }

*/
    private Nivel nivel;

    public Nivel getNivel(){
        return this.nivel;
    }

    public Entrenador(){

    }

    public int factorTiempo() {
        return 1;
    }

    public int factorNivel() {
        return 1;
    }
}
