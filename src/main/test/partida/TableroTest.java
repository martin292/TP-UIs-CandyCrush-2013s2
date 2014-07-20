package partida;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.awt.Point;
import java.util.*;

import org.junit.Before;
import org.junit.Test;

import partida.Celda;
import partida.Partida;
import partida.Tablero;

import dificultad.Dificultad;
import direcciones.*;
import dulces.Dulce;
import explosiones.*;

public class TableroTest {

	protected Tablero tablero;
	
	protected Dulce dulceX = mock(Dulce.class);
	protected Dulce dulceO = mock(Dulce.class);
	protected Dulce dulceL = mock(Dulce.class);
	protected Dulce dulceM = mock(Dulce.class);
	
	@Before
	public void setUp() throws Exception {
		List<Dulce> dulces = new ArrayList<Dulce>();
		dulces.add(dulceX);
		dulces.add(dulceO);
		dulces.add(dulceL);
		dulces.add(dulceM);
		
		Dificultad dificultad = new Dificultad("dificultad", dulces);
		
		this.tablero = new Tablero(mock(Partida.class), 
				new Point(4,3), dificultad);
		
		when(dulceX.compatibleCon(dulceX)).thenReturn(true);
		when(dulceX.compatibleCon(dulceO)).thenReturn(false);
		when(dulceX.compatibleCon(dulceL)).thenReturn(false);
		when(dulceX.compatibleCon(dulceM)).thenReturn(false);
		when(dulceO.compatibleCon(dulceX)).thenReturn(false);
		when(dulceO.compatibleCon(dulceO)).thenReturn(true);
		when(dulceO.compatibleCon(dulceL)).thenReturn(false);
		when(dulceO.compatibleCon(dulceM)).thenReturn(false);
		when(dulceL.compatibleCon(dulceX)).thenReturn(false);
		when(dulceL.compatibleCon(dulceO)).thenReturn(false);
		when(dulceL.compatibleCon(dulceL)).thenReturn(true);
		when(dulceL.compatibleCon(dulceM)).thenReturn(false);
		when(dulceM.compatibleCon(dulceX)).thenReturn(false);
		when(dulceM.compatibleCon(dulceO)).thenReturn(false);
		when(dulceM.compatibleCon(dulceL)).thenReturn(false);
		when(dulceM.compatibleCon(dulceM)).thenReturn(true);
		
		this.tablero.setEnJuego(false);
	}
	
	@Test
	public void testTablero() {
		Partida partida = mock(Partida.class);
		List<Dulce> dulces = new ArrayList<Dulce>();
		dulces.add(dulceX);
		dulces.add(dulceO);
		dulces.add(dulceL);
		dulces.add(dulceM);
		
		Dificultad dificultad = new Dificultad("dificultad", dulces);

		int dimensionX = 5;
		int dimensionY = 4;
		Point dimension = new Point(dimensionX, dimensionY);
		
		this.tablero = new Tablero(partida, dimension, dificultad);
		
		assertEquals(dimension, this.tablero.getDimension());
		assertEquals(dificultad, this.tablero.getDificultad());
		assertEquals(partida, this.tablero.getPartida());
		
		assertEquals(dimensionY + 1, this.tablero.getCeldas().length);
		assertEquals(dimensionX + 1, this.tablero.getCeldas()[0].length);
		
		assertTrue(this.tablero.isEnJuego());
	}

	@Test
	public void testDulceEn() {
		Dulce dulce = mock(Dulce.class);
		Point celda = new Point(2,3);
		this.tablero.ponerDulceEn(dulce, celda);
		
		assertEquals(dulce, this.tablero.dulceEn(celda));
	}

	@Test
	public void testSetCelda() {
		Celda celda = mock(Celda.class);
		Point point = new Point(2,3);
		
		this.tablero.setCelda(celda, point);
		
		assertEquals(celda, this.tablero.getCelda(point));
	}

	@Test
	public void testPonerDulceEn() {
		Dulce dulce = mock(Dulce.class);
		Point celda = new Point(2,3);
		
		this.tablero.ponerDulceEn(dulce, celda);
		
		assertEquals(dulce, this.tablero.dulceEn(celda));
	}

	@Test
	public void testEsCeldaVacia_False() {
		Point celda = new Point(3,2);
		this.tablero.ponerDulceEn(mock(Dulce.class), celda);
		assertFalse(this.tablero.esCeldaVacia(celda));
	}
	
	@Test
	public void testEsCeldaVacia_True() {
		Point celda = new Point(3,2);
		this.tablero.ponerDulceEn(mock(Dulce.class), celda);
		this.tablero.destruirCelda(celda);
		
		assertTrue(this.tablero.esCeldaVacia(celda));
	}

	@Test
	public void testDestruirCelda() {
		Point celda = new Point(3,2);
		this.tablero.ponerDulceEn(mock(Dulce.class), celda);
		assertFalse(this.tablero.esCeldaVacia(celda));
		
		this.tablero.destruirCelda(celda);
		
		assertTrue(this.tablero.esCeldaVacia(celda));
	}

	@Test
	public void testCeldaOrigen() {
		Point celda = new Point(0,0);
		
		assertEquals(celda, this.tablero.puntoOrigen());
	}

	@Test
	public void testMoverCeldaA_SinExplosion() {
		this.tableroSinExplosion();
		/*
		 * X O M M O
		 * O X M O X
		 * X M X X L
		 * X O M M L
		 */
		
		Point celda1 = new Point(0,0);
		Point celda2 = new Point(1,0);
		
		Dulce dulce1 = this.tablero.dulceEn(celda1);
		Dulce dulce2 = this.tablero.dulceEn(celda2);
		
		this.tablero.moverCeldaA(celda1, new Derecha());
		
		assertEquals(dulce1, this.tablero.dulceEn(celda1));
		assertEquals(dulce2, this.tablero.dulceEn(celda2));
	}
	
	@Test
	public void testMoverCeldaA_ConExplosion() {
		this.tableroSinExplosion2();		
		/*
		 * X O M X M
		 * O O X L X
		 * X M X O L
		 * X O M M L
		 */
		
		Point celda32 = new Point(3,2);
		
		Point celda23 = new Point(2,3);
		Point celda43 = new Point(4,3);
		
		Dulce dulce32 = this.tablero.dulceEn(celda32);
		
		Dulce dulce23 = this.tablero.dulceEn(celda23);
		Dulce dulce43 = this.tablero.dulceEn(celda43);
		
		this.tablero.moverCeldaA(celda32, new Arriba());
		
		assertEquals(dulce23, this.tablero.dulceEn(new Point(2,2)));
		assertEquals(dulce32, this.tablero.dulceEn(new Point(3,2)));
		assertEquals(dulce43, this.tablero.dulceEn(new Point(4,2)));
	}
	
	@Test
	public void testMoverCeldas_SinExplosion() {
		this.tableroSinExplosion();
		/*
		 * X O M M O
		 * O X M O X
		 * X M X X L
		 * X O M M L
		 */
		
		Point celda1 = new Point(0,0);
		Point celda2 = new Point(1,0);
		
		Dulce dulce1 = this.tablero.dulceEn(celda1);
		Dulce dulce2 = this.tablero.dulceEn(celda2);
		
		this.tablero.moverCeldas(celda1, celda2);
		
		assertEquals(dulce1, this.tablero.dulceEn(celda1));
		assertEquals(dulce2, this.tablero.dulceEn(celda2));
	}
	
	@Test
	public void testMoverCeldas_ConExplosion() {
		this.tableroSinExplosion2();		
		/*
		 * X O M X M
		 * O O X L X
		 * X M X O L
		 * X O M M L
		 */
		
		Point celda33 = new Point(3,3);
		Point celda32 = new Point(3,2);
		
		Point celda23 = new Point(2,3);
		Point celda43 = new Point(4,3);
		
		Dulce dulce32 = this.tablero.dulceEn(celda32);
		
		Dulce dulce23 = this.tablero.dulceEn(celda23);
		Dulce dulce43 = this.tablero.dulceEn(celda43);
		
		this.tablero.moverCeldas(celda33, celda32);
		
		assertEquals(dulce23, this.tablero.dulceEn(new Point(2,2)));
		assertEquals(dulce32, this.tablero.dulceEn(new Point(3,2)));
		assertEquals(dulce43, this.tablero.dulceEn(new Point(4,2)));
	}
	
	@Test
	public void testIntercambiarCeldaA() {
		Point celda1 = new Point(2,3);
		Point celda2 = new Point(2,2);
		Direccion direccion = mock(Direccion.class);
		when(direccion.puntoEnDireccion(celda1)).thenReturn(celda2);
		Dulce dulce1 = mock(Dulce.class);
		Dulce dulce2 = mock(Dulce.class);
		this.tablero.ponerDulceEn(dulce1, celda1);
		this.tablero.ponerDulceEn(dulce2, celda2);
		
		this.tablero.intercambiarCeldaA(celda1, direccion);
		
		assertEquals(dulce2, this.tablero.dulceEn(celda1));
		assertEquals(dulce1, this.tablero.dulceEn(celda2));
	}

	@Test
	public void testIntercambiarCeldas() {
		Point celda1 = new Point(2,3);
		Point celda2 = new Point(2,2);
		Dulce dulce1 = mock(Dulce.class);
		Dulce dulce2 = mock(Dulce.class);
		this.tablero.ponerDulceEn(dulce1, celda1);
		this.tablero.ponerDulceEn(dulce2, celda2);
		
		this.tablero.intercambiarCeldas(celda1, celda2);
		
		assertEquals(dulce2, this.tablero.dulceEn(celda1));
		assertEquals(dulce1, this.tablero.dulceEn(celda2));
	}
	
	@Test
	public void testBuscarYNotificarExplosionesDesde_NingunaCeldaExplosion() {
		List<Point> aExplotar = this.tableroSinExplosion();
		/*
		 * X O M M O
		 * O X M O X
		 * X M X X L
		 * X O M M L
		 */
		
		Point celda1 = new Point(3,2);
		Point celda2 = new Point(3,3);
		List<Point> celdas = new ArrayList<Point>();
		celdas.add(celda1);
		celdas.add(celda2);
		
		List<Point> detectadas = this.tablero.buscarYNotificarExplosionesDesde(celdas);
		
		assertEquals(aExplotar.size(), detectadas.size());
		assertTrue(detectadas.containsAll(aExplotar));
	}
	
	@Test
	public void testBuscarYNotificarExplosionesDesde_PrimeraCeldaExplosion() {
		List<Point> aExplotar = this.tableroExplosionHorizontal();
		/*
		 * X O M M O
		 * O X M M X
		 * X X X L L
		 * X O O M M
		 */
		
		Point celda1 = new Point(1,1);
		Point celda2 = new Point(1,0);
		List<Point> celdas = new ArrayList<Point>();
		celdas.add(celda1);
		celdas.add(celda2);
		
		List<Point> detectadas = this.tablero.buscarYNotificarExplosionesDesde(celdas);
		
		assertEquals(aExplotar.size(), detectadas.size());
		assertTrue(detectadas.containsAll(aExplotar));
	}
	
	@Test
	public void testBuscarYNotificarExplosionesDesde_SegundaCeldaExplosion() {
		List<Point> aExplotar = this.tableroExplosionHorizontal();
		/*
		 * X O M M O
		 * O X M M X
		 * X X X L L
		 * X O O M M
		 */
		
		Point celda1 = new Point(1,0);
		Point celda2 = new Point(1,1);
		List<Point> celdas = new ArrayList<Point>();
		celdas.add(celda1);
		celdas.add(celda2);
		
		List<Point> detectadas = this.tablero.buscarYNotificarExplosionesDesde(celdas);
		
		assertEquals(aExplotar.size(), detectadas.size());
		assertTrue(detectadas.containsAll(aExplotar));
	}
	
	@Test
	public void testBuscarYNotificarExplosionesDesde_AmbasCeldasExplosion() {
		List<Point> aExplotar = this.tablero2ExplosionesHorizontales();
		/*
		 * X O M M O
		 * O M M M X
		 * X X X L L
		 * X O O M M
		 */
		
		Point celda1 = new Point(1,1);
		Point celda2 = new Point(1,2);
		List<Point> celdas = new ArrayList<Point>();
		celdas.add(celda1);
		celdas.add(celda2);
		
		List<Point> detectadas = this.tablero.buscarYNotificarExplosionesDesde(celdas);
		
		assertEquals(aExplotar.size(), detectadas.size());
		assertTrue(detectadas.containsAll(aExplotar));
	}

	@Test
	public void testBuscarYNotificarExplosionEn_SinExplosion() {
		List<Point> aExplotar = this.tableroSinExplosion();
		/*
		 * X O M M O
		 * O X M O X
		 * X M X X L
		 * X O M M L
		 */
		
		Point celda = new Point(3,2);;
		
		List<Point> detectadas = this.tablero.buscarYNotificarExplosionEn(celda);
		
		assertEquals(aExplotar.size(), detectadas.size());
		assertTrue(detectadas.containsAll(aExplotar));
	}
	
	@Test
	public void testBuscarYNotificarExplosionEn_DesdeIzquierdaConExplosionHorizontal() {
		List<Point> aExplotar = this.tableroExplosionHorizontal();
		/*
		 * X O M M O
		 * O X M M X
		 * X X X L L
		 * X O O M M
		 */
		
		Point celda = new Point(0,1);
		
		List<Point> detectadas = this.tablero.buscarYNotificarExplosionEn(celda);
		
		assertEquals(aExplotar.size(), detectadas.size());
		assertTrue(detectadas.containsAll(aExplotar));
	}
	
	@Test
	public void testBuscarYNotificarExplosionEn_DesdeCentroConExplosionHorizontal() {
		List<Point> aExplotar = this.tableroExplosionHorizontal();
		/*
		 * X O M M O
		 * O X M M X
		 * X X X L L
		 * X O O M M
		 */
		
		Point celda = new Point(1,1);
		
		List<Point> detectadas = this.tablero.buscarYNotificarExplosionEn(celda);
		
		assertEquals(aExplotar.size(), detectadas.size());
		assertTrue(detectadas.containsAll(aExplotar));
	}
	
	@Test
	public void testBuscarYNotificarExplosionEn_DesdeDerechaConExplosionHorizontal() {
		List<Point> aExplotar = this.tableroExplosionHorizontal();
		/*
		 * X O M M O
		 * O X M M X
		 * X X X L L
		 * X O O M M
		 */
		
		Point celda = new Point(2,1);
		
		List<Point> detectadas = this.tablero.buscarYNotificarExplosionEn(celda);
		
		assertEquals(aExplotar.size(), detectadas.size());
		assertTrue(detectadas.containsAll(aExplotar));
	}
	
	@Test
	public void testBuscarYNotificarExplosionEn_DesdeAbajoConExplosionVertical() {
		List<Point> aExplotar = this.tableroExplosionVertical();
		/*
		 * X O M M O
		 * O X M M X
		 * X X M L L
		 * X O O M M
		 */
		
		Point celda = new Point(2,1);
		
		List<Point> detectadas = this.tablero.buscarYNotificarExplosionEn(celda);
		
		assertEquals(aExplotar.size(), detectadas.size());
		assertTrue(detectadas.containsAll(aExplotar));
	}
	
	@Test
	public void testBuscarYNotificarExplosionEn_DesdeCentroConExplosionVertical() {
		List<Point> aExplotar = this.tableroExplosionVertical();
		/*
		 * X O M M O
		 * O X M M X
		 * X X M L L
		 * X O O M M
		 */
		
		Point celda = new Point(2,2);
		
		List<Point> detectadas = this.tablero.buscarYNotificarExplosionEn(celda);
		
		assertEquals(aExplotar.size(), detectadas.size());
		assertTrue(detectadas.containsAll(aExplotar));
	}
	
	@Test
	public void testBuscarYNotificarExplosionEn_DesdeArribaConExplosionVertical() {
		List<Point> aExplotar = this.tableroExplosionVertical();
		/*
		 * X O M M O
		 * O X M M X
		 * X X M L L
		 * X O O M M
		 */
		
		Point celda = new Point(2,3);
		
		List<Point> detectadas = this.tablero.buscarYNotificarExplosionEn(celda);
		
		assertEquals(aExplotar.size(), detectadas.size());
		assertTrue(detectadas.containsAll(aExplotar));
	}
	
	@Test
	public void testAciertosDesdeCeldaHacia_SinAciertos() {
		this.tableroExplosionVertical();
		/*
		 * X O M M O
		 * O X M M X
		 * X X M L L
		 * X O O M M
		 */
		
		Point celda = new Point(2,1);
		
		assertEquals(0, this.tablero.aciertosDesdeCeldaHacia(celda, new Abajo()));
	}
	
	@Test
	public void testAciertosDesdeCeldaHacia_UnAcierto() {
		this.tableroExplosionVertical();
		/*
		 * X O M M O
		 * O X M M X
		 * X X M L L
		 * X O O M M
		 */
		
		Point celda = new Point(3,2);
		
		assertEquals(1, this.tablero.aciertosDesdeCeldaHacia(celda, new Izquierda()));
	}
	
	@Test
	public void testAciertosDesdeCeldaHacia_VariosAciertos() {
		this.tableroExplosion4Vertical();
		/*
		 * X X M M O
		 * O X X M X
		 * X X M L L
		 * X X O M M
		 */
		
		Point celda = new Point(1,1);
		assertEquals(2, this.tablero.aciertosDesdeCeldaHacia(celda, new Arriba()));
		
		celda = new Point(1,3);
		assertEquals(3, this.tablero.aciertosDesdeCeldaHacia(celda, new Abajo()));
	}
	
	@Test
	public void testCrearExplosion_Comun() {
		Dulce dulce = mock(Dulce.class);
		int magnitud = 3;
		
		Explosion explosion = this.tablero.crearExplosion(dulce, magnitud);
		
		assertTrue(explosion.getClass() == Explosion.class);
	}
	
	@Test
	public void testCrearExplosion_Grande() {
		Dulce dulce = mock(Dulce.class);
		int magnitud = 4;
		
		Explosion explosion = this.tablero.crearExplosion(dulce, magnitud);
		
		assertTrue(explosion.getClass() == GranExplosion.class);
	}

	@Test
	public void testDestruirCeldas() {
		List<Point> aExplotar = this.tableroExplosionHorizontal();
		/*
		 * X O M M O
		 * O X M M X
		 * X X X L L
		 * X O O M M
		 */
		
		assertFalse(this.tablero.esCeldaVacia(aExplotar.get(0)));
		assertFalse(this.tablero.esCeldaVacia(aExplotar.get(1)));
		assertFalse(this.tablero.esCeldaVacia(aExplotar.get(2)));
		
		this.tablero.destruirCeldas(aExplotar);
		
		assertTrue(this.tablero.esCeldaVacia(aExplotar.get(0)));
		assertTrue(this.tablero.esCeldaVacia(aExplotar.get(1)));
		assertTrue(this.tablero.esCeldaVacia(aExplotar.get(2)));
	}

	@Test
	public void testMoverCeldasPorDestruccion_Horizontal() {
		List<Point> aExplotar = this.tableroExplosionHorizontal();
		/*
		 * X O M M O
		 * O X M M X
		 * X X X L L
		 * X O O M M
		 */
		
		Dulce d02 = this.tablero.dulceEn(new Point(0,2));
		Dulce d12 = this.tablero.dulceEn(new Point(1,2));
		Dulce d22 = this.tablero.dulceEn(new Point(2,2));
		Dulce d03 = this.tablero.dulceEn(new Point(0,3));
		Dulce d13 = this.tablero.dulceEn(new Point(1,3));
		Dulce d23 = this.tablero.dulceEn(new Point(2,3));
		
		Dulce d33 = this.tablero.dulceEn(new Point(3,3));
		Dulce d43 = this.tablero.dulceEn(new Point(4,3));
		
		this.tablero.destruirCeldas(aExplotar);
		
		/* Deberia quedar:
		 *       M O
		 * X O M M X
		 * O X M L L
		 * X O O M M
		 */
		
		this.tablero.moverCeldasPorDestruccion(aExplotar);
		
		assertEquals(d02, this.tablero.dulceEn(new Point(0,1)));
		assertEquals(d12, this.tablero.dulceEn(new Point(1,1)));
		assertEquals(d22, this.tablero.dulceEn(new Point(2,1)));
		assertEquals(d03, this.tablero.dulceEn(new Point(0,2)));
		assertEquals(d13, this.tablero.dulceEn(new Point(1,2)));
		assertEquals(d23, this.tablero.dulceEn(new Point(2,2)));
		assertTrue(this.tablero.esCeldaVacia(new Point(0,3)));
		assertTrue(this.tablero.esCeldaVacia(new Point(1,3)));
		assertTrue(this.tablero.esCeldaVacia(new Point(2,3)));
		
		//se revisa que la parte de arriba de las demas columnas no hayan cambiado
		assertEquals(d33, this.tablero.dulceEn(new Point(3,3)));
		assertEquals(d43, this.tablero.dulceEn(new Point(4,3)));
		
	}
	
	@Test
	public void testMoverCeldasPorDestruccion_Vertical() {
		List<Point> aExplotar = this.tableroExplosionVertical2();
		/*
		 * X O M M O
		 * O X X M L
		 * X X M L L
		 * X O O M L
		 */
		
		Dulce d43 = this.tablero.dulceEn(new Point(4,3));
		
		Dulce d03 = this.tablero.dulceEn(new Point(0,3));
		Dulce d13 = this.tablero.dulceEn(new Point(1,3));
		Dulce d23 = this.tablero.dulceEn(new Point(2,3));
		Dulce d33 = this.tablero.dulceEn(new Point(3,3));
		
		this.tablero.destruirCeldas(aExplotar);
		
		/* Deberia quedar:
		 * X O M M 
		 * O X X M 
		 * X X M L 
		 * X O O M O
		 */
		
		this.tablero.moverCeldasPorDestruccion(aExplotar);
		
		assertEquals(d43, this.tablero.dulceEn(new Point(4,0)));
		assertTrue(this.tablero.esCeldaVacia(new Point(4,1)));
		assertTrue(this.tablero.esCeldaVacia(new Point(4,2)));
		assertTrue(this.tablero.esCeldaVacia(new Point(4,3)));
		
		//se revisa que la parte de arriba de las demas columnas no hayan cambiado
		assertEquals(d03, this.tablero.dulceEn(new Point(0,3)));
		assertEquals(d13, this.tablero.dulceEn(new Point(1,3)));
		assertEquals(d23, this.tablero.dulceEn(new Point(2,3)));
		assertEquals(d33, this.tablero.dulceEn(new Point(3,3)));
	}
	
	@Test
	public void testReponerDulces() {
		this.tableroSinExplosion();
		/*
		 * X O M M O
		 * O X M O X
		 * X M X X L
		 * X O M M L
		 */
		
		//celdas a reponer
		Point c1 = new Point(0,3);
		Point c2 = new Point(0,2);
		Point c3 = new Point(0,1);
		Point c4 = new Point(1,3);
		Point c5 = new Point(2,3);
		Point c6 = new Point(2,2);
		Point c7 = new Point(2,1);
		Point c8 = new Point(2,0);
		
		this.tablero.destruirCelda(c1);
		this.tablero.destruirCelda(c2);
		this.tablero.destruirCelda(c3);
		this.tablero.destruirCelda(c4);
		this.tablero.destruirCelda(c5);
		this.tablero.destruirCelda(c6);
		this.tablero.destruirCelda(c7);
		this.tablero.destruirCelda(c8);
		
		List<Point> bases = new ArrayList<Point>();
		bases.add(new Point(0,0));
		bases.add(new Point(1,1));
		bases.add(new Point(2,0));
		
		this.tablero.reponerDulces(bases);
		
		assertFalse(this.tablero.esCeldaVacia(c1));
		assertFalse(this.tablero.esCeldaVacia(c2));
		assertFalse(this.tablero.esCeldaVacia(c3));
		assertFalse(this.tablero.esCeldaVacia(c4));
		assertFalse(this.tablero.esCeldaVacia(c5));
		assertFalse(this.tablero.esCeldaVacia(c6));
		assertFalse(this.tablero.esCeldaVacia(c7));
		assertFalse(this.tablero.esCeldaVacia(c8));
	}

	@Test
	public void testBuscarExplosionYNotificar_SinExplosion() {
		List<Point> sinExp = this.tableroSinExplosion();
		/*
		 * X O M M O
		 * O X M O X
		 * X M X X L
		 * X O M M L
		 */
		
		assertEquals(sinExp, this.tablero.buscarExplosionYNotificar(false));
		assertEquals(sinExp, this.tablero.buscarExplosionYNotificar(true));
	}
	
	@Test
	public void testBuscarExplosionYNotificar_ConExplosionVertical() {
		List<Point> explosion = this.tableroExplosionVertical2();
		/*
		 * X O M M O
		 * O X X M L
		 * X X M L L
		 * X O O M L
		 */
		
		assertEquals(explosion, this.tablero.buscarExplosionYNotificar(false));
		assertEquals(explosion, this.tablero.buscarExplosionYNotificar(true));
	}
	
	@Test
	public void testBuscarExplosionYNotificar_ConExplosionHorizontal() {
		List<Point> explosion = this.tableroExplosion4Horizontal();
		/*
		 * X X M M O
		 * O X X X X
		 * X M M L L
		 * X X O M M
		 */
		
		assertEquals(explosion, this.tablero.buscarExplosionYNotificar(false));
		assertEquals(explosion, this.tablero.buscarExplosionYNotificar(true));
	}

	@Test
	public void testBuscarExplosionDesdeOrigenEnDireccion_SinExplosion() {
		List<Point> sinExp = this.tableroSinExplosion();
		/*
		 * X O M M O
		 * O X M O X
		 * X M X X L
		 * X O M M L
		 */
		
		assertEquals(sinExp, this.tablero.buscarExplosionDesdeOrigenEnDireccion(new Arriba()));
		assertEquals(sinExp, this.tablero.buscarExplosionDesdeOrigenEnDireccion(new Derecha()));
	}
	
	@Test
	public void testBuscarExplosionDesdeOrigenEnDireccion_ExplosionVertical() {
		List<Point> explosiones = this.tableroExplosionVertical();
		/*
		 * X O M M O
		 * O X M M X
		 * X X M L L
		 * X O O M M
		 */
		
		List<Point> sinExp = new ArrayList<Point>();
		
		List<Point> explosionesArriba = this.tablero.buscarExplosionDesdeOrigenEnDireccion(new Arriba());
		
		assertEquals(sinExp, this.tablero.buscarExplosionDesdeOrigenEnDireccion(new Derecha()));
		assertEquals(explosiones.size(), explosionesArriba.size());
		assertTrue(explosionesArriba.containsAll(explosiones));
	}
	
	@Test
	public void testBuscarExplosionDesdeOrigenEnDireccion_ExplosionHorizontal() {
		List<Point> explosiones = this.tableroExplosionHorizontal();
		/*
		 * X O M M O
		 * O X M M X
		 * X X X L L
		 * X O O M M
		 */
		
		List<Point> sinExp = new ArrayList<Point>();
		
		List<Point> explosionesDerecha = this.tablero.buscarExplosionDesdeOrigenEnDireccion(new Derecha());
		
		assertEquals(sinExp, this.tablero.buscarExplosionDesdeOrigenEnDireccion(new Arriba()));
		assertEquals(explosiones.size(), explosionesDerecha.size());
		assertTrue(explosionesDerecha.containsAll(explosiones));
	}
	
	@Test
	public void testBuscarExplosionDesdeOrigenEnDireccion_GranExplosionVertical() {
		List<Point> explosiones = this.tableroExplosion4Vertical();
		/*
		 * X X M M O
		 * O X X M X
		 * X X M L L
		 * X X O M M
		 */
		
		List<Point> sinExp = new ArrayList<Point>();
		
		List<Point> explosionesArriba = this.tablero.buscarExplosionDesdeOrigenEnDireccion(new Arriba());
		
		assertEquals(sinExp, this.tablero.buscarExplosionDesdeOrigenEnDireccion(new Derecha()));
		assertEquals(explosiones.size(), explosionesArriba.size());
		assertTrue(explosionesArriba.containsAll(explosiones));
	}
	
	@Test
	public void testBuscarExplosionDesdeOrigenEnDireccion_GranExplosionHorizontal() {
		List<Point> explosiones = this.tableroExplosion4Horizontal();
		/*
		 * X X M M O
		 * O X X X X
		 * X M M L L
		 * X X O M M
		 */
		
		List<Point> sinExp = new ArrayList<Point>();
		
		List<Point> explosionesDerecha = this.tablero.buscarExplosionDesdeOrigenEnDireccion(new Derecha());
		
		assertEquals(sinExp, this.tablero.buscarExplosionDesdeOrigenEnDireccion(new Arriba()));
		assertEquals(explosiones.size(), explosionesDerecha.size());
		assertTrue(explosionesDerecha.containsAll(explosiones));
	}
	

	@Test
	public void testLlenarTablero() {
		int y = this.tablero.getDimension().y;
		int x = this.tablero.getDimension().x;
		
		this.tablero.setCeldas(new Celda[y + 1][x + 1]);
		
		for(int fila = 0; fila <= y; fila++)
		{	for(int columna = 0; columna <= x; columna++)
			{	this.tablero.setCelda(new Celda(fila, columna), new Point(columna, fila));
			}
		}
		
		assertTrue(this.tablero.esCeldaVacia(new Point(1,3)));
		
		this.tablero.llenarTablero();
		
		for(int columna = 0; columna < x; columna++)
		{	for(int fila = 0; fila < y; fila++)
			{	assertFalse(this.tablero.esCeldaVacia(new Point(columna, fila)));
			}
		}
		
		assertTrue(this.tablero.buscarExplosionYNotificar(false).isEmpty());
	}

	@Test
	public void testLlenarTableroConPotencialesExplosiones() {
		int y = this.tablero.getDimension().y;
		int x = this.tablero.getDimension().x;
		
		this.tablero.setCeldas(new Celda[y + 1][x + 1]);
		
		for(int fila = 0; fila <= y; fila++)
		{	for(int columna = 0; columna <= x; columna++)
			{	this.tablero.setCelda(new Celda(fila, columna), new Point(columna, fila));
			}
		}
		
		assertTrue(this.tablero.esCeldaVacia(new Point(1,3)));
		
		this.tablero.llenarTableroConPotencialesExplosiones();
		
		for(int columna = 0; columna < x; columna++)
		{	for(int fila = 0; fila < y; fila++)
			{	assertFalse(this.tablero.esCeldaVacia(new Point(columna, fila)));
			}
		}
	}
	
	@Test
	public void testEsCeldaAdyacente_False(){
		assertFalse(this.tablero.esCeldaAdyacente(new Point(2,2), new Point(3,3)));
		assertFalse(this.tablero.esCeldaAdyacente(new Point(2,2), new Point(3,1)));
		assertFalse(this.tablero.esCeldaAdyacente(new Point(2,2), new Point(1,3)));
		assertFalse(this.tablero.esCeldaAdyacente(new Point(2,2), new Point(1,1)));
	}
	
	@Test
	public void testEsCeldaAdyacente_True(){
		assertTrue(this.tablero.esCeldaAdyacente(new Point(2,2), new Point(2,3)));
		assertTrue(this.tablero.esCeldaAdyacente(new Point(2,2), new Point(2,1)));
		assertTrue(this.tablero.esCeldaAdyacente(new Point(2,2), new Point(3,2)));
		assertTrue(this.tablero.esCeldaAdyacente(new Point(2,2), new Point(1,2)));
	}
	
	//operaciones auxiliares
	protected void crearTablero(
						Dulce d16, Dulce d17, Dulce d18, Dulce d19, Dulce d20,
						Dulce d11, Dulce d12, Dulce d13, Dulce d14, Dulce d15, 
						Dulce d6, Dulce d7, Dulce d8, Dulce d9, Dulce d10, 
						Dulce d1, Dulce d2, Dulce d3, Dulce d4, Dulce d5){
		/*
		 * d16 d17 d18 d19 d20
		 * d11 d12 d13 d14 d15
		 * d6  d7  d8  d9  d10
		 * d1  d2  d3  d4  d5
		 */
		this.tablero.ponerDulceEn(d1, new Point(0,0));
		this.tablero.ponerDulceEn(d2, new Point(1,0));
		this.tablero.ponerDulceEn(d3, new Point(2,0));
		this.tablero.ponerDulceEn(d4, new Point(3,0));
		this.tablero.ponerDulceEn(d5, new Point(4,0));
		this.tablero.ponerDulceEn(d6, new Point(0,1));
		this.tablero.ponerDulceEn(d7, new Point(1,1));
		this.tablero.ponerDulceEn(d8, new Point(2,1));
		this.tablero.ponerDulceEn(d9, new Point(3,1));
		this.tablero.ponerDulceEn(d10, new Point(4,1));
		this.tablero.ponerDulceEn(d11, new Point(0,2));
		this.tablero.ponerDulceEn(d12, new Point(1,2));
		this.tablero.ponerDulceEn(d13, new Point(2,2));
		this.tablero.ponerDulceEn(d14, new Point(3,2));
		this.tablero.ponerDulceEn(d15, new Point(4,2));
		this.tablero.ponerDulceEn(d16, new Point(0,3));
		this.tablero.ponerDulceEn(d17, new Point(1,3));
		this.tablero.ponerDulceEn(d18, new Point(2,3));
		this.tablero.ponerDulceEn(d19, new Point(3,3));
		this.tablero.ponerDulceEn(d20, new Point(4,3));
	}
	
	protected List<Point> tableroSinExplosion(){
		/*
		 * X O M M O
		 * O X M O X
		 * X M X X L
		 * X O M M L
		 */
		
		Dulce X = this.dulceX;
		Dulce O = this.dulceO;
		Dulce L = this.dulceL;
		Dulce M = this.dulceM;
		
		this.crearTablero(	
							X, O, M, M, O, 
							O, X, M, O, X, 
							X, M, X, X, L, 
							X, O, M, M, L);
		
		List<Point> aExplotar = new ArrayList<Point>();
		return aExplotar;
	}
	
	protected List<Point> tableroSinExplosion2(){
		/*
		 * X O M X M
		 * O O X L X
		 * X M X O L
		 * X O M M L
		 */
		
		Dulce X = this.dulceX;
		Dulce O = this.dulceO;
		Dulce L = this.dulceL;
		Dulce M = this.dulceM;
		
		this.crearTablero(	
							X, O, M, X, M, 
							O, O, X, L, X, 
							X, M, X, O, L, 
							X, O, M, M, L);
		
		List<Point> aExplotar = new ArrayList<Point>();
		return aExplotar;
	}
	
	
	protected List<Point> tableroExplosionHorizontal(){
		/*
		 * X O M M O
		 * O X M M X
		 * X X X L L
		 * X O O M M
		 */
		
		Dulce X = this.dulceX;
		Dulce O = this.dulceO;
		Dulce L = this.dulceL;
		Dulce M = this.dulceM;
		
		this.crearTablero(	
							X, O, M, M, O, 
							O, X, M, M, X, 
							X, X, X, L, L, 
							X, O, O, M, M);
		
		List<Point> aExplotar = new ArrayList<Point>();
		aExplotar.add(new Point(0,1));
		aExplotar.add(new Point(1,1));
		aExplotar.add(new Point(2,1));
		return aExplotar;
	}
	
	protected List<Point> tableroExplosion4Horizontal(){
		/*
		 * X X M M O
		 * O X X X X
		 * X M M L L
		 * X X O M M
		 */
		
		Dulce X = this.dulceX;
		Dulce O = this.dulceO;
		Dulce L = this.dulceL;
		Dulce M = this.dulceM;
		
		this.crearTablero(	
							X, X, M, M, O, 
							O, X, X, X, X, 
							X, M, M, L, L, 
							X, X, O, M, M);
		
		List<Point> aExplotar = new ArrayList<Point>();
		aExplotar.add(new Point(1,2));
		aExplotar.add(new Point(2,2));
		aExplotar.add(new Point(3,2));
		aExplotar.add(new Point(4,2));
		return aExplotar;
	}
	
	protected List<Point> tableroExplosionVertical(){
		/*
		 * X O M M O
		 * O X M M X
		 * X X M L L
		 * X O O M M
		 */
		
		Dulce X = this.dulceX;
		Dulce O = this.dulceO;
		Dulce L = this.dulceL;
		Dulce M = this.dulceM;
		
		this.crearTablero(	
							X, O, M, M, O, 
							O, X, M, M, X, 
							X, X, M, L, L, 
							X, O, O, M, M);
		
		List<Point> aExplotar = new ArrayList<Point>();
		aExplotar.add(new Point(2,1));
		aExplotar.add(new Point(2,2));
		aExplotar.add(new Point(2,3));
		return aExplotar;
	}
	
	protected List<Point> tableroExplosionVertical2(){
		/*
		 * X O M M O
		 * O X X M L
		 * X X M L L
		 * X O O M L
		 */
		
		Dulce X = this.dulceX;
		Dulce O = this.dulceO;
		Dulce L = this.dulceL;
		Dulce M = this.dulceM;
		
		this.crearTablero(	
							X, O, M, M, O, 
							O, X, X, M, L, 
							X, X, M, L, L, 
							X, O, O, M, L);
		
		List<Point> aExplotar = new ArrayList<Point>();
		aExplotar.add(new Point(4,0));
		aExplotar.add(new Point(4,1));
		aExplotar.add(new Point(4,2));
		return aExplotar;
	}
	
	protected List<Point> tableroExplosion4Vertical(){
		/*
		 * X X M M O
		 * O X X M X
		 * X X M L L
		 * X X O M M
		 */
		
		Dulce X = this.dulceX;
		Dulce O = this.dulceO;
		Dulce L = this.dulceL;
		Dulce M = this.dulceM;
		
		this.crearTablero(	
							X, X, M, M, O, 
							O, X, X, M, X, 
							X, X, M, L, L, 
							X, X, O, M, M);
		
		List<Point> aExplotar = new ArrayList<Point>();
		aExplotar.add(new Point(1,0));
		aExplotar.add(new Point(1,1));
		aExplotar.add(new Point(1,2));
		aExplotar.add(new Point(1,3));
		return aExplotar;
	}
	
	protected List<Point> tablero2ExplosionesHorizontales(){
		/*
		 * X O M M O
		 * O M M M X
		 * X X X L L
		 * X O O M M
		 */
		
		Dulce X = this.dulceX;
		Dulce O = this.dulceO;
		Dulce L = this.dulceL;
		Dulce M = this.dulceM;
		
		this.crearTablero(	
							X, O, M, M, O, 
							O, M, M, M, X, 
							X, X, X, L, L, 
							X, O, O, M, M);
		
		List<Point> aExplotar = new ArrayList<Point>();
		aExplotar.add(new Point(0,1));
		aExplotar.add(new Point(1,1));
		aExplotar.add(new Point(2,1));
		aExplotar.add(new Point(1,2));
		aExplotar.add(new Point(2,2));
		aExplotar.add(new Point(3,2));
		
		return aExplotar;
	}
	
	protected List<Point> tableroExplosionesEnCadena(){
		/*
		 * X O X L O
		 * X X M M M
		 * M O L X L
		 * X O O M M
		 */
		
		Dulce X = this.dulceX;
		Dulce O = this.dulceO;
		Dulce L = this.dulceL;
		Dulce M = this.dulceM;
		
		this.crearTablero(	
							X, O, X, L, O, 
							X, X, M, M, M, 
							M, O, L, X, L, 
							X, O, O, M, M);
		
		List<Point> segurasConExplosion = new ArrayList<Point>();
		segurasConExplosion.add(new Point(2,2));
		segurasConExplosion.add(new Point(3,2));
		segurasConExplosion.add(new Point(4,2));
		
		segurasConExplosion.add(new Point(0,2));
		segurasConExplosion.add(new Point(1,2));

		segurasConExplosion.add(new Point(1,0));
		segurasConExplosion.add(new Point(1,1));
		
		//pueden llegar a explotar tambien todas las demas, aunque no con seguridad
		return segurasConExplosion;
	}
	
}
