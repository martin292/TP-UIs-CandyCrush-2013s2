package dificultad;

import java.io.Serializable;
import java.util.*;

import dulces.Dulce;
import org.uqbar.commons.utils.Observable;

@Observable
public class Dificultad implements Serializable {
	
	protected String nombre;
	
	// ****************************************************
	// * Variables de clase
	// ****************************************************
	public static final Dificultad FACIL = 	new Dificultad("Facil", Dulce.getDulces(4));
	public static final Dificultad MEDIO = 	new Dificultad("Medio", Dulce.getDulces(5));
	public static final Dificultad DIFICIL = new Dificultad("Dificil", Dulce.getDulces(6));
	protected static List<Dificultad> dificultades = Arrays.asList(FACIL, MEDIO, DIFICIL);
	
	// ****************************************************
	// * Variables
	// ****************************************************
	protected List<Dulce> dulces;
	
	// ****************************************************
	// * Constructores
	// ****************************************************
	public Dificultad(String nombre, List<Dulce> dulces) {
		super();
		this.nombre = nombre;
		this.dulces = dulces;
	}
	
	public Dificultad() {
	}

	// ****************************************************
	// * Metodos
	// ****************************************************
	/**
	 * @return un Dulce aleatorio
	 */
	public Dulce dulceRandom(){
		List<Dulce> dulces = this.getDulces();
		return dulces.get((int)(Math.random() * dulces.size()));
	}
	
	@Override
	public String toString(){
		return this.getNombre();
	}
	
	// ****************************************************
	// * Accessors
	// ****************************************************	
	public static List<Dificultad> getDificultades(){return dificultades;}
	
	public List<Dificultad> getDificultade(){return dificultades;}
	public List<Dulce> getDulces() {return dulces;}
	public void setDulces(List<Dulce> dulces) {this.dulces = dulces;}

	public String getNombre() {return nombre;}
	public void setNombre(String nombre) {this.nombre = nombre;}
	
}
