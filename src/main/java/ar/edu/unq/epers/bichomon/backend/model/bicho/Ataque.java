package ar.edu.unq.epers.bichomon.backend.model.bicho;

public class Ataque {

    private Bicho atacante;
    private int daño;
    private Bicho atacado;

    public Ataque(Bicho atacado, int daño, Bicho atacante){
        this.setAtacado(atacado);
        this.setDaño(daño);
        this.setAtacante(atacante);
    }

    public void setAtacado(Bicho bicho){
        this.atacado = bicho;
    }

    public void setAtacante(Bicho bicho){
        this.atacante = bicho;
    }

    public void setDaño(int daño){
        this.daño = daño;
    }

    public Bicho getAtacante(){
        return atacante;
    }

    public Bicho getAtacado(){
        return atacado;
    }

    public int getDaño(){
        return daño;
    }

}
