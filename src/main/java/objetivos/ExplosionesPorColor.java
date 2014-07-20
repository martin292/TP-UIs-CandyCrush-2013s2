package objetivos;

import dulces.Dulce;
import explosiones.Explosion;

public class ExplosionesPorColor extends Adicional {
	
	// ****************************************************
	// * Variables
	// ****************************************************
	protected int cantidadExplosion;

	// ****************************************************
	// * Accessors
	// ****************************************************
	public int getCantidadExplosion() {
		return cantidadExplosion;
	}
	public void setCantidadExplosion(int cantidadExplosion) {
		this.cantidadExplosion = cantidadExplosion;
	}

	// ****************************************************
	// * Constructores
	// ****************************************************
	public ExplosionesPorColor(int puntos, Dulce dulce, int cantidad){
		this.setPuntos(puntos);
		this.setDulce(dulce);
		this.setCantidadExplosion(cantidad);
	}
	public ExplosionesPorColor(Dulce dulce, int cantidad) {
		super();
		this.setDulce(dulce);
		this.setCantidad(cantidad);
	}
	public ExplosionesPorColor() {
		super();
	}

	// ****************************************************
	// * Metodos
	// ****************************************************
	/**
	 * 
	 * @param ObjetivoParaCumplir obj, Explosion exp
	 * @return boolean
	 */
	public boolean chequearRealizacion(ObjetivoParaCumplir objetivo, Explosion explosion){
		boolean realizado = false;
		if(this.esMismoColor(explosion)){
			objetivo.incrementarValorPartida();
			realizado = objetivo.getValorPartida() >= this.getCantidadExplosion();
		}
		return realizado;
	}

	/**
	 * Retorna true si son del mismo color 
	 * 
	 * @param Explosion explosion
	 * @return boolean
	 */
	private boolean esMismoColor(Explosion explosion){
		return explosion.colorIgualA(this.getDulce());
	}
	@Override
	public void agregarCantidad(int cantidad) {
		this.setCantidadExplosion(cantidad);		
	}
	public void setColor(Dulce dulce){
		this.setDulce(dulce);
	}
	@Override
	public Dulce getColor() {
		return this.getDulce();
	}
	@Override
	public int getCantidad() {
		return this.getCantidadExplosion();
	}
		@Override
	public void setCantidad(int cantidad) {
		this.setCantidadExplosion(cantidad);
	}
	@Override
	public String toString(){
		return "Realizar "+this.getCantidadExplosion()+" explosiones de "+this.getDulce();
	}
	@Override
	public String getNombre() {
		return "Realizar "+this.getCantidad()+" explosiones del color "+this.getDulce();
	}
	
}
