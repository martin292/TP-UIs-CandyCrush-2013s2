package objetivos;

import org.junit.Test;

import objetivos.ExplosionesPorColor;
import objetivos.ObjetivoParaCumplir;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ObjetivoParaCumplirTest {

	//Variables
	public ObjetivoParaCumplir obj;
	
	@Test
	public void testIncrementarValorPartida(){
		ExplosionesPorColor mockObj = mock(ExplosionesPorColor.class);
		
		obj = new ObjetivoParaCumplir(mockObj);
		
		int valorEsperado = 1;
		
		obj.incrementarValorPartida();
		
		assertEquals(valorEsperado, obj.getValorPartida());
	}
	
}
