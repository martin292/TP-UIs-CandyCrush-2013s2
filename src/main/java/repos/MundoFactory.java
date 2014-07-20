package repos;

import java.awt.Point;
import java.util.*;

import dulces.Dulce;
import objetivos.ExplosionesPorColor;
import objetivos.GrandesExplosiones;
import objetivos.Objetivo;
import objetivos.Regular;
import mundo.Jugador;
import mundo.Nivel;

public class MundoFactory {
	
	// ****************************************************
	// * Variables
	// ****************************************************
	protected List<Nivel> niveles;
	protected List<Jugador> jugadores = new ArrayList<Jugador>();
	protected Jugador jugadorSeleccionado;
	protected Nivel nivelSeleccionado;
	private static MundoFactory instance = new MundoFactory();
	
	public static synchronized MundoFactory getInstance() {
		return instance;
	}
	
	// ****************************************************
	// * Constructores
	// ****************************************************
	public MundoFactory(){
		super();
		this.crearMundo();
	}

	// ****************************************************
	// * Metodos
	// ****************************************************
	public void crearMundo(){
		this.crearNiveles();
		this.crearJugadores();
	}
	
	protected void crearNiveles(){
		List<Nivel> niveles = new ArrayList<Nivel>();
		niveles.add(new Nivel("Bienvenido", new Point(4,4), 2,
				this.agregarRegular(50, 60, new ArrayList<Objetivo>())));
		niveles.add(new Nivel("Empezemos", new Point(5,4), 5,
				this.agregarRegular(70, 120, new ArrayList<Objetivo>())));
		niveles.add(new Nivel("Calentando", new Point(5,5), 7,
				this.agregarExplosionPorColor(100, Dulce.VERDE, 2,
				this.agregarGranExplosion(150, Dulce.ROJO, 4, 
				this.agregarRegular(50, 80, new ArrayList<Objetivo>())))));
		niveles.add(new Nivel("Accion", new Point(6,6), 1,
				this.agregarRegular(50, 30, new ArrayList<Objetivo>())));
		this.setNiveles(niveles);
	}
	
	protected void crearJugadores(){
		Jugador jugador = new Jugador("Jorge", this.getNiveles());
		jugador.setNivelesPropios(this.getNiveles());
		List<Jugador> jugadores = new ArrayList<Jugador>();
		jugadores.add(jugador);
		this.setJugadores(jugadores);
	}
	
	protected List<Objetivo> agregarRegular(
			int puntos, int meta, List<Objetivo> objetivos){
		objetivos.add(new Regular(puntos, meta));
		return objetivos;
	}
	
	protected List<Objetivo> agregarExplosionPorColor(
			int puntos, Dulce dulce, int meta, List<Objetivo> objetivos){
		objetivos.add(new ExplosionesPorColor(puntos, dulce, meta));
		return objetivos;
	}
	
	protected List<Objetivo> agregarGranExplosion(
			int puntos, Dulce dulce, int magnitud, List<Objetivo> objetivos){
		objetivos.add(new GrandesExplosiones(puntos, dulce, magnitud));
		return objetivos;
	}
	
	public void agregarNivel(Nivel nivel){
		this.getNiveles().add(nivel);
	}
	
	public Boolean buscarUsuario(String nom) {
		
		boolean ret = false;
		
		for(Jugador j: this.getJugadores()){
			ret = ret || j.comparar(nom);
		}
		
		return ret;
	}

	public void agregarJugador(Jugador nuevo) {
		this.getJugadores().add(nuevo);
	}
	
	public Jugador retJugador(String nom) {
		
		for(Jugador j: this.getJugadores()){
			if(j.comparar(nom)){
				return j;
			}
		}
		
		return null;
	}
	
	// ****************************************************
	// * Accessors
	// ****************************************************	
	public List<Nivel> getNiveles() {return niveles;}
	public void setNiveles(List<Nivel> niveles) {this.niveles = niveles;}
	public List<Jugador> getJugadores() {return jugadores;}
	public void setJugadores(List<Jugador> jugadores) {this.jugadores = jugadores;}
	
	public Jugador getJugadorSeleccionado() {
		return jugadorSeleccionado;
	}
	public void setJugadorSeleccionado(Jugador jugadorSeleccionado) {
		this.jugadorSeleccionado = jugadorSeleccionado;
	}

	public Nivel getNivelSeleccionado() {
		return nivelSeleccionado;
	}

	public void setNivelSeleccionado(Nivel nivelSeleccionado) {
		this.nivelSeleccionado = nivelSeleccionado;
	}
	
}
