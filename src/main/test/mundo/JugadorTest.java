package mundo;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;


import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;

public class JugadorTest {

	protected Jugador jugador;
	
	@Before
	public void setUp() throws Exception {
		this.jugador = new Jugador("Jorge");
	}

	@Test
	public void testJugador() {
		String nombre = "Jose";
		this.jugador = new Jugador(nombre);
		assertEquals(nombre, this.jugador.nombre);
		assertEquals(0, this.jugador.puntaje);
	}
	
	@Test
	public void testReiniciarJuego_MismoJugadorConSoloElPrimerNivelDisp() {
		String nombre = "nombre";
		this.jugador = new Jugador(nombre);
		this.jugador.setPuntaje(1525);
		assertEquals(1525, this.jugador.getPuntaje());
		
		List<NivelParaJugar> niveles = new ArrayList<NivelParaJugar>();
		NivelParaJugar nivel = mock(NivelParaJugar.class);
		niveles.add(nivel);
		this.jugador.setNivelesParaJugar(niveles);
		
		this.jugador.reiniciarJuego();
		assertEquals(0, this.jugador.getPuntaje());
		assertEquals(nombre, this.jugador.getNombre());
		assertEquals(1, this.jugador.getNivelesParaJugar().size());
	}
	
	@Test
	public void testReiniciarJuego_SeReiniciaElPrimerNivelDisponible() {
		NivelParaJugar primerNivelDisp = mock(NivelParaJugar.class);
		List<NivelParaJugar> nivelesDisp = new ArrayList<NivelParaJugar>();
		nivelesDisp.add(primerNivelDisp);
		this.jugador.setNivelesParaJugar(nivelesDisp);
		this.jugador.reiniciarJuego();
		verify(primerNivelDisp).reiniciar();
	}

}
