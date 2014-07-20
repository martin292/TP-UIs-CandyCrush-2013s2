package objetivos;

import explosiones.Explosion;

public class Incompleto extends EstadoObjetivo {

	// ***************************************
	// * Metodos
	// ***************************************
	
	/**
	 * actualiza el estado del ObjetivoParaCumplir, si se completo con el objetivo
	 * 
	 * @param ObjetivoParaCumplir o, Explosion e
	 * @return boolean
	 */
	public boolean chequear(ObjetivoParaCumplir objetivo, Explosion explosion) {		
		boolean seCompleto = this.seCompleto(objetivo, explosion);
		if(seCompleto){
			this.actualizar(objetivo);
		}
		return seCompleto;
	}
	
	/**
	 * Retorna true si se completo el objetivo
	 * @param ObjetivoParaCumplir o, Explosion e
	 * @return boolean
	 */
	public boolean seCompleto(ObjetivoParaCumplir objetivo, Explosion explosion){
		return objetivo.getObjetivoACumplir().chequearRealizacion(objetivo, explosion);
	}
	
	/**
	 * Instancia un objeto de la clase Completo y se lo asigna al ObjetivoParaCumplir
	 * 
	 * @param ObjetivoParaCumplir obj
	 */
	public void actualizar(ObjetivoParaCumplir objetivo){
		Completo completo = EstadoObjetivo.COMPLETO;
		objetivo.setEstado(completo);
	}

	public boolean estaCompleto(){return false;}
	
	@Override
	public String toString(){
		return "Incompleto";
	}
}
