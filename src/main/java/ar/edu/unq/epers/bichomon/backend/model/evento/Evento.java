package ar.edu.unq.epers.bichomon.backend.model.evento;

import org.jongo.marshall.jackson.oid.MongoId;
import org.jongo.marshall.jackson.oid.MongoObjectId;

import java.time.LocalDate;

public abstract class Evento {

    @MongoId
    @MongoObjectId
    private String id;

    private String entrenador;
    private String tipoEvento;
    private String ubicacion;
    private LocalDate fecha;

    protected Evento() { }

    public Evento(String entrenador, String tipoEvento, String ubicacion) {
        this.entrenador = entrenador;
        this.tipoEvento = tipoEvento;
        this.ubicacion = ubicacion;
    }

    public String getId() {
        return id;
    }

    public String getEntrenador() {
        return entrenador;
    }

    public String getTipo() {
        return tipoEvento;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public String getFecha() {
        return fecha.toString();
    }

}
