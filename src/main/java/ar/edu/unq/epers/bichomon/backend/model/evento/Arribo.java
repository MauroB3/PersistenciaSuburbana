package ar.edu.unq.epers.bichomon.backend.model.evento;

public class Arribo extends Evento {

    private String ubicacionDestino;

    protected Arribo() {}

    public Arribo(String entrenador, String ubicacionDestino, String ubicacionOrigen) {
        super(entrenador, "Arribo", ubicacionOrigen);
        this.ubicacionDestino = ubicacionDestino;
    }

    public String getUbicacionDestino() {
        return ubicacionDestino;
    }

}
