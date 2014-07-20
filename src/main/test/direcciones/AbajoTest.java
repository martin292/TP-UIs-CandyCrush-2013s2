package direcciones;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class AbajoTest extends DireccionTest {

	protected Abajo abajo;
	
	@Before
	public void setUp() throws Exception {
		this.abajo = new Abajo();
	}
	
	@Test
	public void testPuedeMover_False_DistintoX() {
		Point point = new Point(2,0);
		Point dimension = new Point(3,0);
		
		assertFalse(this.abajo.puedeMover(point, dimension));
	}
	
	@Test
	public void testPuedeMover_False_MismoX() {
		Point point = new Point(1,0);
		Point dimension = point;
		
		assertFalse(this.abajo.puedeMover(point, dimension));
	}
	
	@Test
	public void testPuedeMover_True_DistintoY() {
		Point point = new Point(1,2);
		Point dimension = new Point(2,3);
		
		assertTrue(this.abajo.puedeMover(point, dimension));
	}
	
	@Test
	public void testPuedeMover_True_MismoY() {
		Point point = new Point(2,3);
		Point dimension = new Point(2,3);
		
		assertTrue(this.abajo.puedeMover(point, dimension));
	}

	@Test
	public void testPuntoEnDireccion() {
		Point point = new Point(2,3);
		Point pointExpected = new Point(2,2);
		
		assertEquals(pointExpected, this.abajo.puntoEnDireccion(point));
	}
	
	@Test
	public void testPuntoEnDireccion_ValorCero() {
		Point point = new Point(0,1);
		Point pointExpected = new Point(0,0);
		
		assertEquals(pointExpected, this.abajo.puntoEnDireccion(point));
	}
	
	@Test
	public void testPuntosA(){
		int cantidad = 3;
		Point celda0 = new Point(2,3);
		Point celda1 = new Point(2,2);
		Point celda2 = new Point(2,1);
		Point celda3 = new Point(2,0);
		List<Point> puntos = new ArrayList<Point>();
		puntos.add(celda1);
		puntos.add(celda2);
		puntos.add(celda3);
		
		assertEquals(puntos, this.abajo.puntosA(celda0, cantidad));
	}
	
	@Test
	public void testPuntosA_Cantidad0(){
		int cantidad = 0;
		Point celda0 = mock(Point.class);
		List<Point> puntos = new ArrayList<Point>();
		
		assertEquals(puntos, this.abajo.puntosA(celda0, cantidad));
	}
	
	@Test
	public void testOpuesta(){
		assertEquals(Arriba.class, this.abajo.opuesta().getClass());
	}
	
}
