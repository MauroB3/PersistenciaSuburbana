package ar.edu.unq.epers.bichomon.backend.model.ubicacion;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;

import javax.persistence.Column;
import java.util.ArrayList;
import java.util.Random;


public class Pueblo extends Ubicacion {

    @Column
    private ArrayList<EspeciePosible> especiesQueHabitan = new ArrayList<>();

    @Column
    private int siguienteProbabilidadInicial = 0;

    @Column
    private int poblacion = 0;

    private int dividendoFactorPoblacion = 100;

    @Override
    public int getFactorPoblacion() {
        return (dividendoFactorPoblacion / poblacion);
    }

    public void setDividendoFactorPoblacion(int dividendoFactorPoblacion) {
        this.dividendoFactorPoblacion = dividendoFactorPoblacion;
    }

    public void sumarPoblacion() {
        poblacion += 1;
    }

    public void restarPoblacion() {
        poblacion -= 1;
    }

    public int getPoblacion() { return poblacion; }

    //No se puede agregar una especie con probabilidad 0
    public void agregarEspecie(Especie especie, int probabilidad) {
        especiesQueHabitan.add(new EspeciePosible(especie, siguienteProbabilidadInicial, siguienteProbabilidadInicial + probabilidad - 1));
        siguienteProbabilidadInicial += probabilidad;
    }

    public void eliminarEspecie(Especie especie) {
        for(EspeciePosible esp : especiesQueHabitan) {
            if(esp.getEspecie() == especie) {
                especiesQueHabitan.remove(esp);
            }
        }
    }

    public ArrayList<EspeciePosible> getEspeciesQueHabitan() {
        return especiesQueHabitan;
    }

    @Override
    public Boolean esPueblo() {
        return true;
    }

    @Override
    public Bicho bichoEncontrado(Entrenador entrenador) {
        int resultado = (int) (Math.random() * siguienteProbabilidadInicial);

        Especie especieResultado = null;
        for (EspeciePosible especie : especiesQueHabitan) {
            if(resultado > especie.getProbInicial() && resultado < especie.getProbFinal()) {
                especieResultado = especie.getEspecie();
            }
        }

        return new Bicho(especieResultado);


    }

}
