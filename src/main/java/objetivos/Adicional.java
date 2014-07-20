package objetivos;

import dulces.Dulce;

public abstract class Adicional extends Objetivo {
	
	// ****************************************************
	// * Variables
	// ****************************************************
	protected Dulce dulce;

	// ****************************************************
	// * Accessors
	// ****************************************************
	public Dulce getDulce() {
		return dulce;
	}
	public void setDulce(Dulce dulce) {
		this.dulce = dulce;
	}
	
}
