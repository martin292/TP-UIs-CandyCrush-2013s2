package mundo;

import java.io.Serializable;
import java.util.*;

import org.uqbar.commons.utils.Observable;

import dificultad.Dificultad;

@Observable
public class Jugador implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// ****************************************************
	// * Variables
	// ****************************************************
	protected String nombre;
	protected int puntaje;
	protected List<NivelParaJugar> nivelesParaJugar;
	
	protected int primerNivelParaJugar;
	protected Dificultad dificultadSeleccionada = Dificultad.FACIL;
	
	protected List<Nivel> nivelesPropios;
	
	// ****************************************************
	// * Constructores
	// ****************************************************
	/**
	 * @param nombre nombre del jugador
	 * @param primerNivel primer nivel disponible a jugarse
	 */
	public Jugador(String nombre) {
		super();
		this.nombre = nombre;
		this.puntaje = 0;
		this.nivelesParaJugar = new ArrayList<NivelParaJugar>();
		this.nivelesPropios = new ArrayList<Nivel>();
	}
	
	public Jugador(String nombre, List<Nivel> niveles) {
		this(nombre);
		this.agregarNiveles(niveles);
		this.primerNivelParaJugar = 0;
	}
	
	// ****************************************************
	// * Metodos
	// ****************************************************
	/**
	 * Reinicia el juego, deshabilitando todos los niveles posteriores al primero.
	 * Se eliminan todos los puntos obtenidos
	 * No se eliminan los niveles adicionales creados
	 */
	public void reiniciarJuego(){
		this.setPuntaje(0);
		this.setPrimerNivelParaJugar(0);
		for(NivelParaJugar nivel : this.getNivelesParaJugar())
		{	nivel.reiniciar();
		}
		this.getNivelesParaJugar().get(0).setDisponible(true);
	}
	
	/** Se habilita en la lista de niveles para jugar el siguiente nivel
	 * 		correspondiente al parametro
	 * @param nivelPasado
	 */
	public void habilitarNuevoNivelParaJugar(NivelParaJugar nivelPasado){
		if(this.getPrimerNivelParaJugar() + 1 < this.getNivelesParaJugar().size())
		{	this.setPrimerNivelParaJugar(1 + this.getPrimerNivelParaJugar());
			this.getNivelesParaJugar().get(this.getPrimerNivelParaJugar()).setDisponible(true);
		}
		else
		{	if(! nivelPasado.isPersonalizado())
			{	throw new JuegoTerminadoException("juego terminado");
			}
		}
	}
	
	public NivelParaJugar ultimoNivelDisponible(){
		return this.getNivelesParaJugar().get(this.getPrimerNivelParaJugar());
	}
	
	public void actualizarPuntaje(){
		int puntaje = 0;
		for(NivelParaJugar nivel : this.getNivelesParaJugar())
		{	puntaje += nivel.getPuntosObtenidos();
		}
		this.setPuntaje(puntaje);
	}
	
	public NivelParaJugar siguienteNivel(NivelParaJugar nivelParaJugar){
		boolean found = false;
		NivelParaJugar nivelSiguiente = this.getNivelesParaJugar().get(0);
		for(int i = 0; ! found; i++)
		{	found = this.getNivelesParaJugar().get(i).getNivel() ==
					nivelParaJugar.getNivel();
			if(found)
			{	nivelSiguiente = this.getNivelesParaJugar().get(i+1);
			}
		}
		return nivelSiguiente;
	}
	
	public void agregarNiveles(List<Nivel> niveles){
		for(Nivel nivel : niveles)
			{	this.getNivelesParaJugar().add(new NivelParaJugar(this, nivel));
			}
		this.getNivelesParaJugar().get(0).setDisponible(true);
	}
	
	public boolean comparar(String nom) {
		return this.getNombre().equals(nom);
	}
	
	public void eliminarNivelPropio(String nombreNivel){
		Nivel nivelAEliminar = this.getNivelesPropios().get(0);
		for(Nivel nivel : this.getNivelesPropios()){
			if(nivel.getNombre().equals(nombreNivel)){
				nivelAEliminar = nivel;
			}
		}
		this.getNivelesPropios().remove(nivelAEliminar);
	}
	
	//PRECONDICIÓN: Que el nivel exista
	public Nivel getNivelPropio(String nombreNivel){
		Nivel n = this.getNivelesPropios().get(0);
		for(Nivel nivel : this.getNivelesPropios()){
			if(nivel.getNombre().equals(nombreNivel)){
				n = nivel;
			}
		}
		return n;
	}
	
	public void agregarNivel(Nivel nivel){
		this.getNivelesPropios().add(nivel);
		this.getNivelesParaJugar().add(new NivelParaJugar(this, nivel));
	}
	
	public String toString(){
		return this.getNombre();
	}
	
	// ****************************************************
	// * Accessors
	// ****************************************************	
	public String getNombre() {return nombre;}
	public void setNombre(String nombre) {this.nombre = nombre;}
	public int getPuntaje() {return puntaje;}
	public void setPuntaje(int puntaje) {this.puntaje = puntaje;}
	public List<NivelParaJugar> getNivelesParaJugar() {return nivelesParaJugar;}
	public void setNivelesParaJugar(List<NivelParaJugar> nivelesDisponibles) 
		{this.nivelesParaJugar = nivelesDisponibles;}
	public int getPrimerNivelParaJugar() {return primerNivelParaJugar;}
	public void setPrimerNivelParaJugar(int primerNivelParaJugar) {
		this.primerNivelParaJugar = primerNivelParaJugar;}
	public Dificultad getDificultadSeleccionada() {return dificultadSeleccionada;}
	public void setDificultadSeleccionada(Dificultad dificultadSeleccionada) {
		this.dificultadSeleccionada = dificultadSeleccionada;}
	public List<Nivel> getNivelesPropios() {return nivelesPropios;}
	public void setNivelesPropios(List<Nivel> nivelesPropios) {this.nivelesPropios = nivelesPropios;}
	
}
