package explosiones;

import org.junit.Test;

import dulces.Dulce;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import explosiones.GranExplosion;

public class GranExplosionTest {

	//Variables
	public GranExplosion gExp;

	@Test
	public void testPuntosDeLaGranExplosion(){
		int val = 10;
		Dulce mockDulce = mock(Dulce.class);
		int mag = 5;
		
		gExp = new GranExplosion(mockDulce, val, mag);
		
		int valorEsperado = 50;
		
		assertEquals(valorEsperado, gExp.puntos());
	}
	
	@Test
	public void testCompararMagnitudes(){
		int val = 10;
		Dulce mockDulce = mock(Dulce.class);
		int mag = 5;
		
		gExp = new GranExplosion(mockDulce, val, mag);
		
		int magnitud = 5;
		
		assertTrue(gExp.mismaMagnitud(magnitud));
	}
	
}
