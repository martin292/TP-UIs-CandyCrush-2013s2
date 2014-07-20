package explosiones;

import org.junit.Test;

import dulces.Dulce;
import static org.junit.Assert.*;
import explosiones.Explosion;
import static org.mockito.Mockito.*;

public class ExplosionTest{

	//Variables
	public Explosion exp;

	@Test
	public void testPuntosDeLaExplosion(){
		int val = 10;
		Dulce mockDulce = mock(Dulce.class);
		exp = new Explosion(mockDulce, val);
		
		int valorEsperado = 30;
		
		assertEquals(valorEsperado, exp.puntos());
	}
	
	@Test
	public void testExplosionCompatibleConColor(){
		int val = 10;
		Dulce mockDulce = mock(Dulce.class);
		exp = new Explosion(mockDulce, val);
		
		Dulce colorACompara = mock(Dulce.class);
		
		when(mockDulce.compatibleConColor(colorACompara)).thenReturn(true);
		
		assertTrue(exp.colorIgualA(colorACompara));
		
		verify(mockDulce).compatibleConColor(colorACompara);
	}
	
	@Test
	public void testCompararMagnitudes(){
		int val = 10;
		Dulce mockDulce = mock(Dulce.class);
		exp = new Explosion(mockDulce, val);
		
		int magnitud = 3;
		
		assertTrue(exp.mismaMagnitud(magnitud));
	}
	
}