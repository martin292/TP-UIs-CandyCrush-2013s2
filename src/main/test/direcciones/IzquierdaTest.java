package direcciones;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class IzquierdaTest extends DireccionTest {

	protected Izquierda izquierda;
	
	@Before
	public void setUp() throws Exception {
		this.izquierda = new Izquierda();
	}
	
	@Test
	public void testPuedeMover_False_DistintoY() {
		Point point = new Point(0,4);
		Point dimension = new Point(3,5);
		
		assertFalse(this.izquierda.puedeMover(point, dimension));
	}
	
	@Test
	public void testPuedeMover_False_MismoY() {
		Point point = new Point(0,3);
		Point dimension = point;
		
		assertFalse(this.izquierda.puedeMover(point, dimension));
	}
	
	@Test
	public void testPuedeMover_True_DistintoY() {
		Point point = new Point(1,0);
		Point dimension = new Point(2,3);
		
		assertTrue(this.izquierda.puedeMover(point, dimension));
	}
	
	@Test
	public void testPuedeMover_True_MismoY() {
		Point point = new Point(3,2);
		Point dimension = new Point(3,2);
		
		assertTrue(this.izquierda.puedeMover(point, dimension));
	}

	@Test
	public void testPuntoEnDireccion() {
		Point point = new Point(2,3);
		Point pointExpected = new Point(1,3);
		
		assertEquals(pointExpected, this.izquierda.puntoEnDireccion(point));
	}
	
	@Test
	public void testPuntoEnDireccion_ValorCero() {
		Point point = new Point(1,0);
		Point pointExpected = new Point(0,0);
		
		assertEquals(pointExpected, this.izquierda.puntoEnDireccion(point));
	}

	@Test
	public void testPuntosA(){
		int cantidad = 3;
		Point celda0 = new Point(5,5);
		Point celda1 = new Point(4,5);
		Point celda2 = new Point(3,5);
		Point celda3 = new Point(2,5);
		List<Point> puntos = new ArrayList<Point>();
		puntos.add(celda1);
		puntos.add(celda2);
		puntos.add(celda3);
		
		assertEquals(puntos, this.izquierda.puntosA(celda0, cantidad));
	}
	
	@Test
	public void testPuntosA_Cantidad0(){
		int cantidad = 0;
		Point celda0 = mock(Point.class);
		List<Point> puntos = new ArrayList<Point>();
		
		assertEquals(puntos, this.izquierda.puntosA(celda0, cantidad));
	}
	
	@Test
	public void testOpuesta(){
		assertEquals(Derecha.class, this.izquierda.opuesta().getClass());
	}

}
