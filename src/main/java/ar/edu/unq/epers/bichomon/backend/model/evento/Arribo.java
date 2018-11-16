package ar.edu.unq.epers.bichomon.backend.model.evento;

public class Arribo extends Evento {

    private String ubicacionOrigen;

    protected Arribo() {}

    public Arribo(String entrenador, String ubicacionDestino, String ubicacionOrigen) {
        super(entrenador, "Arribo", ubicacionDestino);
        this.ubicacionOrigen = ubicacionOrigen;
    }

    public String getUbicacionOrigen() {
        return ubicacionOrigen;
    }

}
