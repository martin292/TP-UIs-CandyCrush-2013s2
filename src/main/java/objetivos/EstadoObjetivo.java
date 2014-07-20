package objetivos;

import explosiones.Explosion;

public abstract class EstadoObjetivo {
	
	public static final Completo COMPLETO = new Completo();
	public static final Incompleto INCOMPLETO = new Incompleto();
	
	
	// ****************************************************
	// * Metodos
	// ****************************************************
		
	public abstract boolean chequear(ObjetivoParaCumplir objetivo, Explosion explosion);
	public abstract void actualizar(ObjetivoParaCumplir objetivo);
	public abstract boolean estaCompleto();
}
