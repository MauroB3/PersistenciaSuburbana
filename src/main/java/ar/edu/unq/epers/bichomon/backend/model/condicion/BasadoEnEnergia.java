package ar.edu.unq.epers.bichomon.backend.model.condicion;
import ar.edu.unq.epers.bichomon.backend.model.bicho.*;

public class BasadoEnEnergia extends Condicion {

    private int condicionEnergia;

    public BasadoEnEnergia(int energia) {
        this.setCondicionEnergia(energia);
    }

    public int getEnergia() {
        return this.condicionEnergia;
    }

    private void setCondicionEnergia(int energia) {
        this.condicionEnergia = energia;
    }

    @Override
    public Boolean cumpleConLaCondicion(Bicho bicho) {
        return bicho.getEnergia() > this.condicionEnergia;
    }

}