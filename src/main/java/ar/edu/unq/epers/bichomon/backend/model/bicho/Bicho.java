package ar.edu.unq.epers.bichomon.backend.model.bicho;

import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;

import javax.persistence.Id;
import java.time.LocalDate;
import java.util.Random;

/**
 * Un {@link Bicho} existente en el sistema, el mismo tiene un nombre
 * y pertenece a una {@link Especie} en particular.
 * 
 * @author Charly Backend
 */
public class Bicho {

    //Ya no tiene nombre "por simplicidad"
	@Id
	private int id; //Agregado porque si

	private Especie especie;
	private int energia;
	private Entrenador entrenador;
	private LocalDate fechaDeCaptura;
	private int victorias;

	public Bicho(){}

	public Bicho(Especie especie) {
		this.especie = especie;
	}

	/**
	 * @return la especie a la que este bicho pertenece.
	 */
	public Especie getEspecie() {
		return this.especie;
	}
	
	/**
	 * @return la cantidad de puntos de energia de este bicho en
	 * particular. Dicha cantidad crecerá (o decrecerá) conforme
	 * a este bicho participe en combates contra otros bichomones.
	 */
	public int getEnergia() {
		return this.energia;
	}

	public void setEnergia(int energia) {
		this.energia = energia;
	}

	public LocalDate getFechaDeCaptura(){
		return fechaDeCaptura;
	}

	public void setFechaDeCaptura(LocalDate fecha){
		this.fechaDeCaptura = fecha;
	}

	public int getVictorias(){
		return victorias;
	}

	public void incrementarVictorias(){
	    victorias++;
    }

	public Entrenador getEntrenador(){
		return entrenador;
	}

	public void setEntrenador(Entrenador entrenador){
	    this.entrenador = entrenador;
    }

    public void evolucionar(){
		this.especie = this.especie.buscarSiguienteEvolucion();
    }

    public boolean puedeEvolucionar(){
		return this.especie.puedeEvolucionar(this);
	}

	public Ataque atacar(Bicho bicho) {
		return new Ataque(bicho, this.energia * Math.random() * 1,this);
	}
}
