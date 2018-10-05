package ar.edu.unq.epers.bichomon.backend.model.ubicacion;

import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;

public class EspeciePosible {

    private Especie especie;
    private int probInicial;
    private int probFinal;

    public EspeciePosible(Especie especie, int probInicial, int probFinal) {
        especie = especie;
        probInicial = probInicial;
        probFinal = probFinal;
    }

    public Especie getEspecie() {
        return especie;
    }

    public int getProbInicial() {
        return probInicial;
    }

    public int getProbFinal() {
        return probFinal;
    }


}
