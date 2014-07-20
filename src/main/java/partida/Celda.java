package partida;

import java.io.Serializable;

import org.uqbar.commons.utils.Observable;

import dulces.Dulce;

@Observable
public class Celda implements Serializable{

	// ****************************************************
	// * Variables
	// ****************************************************
	protected Dulce dulce = null;
	public final int FILA;
	public final int COLUMNA;
	
	// ****************************************************
	// * Constructores
	// ****************************************************
	public Celda(int fila, int columna) {
		super();
		this.FILA = fila;
		this.COLUMNA = columna;
	}
	
	// ****************************************************
	// * Metodos
	// ****************************************************
	public boolean estaVacia(){
		return this.getDulce() == null;
	}

	public void destruir(){
		this.setDulce(null);
	}
	
	// ****************************************************
	// * Accessors
	// ****************************************************	
	public Dulce getDulce() {return dulce;}
	public void setDulce(Dulce dulce) {this.dulce = dulce;}
	
}
