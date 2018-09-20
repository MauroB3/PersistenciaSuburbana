package ar.edu.unq.epers.bichomon.backend.model.condicion;

import ar.edu.unq.epers.bichomon.backend.model.bicho.*;

public class BasadoEnNivel extends Condicion {

    private int condicionNivel;

    public BasadoEnNivel (int condicionNivel){
        this.setCondicionNivel(condicionNivel);
    }

    @Override
    public Boolean cumpleConLaCondicion(Bicho bicho) {
        return bicho.getEntrenador().getNivel().nivel() > condicionNivel;
    }

    public int getCondicionNivel(){
        return this.condicionNivel;
    }

    private void setCondicionNivel(int nivel){
        this.condicionNivel = nivel;
    }

}
