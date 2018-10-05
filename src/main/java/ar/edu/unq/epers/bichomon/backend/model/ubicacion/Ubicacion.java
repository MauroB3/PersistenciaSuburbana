package ar.edu.unq.epers.bichomon.backend.model.ubicacion;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Ubicacion {

    @Id
    @Column(name = "Nombre", nullable = false, unique = true)
    private String Nombre;

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public Bicho buscar(Entrenador entrenador){
        if (busquedaExitosa(entrenador)) {
            return bichoEncontrado(entrenador);
        }
        else {
            return null;
        }
    }

    private Boolean busquedaExitosa(Entrenador entrenador) {
        return (entrenador.factorTiempo() * entrenador.factorNivel() * getFactorPoblacion() * (double) (Math.random() * 1) > 0.5);
    }

    public int getFactorPoblacion() {
        return 1;
    }

    abstract Bicho bichoEncontrado(Entrenador entrenador);

    public Boolean esDojo() {
        return false;
    }

    public Boolean esGuarderia() {
        return false;
    }

    public Boolean esPueblo() {
        return false;
    }

}
