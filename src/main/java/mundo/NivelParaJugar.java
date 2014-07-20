package mundo;


import java.io.Serializable;

import org.uqbar.commons.utils.Observable;

import partida.PartidaGanadaException;
import partida.PartidaPerdidaException;
import partida.PartidaPersonalizadaGanadaException;

@Observable
public class NivelParaJugar implements Serializable{
	
	// ****************************************************
	// * Variables
	// ****************************************************
	protected int puntosObtenidos;
	protected Jugador jugador;
	protected Nivel nivel;
	
	protected boolean disponible;
	protected boolean completado;
	
	protected boolean personalizado;
	
	// ****************************************************
	// * Constructores
	// ****************************************************
	/**
	 * @param jugador jugador que tiene el nivel disponible
	 * @param nivel el nivel que esta disponible
	 */
	public NivelParaJugar(Jugador jugador, Nivel nivel) {
		this.puntosObtenidos = 0;
		this.jugador = jugador;
		this.nivel = nivel;
		this.disponible = false;
		this.completado = false;
		this.personalizado = false;
	}
	
	public NivelParaJugar(Jugador jugador, Nivel nivel, boolean personalizado) {
		this(jugador, nivel);
		this.personalizado = personalizado;
	}
	
	public NivelParaJugar() {
	}
	
	// ****************************************************
	// * Metodos
	// ****************************************************
	/** 
	 * Se considerara al nivel como ganado
	 */
	public void ganar(){
		if(! this.isPersonalizado())
		{	if(! this.isCompletado())
			{	this.setCompletado(true);
				this.getJugador().habilitarNuevoNivelParaJugar(this);
			}
			throw new PartidaGanadaException("partida ganada");
		}
		else
		{	throw new PartidaPersonalizadaGanadaException("partida ganada");
		}
	}
	
	public void perder(){
		throw new PartidaPerdidaException("partida perdida");
	}
	
	/**
	 * Vuelve los datos actuales a sus valores iniciales
	 */
	public void reiniciar(){
		this.setPuntosObtenidos(0);
		this.setDisponible(false);
		this.setCompletado(false);
	}
	
	public NivelParaJugar siguienteNivel(){
		return this.getJugador().siguienteNivel(this);
	}
	
	@Override
	public String toString(){
		return this.getNivel().toString();
	}
	
	// ****************************************************
	// * Accessors
	// ****************************************************
	public int getPuntosObtenidos() {return puntosObtenidos;}
	public void setPuntosObtenidos(int puntosObtenidos) {this.puntosObtenidos = puntosObtenidos;}
	public Jugador getJugador() {return jugador;}
	public void setJugador(Jugador jugador) {this.jugador = jugador;}
	public Nivel getNivel() {return nivel;}
	public void setNivel(Nivel nivel) {this.nivel = nivel;}
	public boolean isCompletado() {return completado;}
	public void setCompletado(boolean completado) {this.completado = completado;}
	public boolean isDisponible() {return disponible;}
	public void setDisponible(boolean disponible) {this.disponible = disponible;}
	public boolean isPersonalizado() {return personalizado;}
	public void setPersonalizado(boolean personalizado) {this.personalizado = personalizado;}
	
}
