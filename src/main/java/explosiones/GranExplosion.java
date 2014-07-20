package explosiones;

import dulces.Dulce;

public class GranExplosion extends Explosion {
		
	// ****************************************************
	// * Variables
	// ****************************************************
	protected int magnitud;
		
	// ****************************************************
	// * Constructores
	// ****************************************************
	public GranExplosion(Dulce d, int m){
		super(d);
		this.setMagnitud(m);
	}
	public GranExplosion(Dulce d, int v, int m){
		super(d, v);
		this.setMagnitud(m);
	}

	// ****************************************************
	// * Accessors
	// ****************************************************
	public void setMagnitud(int m){
		this.magnitud = m;
	}
	public int getMagnitud(){
		return this.magnitud;
	}
		
	// ****************************************************
	// * Metodos
	// ****************************************************
	
	/**
	 * Retorna los puntos de la explosion
	 * Puntos = (valor de la explocion) * (magnitud de la explocion)
	 * 
	 * @return int
	 */
	public int puntos(){
		return (this.getValor() * this.getMagnitud());
	}
	
	/**
	 * Retorna true si la magnitud pasada por parametro es igual a la magnitud de la explosion
	 * la magnitud de una GranExplosion es siempre mayor a 3
	 * 
	 * @param int magnitud
	 * @return boolean 
	 */
	public boolean mismaMagnitud(int magnitud) {
		return magnitud >= this.getMagnitud();
	}
	
	@Override
	public String toString(){
		return "Gran Explosion " + this.getDulce() + "!";
	}
	
}
