package ar.edu.unq.epers.bichomon.backend.model.evento;

public class Coronacion extends Evento{

    private String nombreEntrenadordescoronado;

    protected Coronacion() {}

    public Coronacion(String nombreEntrenadorCoronado, String nombreEntrenadorDescoronado, String nombreUbicacionOrigen){
        super(nombreEntrenadorCoronado, "Coronacion", nombreUbicacionOrigen);
        this.nombreEntrenadordescoronado = nombreEntrenadorDescoronado;
    }

    public String getNombreEntrenadordescoronado() {
        return this.nombreEntrenadordescoronado;
    }

}
