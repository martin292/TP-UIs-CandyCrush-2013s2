package dulces;

import org.junit.Test;

import dulces.Dulce;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class DulceTest {

	public Dulce dulce;
	
	@Test
	public void testCompararColor(){
		dulce = new Dulce("Rojo");
		Dulce dulceMock = mock(Dulce.class);
		
		when(dulceMock.getColor()).thenReturn("Rojo");
		
		assertTrue(dulce.compatibleConColor(dulceMock));
		
		verify(dulceMock).getColor();
	}
	
}
