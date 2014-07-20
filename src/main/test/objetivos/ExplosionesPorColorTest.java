package objetivos;

import org.junit.Test;

import dulces.Dulce;
import explosiones.Explosion;
import objetivos.ExplosionesPorColor;
import objetivos.ObjetivoParaCumplir;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ExplosionesPorColorTest {

	//Variables
	public ExplosionesPorColor obj;
	
	@Test
	public void testChequearRealizacion(){
		int p = 10;
		Dulce mockDulce = mock(Dulce.class);
		int c = 1;
		
		obj = new ExplosionesPorColor(p, mockDulce, c);
		
		ObjetivoParaCumplir mockObj = mock(ObjetivoParaCumplir.class);
		Explosion mockExp = mock(Explosion.class);
		
		when(mockExp.colorIgualA(mockDulce)).thenReturn(true);
		when(mockObj.getValorPartida()).thenReturn(1);
		
		assertTrue(obj.chequearRealizacion(mockObj, mockExp));
		
	}
		
}
