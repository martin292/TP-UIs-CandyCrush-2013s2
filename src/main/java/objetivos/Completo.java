package objetivos;

import explosiones.Explosion;

public class Completo extends EstadoObjetivo {

	// ***************************************
		// * Metodos
		// ***************************************
		public boolean chequear(ObjetivoParaCumplir objetivo, Explosion explosion) {return false;}
				//Objetivo completo. Nada que chequear
		public void actualizar(ObjetivoParaCumplir objetivo){}//Los objetivos completos no se actualizan
		public boolean estaCompleto(){return true;}
		
		@Override
		public String toString(){
			return "Completado";
		}
}
