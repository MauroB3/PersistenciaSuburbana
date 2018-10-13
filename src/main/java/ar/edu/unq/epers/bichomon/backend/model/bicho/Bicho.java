package ar.edu.unq.epers.bichomon.backend.model.bicho;

import ar.edu.unq.epers.bichomon.backend.model.condicion.*;
import ar.edu.unq.epers.bichomon.backend.model.duelo.Ataque;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import com.sun.org.apache.xpath.internal.operations.Bool;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Random;

/**
 * Un {@link Bicho} existente en el sistema, el mismo tiene un nombre
 * y pertenece a una {@link Especie} en particular.
 * 
 * @author Charly Backend
 */
@Entity
public class Bicho {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable = false, unique = true, length=190)
	private int id;

	@ManyToOne(cascade= CascadeType.ALL, fetch = FetchType.EAGER)
	private Especie especie;
	private int energia;

	@ManyToOne(cascade= CascadeType.ALL, fetch = FetchType.EAGER)
	private Entrenador entrenador;

	private LocalDate fechaDeCaptura;
	private int victorias;
	private boolean estaAbandonado;

	public Bicho(){}

	public Bicho(Especie especie, Entrenador entrenador) {
		this.entrenador = entrenador;
		this.especie = especie;
		this.energia = especie.getEnergiaInicial();
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

	public void serAdoptado(Entrenador entrenador){
	    this.entrenador = entrenador;
    }

    public void evolucionar(){
		this.especie = this.especie.buscarSiguienteEvolucion();
    }

    public boolean puedeEvolucionar(){
		return this.especie.puedeEvolucionar(this);
	}

	public Ataque atacar(Bicho bicho) {
		return new Ataque(bicho.getID(),this.getEnergia() * ((Math.random() * 1) + 0.5f), this.getID());
	}

	public void incrementarEnergia(){
		this.energia += (Math.random() * 5) + 1;
	}

	public int getID() {
		return id;
	}

	public void serAbandonado(){
		this.estaAbandonado = true;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Bicho bicho = (Bicho) o;
		return id == bicho.id;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
