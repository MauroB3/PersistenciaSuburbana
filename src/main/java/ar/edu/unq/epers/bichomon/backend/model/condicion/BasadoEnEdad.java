package ar.edu.unq.epers.bichomon.backend.model.condicion;
import ar.edu.unq.epers.bichomon.backend.model.bicho.*;

import java.time.LocalDate;

public class BasadoEnEdad extends Condicion{

    private LocalDate condicionFecha;

    public BasadoEnEdad(LocalDate condicionFecha){
        this.setCondicionEdad(condicionFecha);
    }

    @Override
    public Boolean cumpleConLaCondicion(Bicho bicho){
        return bicho.getFechaDeCaptura().isAfter(condicionFecha);
    }

    private void setCondicionEdad(LocalDate condicionFecha){
        this.condicionFecha = condicionFecha;
    }

    public LocalDate getCondicionFecha(){
        return this.condicionFecha;
    }
}
