package ar.edu.unq.epers.bichomon.backend.model.especie;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.condicion.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

/**
 * Representa una {@link Especie} de bicho.
 * 
 * @author Charly Backend
 */
@Entity
public class Especie {

	@Id
	private Integer id;
	private String nombre;
	private int altura;
	private int peso;
	private TipoBicho tipo;

	private Especie especieRaiz;

	private int nroEvolucion;

	private int energiaInicial; //Es necesaria ?

	private String urlFoto;

	private int cantidadBichos;

	@Transient
	private Condicion condicionDeEvolucion;
	
	public Especie(){
	}

	/**
	 * Este constructor se usa para crear una especie raiz
	 * ---------------------------------------------------
	 * @param nombre = nombre de la especie
	 * @param tipo = tipo de la especie (ver enumerador "TipoBicho")
	 * @param altura = altura de todos los bichos de esa especie
	 * @param peso = peso de todos los bichos de esa especie
	 * @param energiaInicial = energia con la que inician todos los bichos de esa especie
	 */

	public Especie(int id, String nombre, TipoBicho tipo, Condicion condicionDeEvolucion, int altura, int peso, int energiaInicial){
		this.setEspecieRaiz(this);
		this.setId(id);
		this.setNombre(nombre);
		this.setTipo(tipo);
		this.setCondicionDeEvolucion(condicionDeEvolucion);
		this.setNroEvolucion(1);
		this.setAltura(altura);
		this.setPeso(peso);
		this.setEnergiaIncial(energiaInicial);
		EvolutionHandler.getInstance().agregarEspecie(this);
	}

	/**
	 * Este constructor se usa para crear una evolucion una especie
	 * ------------------------------------------------------------
	 * @param especie = la especie raiz de la especie a crear
	 * @param nroEvolucion = numero de evolucion de la especie [1..n] siendo 1 el mas debil y n el mas fuerte
	 * @param nombre = nombre de la especie
	 * @param altura = altura de todos los bichos de esa especie
	 * @param peso = peso de todos los bichos de esa especie
	 * @param energiaInicial = energia con la que inician todos los bichos de esa especie
	 */

	public Especie(Especie especie, int nroEvolucion,Condicion condicionDeEvolucion,int id, String nombre, int altura, int peso, int energiaInicial) {
		this.setEspecieRaiz(especie);
		this.setNroEvolucion(nroEvolucion);
		this.setCondicionDeEvolucion(condicionDeEvolucion);
		this.setId(id);
		this.setNombre(nombre);
		this.setTipo(especie.getTipo());
		this.setAltura(altura);
		this.setPeso(peso);
		this.setEnergiaIncial(energiaInicial);
		EvolutionHandler.getInstance().agregarEspecie(this);
	}


	public Condicion getCondicionDeEvolucion(){
		return condicionDeEvolucion;
	}

	public void setCondicionDeEvolucion(Condicion condicionDeEvolucion) {
		this.condicionDeEvolucion = condicionDeEvolucion;
	}

	/**
	 * @return el nombre de la especie (por ejemplo: Perromon)
	 */
	public String getNombre() {
		return this.nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	/**
	 * @return la altura de todos los bichos de esta especie
	 */
	public int getAltura() {
		return this.altura;
	}
	public void setAltura(int altura) {
		this.altura = altura;
	}
	
	/**
	 * @return el peso de todos los bichos de esta especie
	 */
	public int getPeso() {
		return this.peso;
	}
	public void setPeso(int peso) {
		this.peso = peso;
	}
	
	/**
	 * @return una url que apunta al un recurso imagen el cual será
	 * utilizado para mostrar un thumbnail del bichomon por el frontend.
	 */
	public String getUrlFoto() {
		return this.urlFoto;
	}
	public void setUrlFoto(String urlFoto) {
		this.urlFoto = urlFoto;
	}
	
	/**
	 * @return la cantidad de energia de poder iniciales para los bichos
	 * de esta especie.
	 */
	public int getEnergiaInicial() {
		return this.energiaInicial;
	}
	public void setEnergiaIncial(int energiaInicial) {
		this.energiaInicial = energiaInicial;
	}

	/**
	 * @return el tipo de todos los bichos de esta especie
	 */
	public TipoBicho getTipo() {
		return this.tipo;
	}
	public void setTipo(TipoBicho tipo) {
		this.tipo = tipo;
	}
	
	/**
	 * @return la cantidad de bichos que se han creado para esta
	 * especie.
	 */
	public int getCantidadBichos() {
		return this.cantidadBichos;
	}
	public void setCantidadBichos(int i) {
		this.cantidadBichos = i;
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	public Bicho crearBicho(){
		this.cantidadBichos++;
		return new Bicho(this);
	}

	public Especie getEspecieRaiz(){
			return especieRaiz;
	}

	public void setEspecieRaiz(Especie especie){
		this.especieRaiz = especie;
	}

	public int getNroEvolucion(){
		return nroEvolucion;
	}

	public void setNroEvolucion(int nroEvolucion){
		this.nroEvolucion = nroEvolucion;
	}

	public boolean puedeEvolucionar(Bicho bicho){
		return this.condicionDeEvolucion.cumpleConLaCondicion(bicho);
	}

	public Especie buscarSiguienteEvolucion(){
		return EvolutionHandler.getInstance().buscarSiguienteEvolucion(this);
	}

	public boolean esSiguienteEvolucion(Especie especie) {
		return this.especieRaiz == especie.getEspecieRaiz() && this.nroEvolucion == especie.getNroEvolucion() + 1;
	}
}
