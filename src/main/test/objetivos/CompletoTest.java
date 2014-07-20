package objetivos;

import org.junit.Test;

import objetivos.Completo;
import static org.junit.Assert.*;

public class CompletoTest {

	//Variables
	public Completo estado = new Completo();
	
	@Test
	public void testEstaCompleto(){
		assertTrue(estado.estaCompleto());
	}
}
