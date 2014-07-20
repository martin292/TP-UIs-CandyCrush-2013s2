package dificultad;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

import org.junit.Test;

import dulces.Dulce;

public class DificultadTest {

	protected Dificultad dificultad;
	
	@Test
	public void testDificultad() {
		List<Dulce> dulces = new ArrayList<Dulce>();
		dulces.add(mock(Dulce.class));
		dulces.add(mock(Dulce.class));
		dulces.add(mock(Dulce.class));
		
		String nombre = "dificultad";
		
		this.dificultad = new Dificultad(nombre, dulces);
		assertEquals(dulces, this.dificultad.getDulces());
		assertEquals(nombre, this.dificultad.getNombre());
	}
	
	@Test
	public void testDulceRandom(){
		Dulce dulce1 = mock(Dulce.class);
		Dulce dulce2 = mock(Dulce.class);
		Dulce dulce3 = mock(Dulce.class);
		Dulce dulce4 = mock(Dulce.class);
		List<Dulce> dulces = new ArrayList<Dulce>();
		dulces.add(dulce1);dulces.add(dulce2);
		dulces.add(dulce3);dulces.add(dulce4);
		
		this.dificultad = new Dificultad("dificultad", dulces);
		Dulce random = this.dificultad.dulceRandom();
		assertTrue(dulces.contains(random));
	}

}
