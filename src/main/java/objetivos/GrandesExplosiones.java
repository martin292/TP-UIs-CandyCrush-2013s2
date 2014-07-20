package objetivos;

import dulces.Dulce;
import explosiones.Explosion;

public class GrandesExplosiones extends Adicional {
	
	// ****************************************************
	// * Variables
	// ****************************************************
	protected int magnitudExplosion;

	// ****************************************************
	// * Accessors
	// ****************************************************
	public int getMagnitudExplosion() {
		return magnitudExplosion;
	}
	public void setMagnitudExplosion(int magnitudExplosion) {
		this.magnitudExplosion = magnitudExplosion;
	}

	// ****************************************************
	// * Constructores
	// ****************************************************
	public GrandesExplosiones(int puntos, Dulce dulce, int cantidad){
		this.setPuntos(puntos);
		this.setDulce(dulce);
		this.setMagnitudExplosion(cantidad);
	}
	public GrandesExplosiones(Dulce dulce, int cantidad) {
		this.setPuntos(0);
		this.setDulce(dulce);
		this.setMagnitudExplosion(cantidad);
	}
	public GrandesExplosiones() {
	}

	// ****************************************************
	// * Metodos
	// ****************************************************
	
	/**
	 * Chequea si se realizo el objetivo
	 * 
	 * @param ObjetivoParaCumplir obj, Explosion exp
	 */
	public boolean chequearRealizacion(ObjetivoParaCumplir objetivo, Explosion explosion){
		return this.esCompatible(explosion);
	}
	
	/**
	 * Retorna true si la explosion es compatible con el objetivo
	 * @param explosion
	 * @return
	 */
	public boolean esCompatible(Explosion explosion) {
		return this.esMismoColor(explosion) && this.esMismaMagnitud(explosion);
	}	
	
	/**
	 * Retorna true si son de la misma magnitud
	 * @param Explosion explosion
	 * @return boolean
	 */
	private boolean esMismaMagnitud(Explosion explosion) {
		return explosion.mismaMagnitud(this.getMagnitudExplosion());
	}
	
	/**
	 * Retorna true si son del mismo color
	 * @param Explosion explosion
	 * @return boolean
	 */
	private boolean esMismoColor(Explosion explosion){
		return explosion.colorIgualA(this.getDulce());
	}
	@Override
	public void agregarCantidad(int cantidad) {
		this.setMagnitudExplosion(cantidad);		
	}
	////////////////////////////////
	public void setColor(Dulce dulce){
		this.setDulce(dulce);
	}
	public Dulce getColor() {
		return this.getDulce();
	}
	/////////////////////////////////
	@Override
	public int getCantidad() {
		return this.getMagnitudExplosion();
	}
	@Override
	public void setCantidad(int cantidad) {
		this.setMagnitudExplosion(cantidad);		
	}
	
	@Override
	public String toString(){
		return "Realizar explosion de "+this.getMagnitudExplosion()+" de "+this.getDulce();
	}
	@Override
	public String getNombre() {
		return "Gran explosion con  "+this.getCantidad()+" dulces de color "+this.getDulce();
	}
}
