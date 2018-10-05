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
    private int factorPoblacion = 0;

    @Override
    public int getFactorPoblacion() {
        return this.factorPoblacion;
    }

    public void sumarPoblacion() {
        this.factorPoblacion += 1;
    }

    public void restarPoblacion() {
        this.factorPoblacion -= 1;
    }

    public void agregarEspecie(Especie especie, int probabilidad) {
        this.especiesQueHabitan.add(new EspeciePosible(especie, siguienteProbabilidadInicial, siguienteProbabilidadInicial + probabilidad - 1));
        siguienteProbabilidadInicial += probabilidad;
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
