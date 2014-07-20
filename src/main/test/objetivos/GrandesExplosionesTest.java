package objetivos;

import static org.mockito.Mockito.*;

import org.junit.Test;

import objetivos.GrandesExplosiones;
import objetivos.ObjetivoParaCumplir;
import dulces.Dulce;
import explosiones.Explosion;
import static org.junit.Assert.*;

public class GrandesExplosionesTest {
	
	//Variables
	public GrandesExplosiones obj;
	
	@Test
	public void testObjetivoCompatibleConExplocion(){
		int puntos = 10;
		int cant = 5;
		Dulce mockDulce = mock(Dulce.class);
		
		obj = new GrandesExplosiones(puntos, mockDulce, cant);
		
		Explosion mockExp = mock(Explosion.class);
		
		when(mockExp.mismaMagnitud(5)).thenReturn(true);
		when(mockExp.colorIgualA(mockDulce)).thenReturn(true);
		
		assertTrue(obj.esCompatible(mockExp));
		
		verify(mockExp).mismaMagnitud(5);
		verify(mockExp).colorIgualA(mockDulce);
	}
	
	@Test
	public void testChequearRealizacionDelObjetivo(){
		int puntos = 10;
		int cant = 5;
		Dulce mockDulce = mock(Dulce.class);
		
		obj = new GrandesExplosiones(puntos, mockDulce, cant);
		
		ObjetivoParaCumplir mockOPC = mock(ObjetivoParaCumplir.class);
		Explosion mockExp = mock(Explosion.class);
		
		when(mockExp.mismaMagnitud(5)).thenReturn(true);
		when(mockExp.colorIgualA(mockDulce)).thenReturn(true);
		
		assertTrue(obj.chequearRealizacion(mockOPC, mockExp));
		
		verify(mockExp).mismaMagnitud(5);
		verify(mockExp).colorIgualA(mockDulce);
	}
	
}