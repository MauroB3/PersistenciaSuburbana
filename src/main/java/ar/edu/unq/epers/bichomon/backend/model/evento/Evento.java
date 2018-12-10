package ar.edu.unq.epers.bichomon.backend.model.evento;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.jongo.marshall.jackson.oid.MongoId;
import org.jongo.marshall.jackson.oid.MongoObjectId;

import javax.persistence.Id;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;

public abstract class Evento {

    @MongoId
    private String id;

    private String entrenador;
    private String tipoEvento;
    private String ubicacion;
    private Date fecha;

    protected Evento() { }

    public Evento(String entrenador, String tipoEvento, String ubicacion) {
        this.entrenador = entrenador;
        this.tipoEvento = tipoEvento;
        this.ubicacion = ubicacion;
        this.fecha = Date.from(Instant.now());
    }

    public String getId() {
        return id;
    }

    public String getEntrenador() {
        return entrenador;
    }

     public String getTipo() { return tipoEvento; }

    public String getUbicacion() {
        return ubicacion;
    }

    public String getFecha() { return fecha.toString(); }

}
