package ar.edu.unq.epers.bichomon.backend.model.entrenador;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.nivel.NivelManager;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Ubicacion;
import org.mockito.cglib.core.Local;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import static java.time.temporal.ChronoUnit.DAYS;

@Entity
public class Entrenador {

    private String nombre;
    @OneToOne
    private Ubicacion ubicacion;
    private int exp = 0;
    @Transient
    private NivelManager nivel;
    private LocalDate ulimaCaptura;
    @Transient
    private List<Bicho> bichos = new ArrayList<Bicho>();

    public Entrenador(){

    }

    public Entrenador(String nombre, NivelManager nivel, Ubicacion ubicacion){
        this.nombre = nombre;
        this.nivel = nivel;
        this.ubicacion = ubicacion;
    }

    public String nombre(){
        return this.nombre;
    }

    public Ubicacion ubicacion(){
        return this.ubicacion;
    }

    public boolean puedeCapturarBicho(){return this.capacidadMaxima() > bichos.size();}

    /** ¿Debe ser de un servicio?*/
    public void retarADuelo(){}

    public int capacidadMaxima(){ return nivel.capacidadMaximaDeBichos(this.exp);} //<-------------

    public float factorNivel(){
        return this.nivel.factorNivel() / this.getNivel();
    }

    public float factorTiempo(){
        if(this.diasDesdeUltimaCaptura() == 0){
            return 1;
        }else{
            return this.diasDesdeUltimaCaptura();
        }
    }

    private long diasDesdeUltimaCaptura(){
        return DAYS.between(this.ulimaCaptura, LocalDate.now());
    }

    public int getNivel(){return nivel.getNivel(this.exp);}

    public void agregarExperiencia(int exp){
        this.exp += exp;
    }

    public void capturarBicho(Bicho bicho){
        if(this.puedeCapturarBicho()){
            bichos.add(bicho);
            ulimaCaptura = LocalDate.now();
        }
    }

    /** Para testear */
    public int cantidadBichos(){
        return bichos.size();
    }

    public int getExperiencia(){
        return exp;
    }

    public void setUlimaCaptura(LocalDate fecha){
        this.ulimaCaptura = fecha;
    }

}
