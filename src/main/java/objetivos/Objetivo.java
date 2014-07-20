package objetivos;

import java.io.Serializable;

import org.uqbar.commons.model.Entity;
import org.uqbar.commons.utils.Observable;
import org.uqbar.commons.utils.Transactional;

import dulces.Dulce;
import explosiones.Explosion;

@Transactional
@Observable
public abstract class Objetivo extends Entity implements Serializable{
	
	// ****************************************************
	// * Variables
	// ****************************************************
	protected int puntos;

	// ****************************************************
	// * Accessors
	// ****************************************************
	public int getPuntos() {
		return puntos;
	}
	public void setPuntos(int puntos) {
		this.puntos = puntos;
	}
	
	// ****************************************************
	// * Metodos
	// ****************************************************
	public abstract boolean chequearRealizacion(ObjetivoParaCumplir objetivo, Explosion explosion);
	public abstract void agregarCantidad(int cantidad);
	public abstract Dulce getColor();
	public abstract void setDulce(Dulce dulce);
	public abstract int getCantidad();
	public abstract void setCantidad(int cantidad);
	public abstract String getNombre();
	
}