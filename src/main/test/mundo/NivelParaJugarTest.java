package mundo;

import static org.junit.Assert.*;

import mundo.Jugador;
import mundo.Nivel;
import mundo.NivelParaJugar;

import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;

public class NivelParaJugarTest {

	protected NivelParaJugar nivelDisp;
	
	@Before
	public void setUp() throws Exception {
		Jugador jugador = mock(Jugador.class);
		Nivel nivel = mock(Nivel.class);
		this.nivelDisp = new NivelParaJugar(jugador, nivel);
	}

	@Test
	public void testNivelDisponible() {
		Jugador jugador = mock(Jugador.class);
		Nivel nivel = mock(Nivel.class);
		this.nivelDisp = new NivelParaJugar(jugador, nivel);
		assertEquals(jugador, this.nivelDisp.getJugador());
		assertEquals(nivel, this.nivelDisp.getNivel());
		assertEquals(0, this.nivelDisp.getPuntosObtenidos());		
	}
	
	@Test
	public void testReiniciar(){
		Jugador jugador = this.nivelDisp.getJugador();
		Nivel nivel = this.nivelDisp.getNivel();
		this.nivelDisp.setPuntosObtenidos(230);
		
		this.nivelDisp.reiniciar();
		
		assertEquals(jugador, this.nivelDisp.getJugador());
		assertEquals(nivel, this.nivelDisp.getNivel());
		assertEquals(0, this.nivelDisp.getPuntosObtenidos());
	}
	

}
