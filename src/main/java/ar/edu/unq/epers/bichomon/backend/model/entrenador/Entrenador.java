package ar.edu.unq.epers.bichomon.backend.model.entrenador;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.nivel.NivelManager;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Ubicacion;

import javax.persistence.Entity;

@Entity
public class Entrenador {

    private String nombre;
    private Ubicacion ubicacion;
    private int exp;
    private NivelManager nivel;
    private LocalDate ulimaCaptura;
    private List<Bicho> bichos;

    public Entrenador(String nombre, int exp, NivelManager nivel, Ubicacion ubicacion){
        this.nombre = nombre;
        this.exp = exp;
        this.nivel = nivel;
        this.ubicacion = ubicacion;
        this.bichos = new ArrayList<Bicho>();
    }

    public String nombre(){
        return this.nombre;
    }

    public Ubicacion ubicacion(){
        return this.ubicacion;
    }

    public boolean puedeCapturarBicho(){return this.capacidadMaxima() < bichos.size();}

    public void retarADuelo(){}

    public int capacidadMaxima(){ return nivel.capacidadMaximaDeBichos(this.exp);}

    public float factorNivel(){
        return this.nivel.factorNivel() / this.getNivel();
    }

    /** TERMINAR */
    public float factorTiempo(){ return 0;}

    public int getNivel(){return nivel.getNivel(this.exp);}

}
