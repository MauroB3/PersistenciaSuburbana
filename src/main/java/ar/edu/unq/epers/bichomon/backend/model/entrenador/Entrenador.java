package ar.edu.unq.epers.bichomon.backend.model.entrenador;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.nivel.NivelManager;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Ubicacion;

import javax.persistence.*;
import static java.time.temporal.ChronoUnit.DAYS;

@Entity
public class Entrenador {

    @Id
    @Column(name = "Nombre", nullable = false, unique = true, length=190)
    private String Nombre;

    @OneToOne(cascade = CascadeType.ALL)
    private Ubicacion ubicacion;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Entrenador that = (Entrenador) o;
        return Objects.equals(Nombre, that.Nombre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Nombre);
    }

    private int exp = 0; //Siempr empieza en 0 de experiencia

    private LocalDate ulimaCaptura;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Bicho> bichos = new ArrayList<>();

    public Entrenador(){

    }

    public Entrenador(String nombre, Ubicacion ubicacion){
        this.Nombre = nombre;
        this.ubicacion = ubicacion;
        ubicacion.sumarPoblacion();
    }

    public String nombre(){
        return this.Nombre;
    }

    public Ubicacion ubicacion(){
        return this.ubicacion;
    }

    public void mover(Ubicacion ubicacion) { this.ubicacion = ubicacion; }

    public boolean puedeCapturarBicho(NivelManager nivelManager) {
        return this.capacidadMaxima(nivelManager) > bichos.size();
    }

    public boolean puedeAbandonarBicho() { return this.bichos.size() > 1; }

    /** ¿Debe ser de un servicio?*/
    public void retarADuelo(){}

    public int capacidadMaxima(NivelManager nivelManager) {
        System.out.println("-------------- ACA ESTA: " + nivelManager.capacidadMaximaDeBichos(this.exp));
        return nivelManager.capacidadMaximaDeBichos(this.exp);
    }

    public float factorNivel(NivelManager nivelManager){
        return nivelManager.factorNivel() / this.getNivel(nivelManager);
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

    public int getNivel(NivelManager nivelManager){return nivelManager.getNivel(this.exp);}

    public void agregarExperiencia(int exp){
        this.exp += exp;
    }

    public void capturarBicho(Bicho bicho){
        bichos.add(bicho);
        ulimaCaptura = LocalDate.now();
    }

    /** Para testear */
    public int cantidadBichos(){
        return bichos.size();
    }

    public void agregarBicho(Bicho bicho) {
        this.bichos.add(bicho);
    }

    public int getExperiencia(){
        return exp;
    }

    public void setUlimaCaptura(LocalDate fecha){
        this.ulimaCaptura = fecha;
    }

    public int getPoderTotal() {
        int poderTotal = 0;
        for(Bicho bicho : bichos) {
            poderTotal += bicho.getEnergia();
        }
        return poderTotal;
    }

    public void abandonarBicho(Bicho bicho) {
        this.bichos.remove(bicho);
    }

}