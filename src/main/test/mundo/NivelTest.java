package mundo;

import static org.junit.Assert.*;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import mundo.Nivel;
import objetivos.Objetivo;

import org.junit.Test;

public class NivelTest {

	@Test
	public void testNivel() {
		String nombre = "Nombre";
		Point dim = new Point(10,12);
		int movimientos = 8;
		List<Objetivo> objetivos = new ArrayList<Objetivo>();
		Nivel nivel = new Nivel(nombre, dim, movimientos, objetivos);
		assertEquals(nombre, nivel.nombre);
		assertEquals(dim, nivel.dimensionTablero);
		assertEquals(movimientos, nivel.movimientosDisponibles);
		assertSame(objetivos, nivel.objetivos);
	}

}
