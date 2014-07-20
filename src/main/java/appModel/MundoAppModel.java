package appModel;

import java.util.ArrayList;
import java.util.List;

import mundo.Jugador;
import mundo.NivelParaJugar;
import dificultad.Dificultad;

public class MundoAppModel {

	private final Jugador jugador;

	private NivelParaJugar nivelSeleccionado;
	private String feedback;

	public MundoAppModel(Jugador jugador) {
		super();
		this.jugador = jugador;
	}
	
	public List<NivelParaJugar> nivelesDisponibles(){
		List<NivelParaJugar> niveles = new ArrayList<NivelParaJugar>();
		for(NivelParaJugar nivel : this.getJugador().getNivelesParaJugar()){
			if(nivel.isDisponible()){
				niveles.add(nivel);
			}
		}
		return niveles;
	}
	
	public boolean nivelSeleccionado(){
		boolean seleccionado = this.getNivelSeleccionado() != null;
		if(seleccionado) {
			this.setFeedback("");
		}
		else {
			this.setFeedback("Selecciona un nivel");
		}
		return seleccionado;
	}
	
	/** PRECONDICIÓN: nivelSeleccionado() = true */
	public PartidaAppModel armarPartida() {
		return new PartidaAppModel(this.getNivelSeleccionado(), 
					this.getJugador().getDificultadSeleccionada());
	}

	public void reiniciarJuego() {
		this.getJugador().reiniciarJuego();
	}

	public void seleccionarDificultadFacil() {
		this.getJugador().setDificultadSeleccionada(Dificultad.FACIL);
	}

	public void seleccionarDificultadMedia() {
		this.getJugador().setDificultadSeleccionada(Dificultad.MEDIO);
	}

	public void seleccionarDificultadDificil() {
		this.getJugador().setDificultadSeleccionada(Dificultad.DIFICIL);
	}

	// ****************************************************
	// * Accessors
	// ****************************************************
	public NivelParaJugar getNivelSeleccionado() {return nivelSeleccionado;}
	public void setNivelSeleccionado(NivelParaJugar nivelSeleccionado) 
			{this.nivelSeleccionado = nivelSeleccionado;}
	public String getFeedback() {return feedback;}
	public void setFeedback(String feedback) {this.feedback = feedback;}
	public Jugador getJugador() {return jugador;}

}
