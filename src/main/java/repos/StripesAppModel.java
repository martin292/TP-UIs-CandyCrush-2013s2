package repos;

import java.util.List;

import objetivos.Objetivo;
import mundo.Jugador;
import mundo.Nivel;

public class StripesAppModel {

	protected Jugador jugador;
	protected Nivel nivel;
	protected Objetivo objetivo;
	
	protected List<Objetivo> objetivos;
	protected String filtroBusquedaNivel;
	protected List<Nivel> nivelesFiltrados;
	
	private static StripesAppModel instance = new StripesAppModel();
	
	
	public static synchronized StripesAppModel getInstance() {
		return instance;
	}

	
	public void resetear(){
		this.setNivelesFiltrados(null);
		this.setFiltroBusquedaNivel(null);
		this.setObjetivo(null);
	}


	public Jugador getJugador() {
		return jugador;
	}


	public void setJugador(Jugador jugador) {
		this.jugador = jugador;
	}


	public Nivel getNivel() {
		return nivel;
	}


	public void setNivel(Nivel nivel) {
		this.nivel = nivel;
	}


	public Objetivo getObjetivo() {
		return objetivo;
	}


	public void setObjetivo(Objetivo objetivo) {
		this.objetivo = objetivo;
	}


	public List<Objetivo> getObjetivos() {
		return objetivos;
	}


	public void setObjetivos(List<Objetivo> objetivos) {
		this.objetivos = objetivos;
	}


	public String getFiltroBusquedaNivel() {
		return filtroBusquedaNivel;
	}


	public void setFiltroBusquedaNivel(String filtroBusquedaNivel) {
		this.filtroBusquedaNivel = filtroBusquedaNivel;
	}


	public List<Nivel> getNivelesFiltrados() {
		return nivelesFiltrados;
	}


	public void setNivelesFiltrados(List<Nivel> nivelesFiltrados) {
		this.nivelesFiltrados = nivelesFiltrados;
	}

}
