package direcciones;

import static org.junit.Assert.*;

import java.awt.Point;
import java.util.*;

import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;

public class ArribaTest extends DireccionTest {

	protected Arriba arriba;
	
	@Before
	public void setUp() throws Exception {
		this.arriba = new Arriba();
	}
	
	@Test
	public void testPuedeMover_False_DistintoX() {
		Point point = new Point(2,4);
		Point dimension = new Point(3,4);
		
		assertFalse(this.arriba.puedeMover(point, dimension));
	}
	
	@Test
	public void testPuedeMover_False_MismoX() {
		Point point = new Point(1,3);
		Point dimension = point;
		
		assertFalse(this.arriba.puedeMover(point, dimension));
	}
	
	@Test
	public void testPuedeMover_True_DistintoX() {
		Point point = new Point(1,0);
		Point dimension = new Point(2,3);
		
		assertTrue(this.arriba.puedeMover(point, dimension));
	}
	
	@Test
	public void testPuedeMover_True_MismoX() {
		Point point = new Point(2,2);
		Point dimension = new Point(2,3);
		
		assertTrue(this.arriba.puedeMover(point, dimension));
	}

	@Test
	public void testPuntoEnDireccion() {
		Point point = new Point(2,3);
		Point pointExpected = new Point(2,4);
		
		assertEquals(pointExpected, this.arriba.puntoEnDireccion(point));
	}
	
	@Test
	public void testPuntoEnDireccion_ValorCero() {
		Point point = new Point(0,0);
		Point pointExpected = new Point(0,1);
		
		assertEquals(pointExpected, this.arriba.puntoEnDireccion(point));
	}

	@Test
	public void testPuntosA(){
		int cantidad = 3;
		Point celda0 = new Point(2,3);
		Point celda1 = new Point(2,4);
		Point celda2 = new Point(2,5);
		Point celda3 = new Point(2,6);
		List<Point> puntos = new ArrayList<Point>();
		puntos.add(celda1);
		puntos.add(celda2);
		puntos.add(celda3);
		
		assertEquals(puntos, this.arriba.puntosA(celda0, cantidad));
	}
	
	@Test
	public void testPuntosA_Cantidad0(){
		int cantidad = 0;
		Point celda0 = mock(Point.class);
		List<Point> puntos = new ArrayList<Point>();
		
		assertEquals(puntos, this.arriba.puntosA(celda0, cantidad));
	}
	
	@Test
	public void testOpuesta(){
		assertEquals(Abajo.class, this.arriba.opuesta().getClass());
	}

}
