package objetivos;

import dulces.Dulce;
import explosiones.Explosion;

public class Regular extends Objetivo {

	// ****************************************************
	// * Variables
	// ****************************************************
	public int puntajeMinimo;
	
	public Dulce col = Dulce.ROJO;
	public int cant = 0;

	// ****************************************************
	// * Constructores
	// ****************************************************
	public Regular(int puntosObjetivo, int puntosAAlcanzar){
		this.setPuntos(puntosObjetivo);
		this.setPuntajeMinimo(puntosAAlcanzar);
	}

	// ****************************************************
	// * Metodos
	// ****************************************************
	public boolean chequearRealizacion(ObjetivoParaCumplir objetivo, Explosion explosion){ 
		objetivo.incrementarValorPartida(explosion.puntos());
		return objetivo.getValorPartida() >= this.getPuntajeMinimo();
	}
	
	@Override
	public void agregarCantidad(int cantidad) {
	}
	@Override
	public Dulce getColor() {
		return this.col;
	}
	@Override
	public int getCantidad() {
		return this.cant;
	}
	
	@Override
	public void setDulce(Dulce dulce) {
		this.col = dulce;
	}

	@Override
	public void setCantidad(int cantidad) {
		this.cant = cantidad;
	}
	
	@Override
	public String toString(){
		return "Alcanzar "+this.getPuntajeMinimo()+" puntos";
	}
	@Override
	public String getNombre() {
		return this.toString();
	}
	
	// ****************************************************
	// * Accessors
	// ****************************************************
	public int getPuntajeMinimo() {
		return puntajeMinimo;
	}
	public void setPuntajeMinimo(int puntajeMinimo) {
		this.puntajeMinimo = puntajeMinimo;
	}
	
}
