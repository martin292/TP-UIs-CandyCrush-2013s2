package objetivos;

import org.junit.Test;

import objetivos.Incompleto;
import static org.junit.Assert.*;


public class IncompletoTest {

	//Variables
	public Incompleto estado = new Incompleto();
	
	@Test
	public void testEstaCompleto(){
		assertFalse(estado.estaCompleto());
	}
	
}
