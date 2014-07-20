package partida;

import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import mundo.Nivel;
import mundo.NivelParaJugar;

import org.uqbar.commons.model.UserException;
import org.uqbar.commons.utils.Observable;

import dificultad.Dificultad;
import direcciones.Direccion;
import explosiones.Explosion;

import objetivos.Objetivo;
import objetivos.ObjetivoParaCumplir;

@Observable
public class Partida implements Serializable{
	
	// ****************************************************
	// * Variables
	// ****************************************************
	protected int movimientosRestantes;
	protected int puntosAlcanzados;
	protected Tablero tablero;
	protected NivelParaJugar nivelParaJugar;
	protected List<ObjetivoParaCumplir> objetivos;
	
	protected int fila = 0;
	protected int columna = 0;
	protected boolean seleccion = false;
	
	protected String representacionVisual;
	protected String logMovimiento;
	
	protected boolean puedeMover = true;
	
	protected Dificultad dificultad;
	
	// ****************************************************
	// * Constructores
	// ****************************************************
	/**
	 * @param nivelParaJugar nivel a jugarse
	 * @param dificultad dificultad seleccionada
	 */
	public Partida(NivelParaJugar nivelParaJugar, Dificultad dificultad) {
		super();
		this.dificultad = dificultad;
		this.nivelParaJugar = nivelParaJugar;
		Nivel nivel = this.nivelParaJugar.getNivel();
		this.puntosAlcanzados = 0;
		this.movimientosRestantes = nivel.getMovimientosDisponibles();
		this.tablero = new Tablero(this, nivel.getDimensionTablero(), dificultad);
		this.objetivos = new ArrayList<ObjetivoParaCumplir>();
		for(Objetivo objetivo : nivel.getObjetivos())
			this.objetivos.add(new ObjetivoParaCumplir(this, objetivo));
		this.representacionVisual = this.tablero.representacionVisual();
		this.limpiarLogMovimiento();
		this.puedeMover = true;
	}
	
	public Partida(){
		super();
	}
	
	// ****************************************************
	// * Metodos
	// ****************************************************
	protected void resetearEleccion(){
		this.setSeleccion(false);
	}
	
	protected boolean sinEleccion(){
		return ! this.isSeleccion();
	}
	
	public String nombreDulceEn(int columna, int fila){
		Point celda = new Point(columna, fila);
		return this.getTablero().dulceEn(celda).toString();
	}
	
	/**
	 * @param fila
	 * @param columna
	 * @return si la celda a la que apuntan las variables de instancia
	 * es adyacente a la celda apuntada por las variables
	 */
	public boolean esCeldaAdyacente(int columna, int fila){
		return this.getTablero().esCeldaAdyacente(new Point(columna, fila),
				new Point(this.getColumna(), this.getFila()));
	}
	
	/**
	 * intercambia la celda indicada en las variables de instancia
	 * con la celda indicada en los parametros
	 * @param celdaDestino
	 * @param columnaDestino
	 */
	public void realizarMovimiento(int columnaDestino, int filaDestino){
		this.setPuedeMover(false);
		this.limpiarLogMovimiento();
		Point celda1 = new Point(this.getColumna(), this.getFila());
		Point celda2 = new Point(columnaDestino, filaDestino);
		this.realizarIntercambio(celda1, celda2);
		this.setPuedeMover(true);
	}
	
	public void limpiarLogMovimiento(){
		this.setLogMovimiento("");
	}
	
	public void elegirCelda(Celda celda){
		this.elegirCelda(celda.COLUMNA, celda.FILA);
	}
	
	public void elegirCelda(int columna, int fila){
		if(this.sinEleccion())
		{	this.setFila(fila);
			this.setColumna(columna);
			this.setSeleccion(true);
		}
		else
		{	if( this.esCeldaAdyacente(columna, fila))
			{	this.realizarMovimiento(columna, fila);
				this.resetearEleccion();
			}
			else
			{	this.setFila(fila);
				this.setColumna(columna);
				this.setSeleccion(true);
			}
		}
	}
	
	/**
	 * @return cantidad de filas del tablero
	 */
	public int filasTablero(){
		return 1 + this.getTablero().getDimension().y;
	}
	
	/**
	 * @return cantidad de columnas del tablero
	 */
	public int columnasTablero(){
		return 1 + this.getTablero().getDimension().x;
	}

	/** Agrega puntos
	 * @param cantPuntos cantidad de puntos a sumarse
	 */
	public void sumarPuntos(int cantPuntos){
		this.setPuntosAlcanzados(cantPuntos + this.getPuntosAlcanzados());
	}
	
	/** Revisa las condiciones de victoria
	 * @return si la partida ha sido ganada
	 */
	public boolean esPartidaGanada(){
		boolean ganada = true;
		for(int i = 0; ganada && i < this.getObjetivos().size(); i++)
			ganada = this.getObjetivos().get(i).objetivoRealizado();
		return ganada;
	}
	
	
	/**
	 * Declara la partida ganada si es que se ha ganado
	 */
	public void terminar() throws UserException {
		if(this.getPuntosAlcanzados() > this.getNivelParaJugar().getPuntosObtenidos())
		{	this.getNivelParaJugar().setPuntosObtenidos(this.getPuntosAlcanzados());
			this.getNivelParaJugar().getJugador().actualizarPuntaje();
		}
		if(this.esPartidaGanada())
		{	this.getNivelParaJugar().ganar();
		}
		else
		{	this.getNivelParaJugar().perder();
		}
	}
	
	/** Notifica de la explosion a los objetivos para que se actualicen
	 * y suma los puntos de la explosion
	 * @param explosion explosion realizada
	 */
	public void agregarExplosion(Explosion explosion){
		this.sumarPuntos(explosion.puntos());
		this.agregarAlLog(explosion);
		for(ObjetivoParaCumplir objetivo : this.getObjetivos())
			objetivo.chequearRealizacion(explosion);
	}
	
	public void agregarAlLog(Explosion explosion){
		this.setLogMovimiento(this.getLogMovimiento() + "BOOM! " + explosion + " " + 
				"puntos: " + explosion.puntos() + "\n");
	}
	
	public void agregarAlLog(Objetivo objetivo){
		this.setLogMovimiento(this.getLogMovimiento() + "\"" + objetivo + "\" completado!\n");
	}
	
	/** Da la orden de mover el dulce de una ubicacion en una direccion
	 * @param point ubicacion a mover
	 * @param direccion direccion a mover
	 */
	public void realizarMovimiento(Point point, Direccion direccion){
		if(this.getTablero().moverCeldaA(point, direccion))
		{	this.registrarMovimiento();
		}
	}
	
	/**
	 * Da la orden de intercambiar las celdas
	 * PRECONDICION: Las celdas deben encontrarse en el tablero
	 * @param celda1
	 * @param celda2
	 */
	public void realizarIntercambio(Point celda1, Point celda2){
		if(this.getTablero().moverCeldas(celda1, celda2))
		{	this.registrarMovimiento();
		}
	}
	
	public void registrarMovimiento(){
		this.decrementarMovimiento();
		if(this.sinMovimientosRestantes())
		{	this.terminar();
		}
	}
	
	public boolean sinMovimientosRestantes(){
		return this.getMovimientosRestantes() == 0;
	}
	
	/**
	 * Resta en 1 la cantidad de movimientos restantes
	 */
	public void decrementarMovimiento(){
		this.setMovimientosRestantes(this.getMovimientosRestantes() - 1);
	}
	
	/**
	 * vuelve al estado inicial
	 */
	public void reiniciar(){
		Nivel nivel = this.getNivelParaJugar().getNivel();
		this.setPuntosAlcanzados(0);
		this.setMovimientosRestantes(nivel.getMovimientosDisponibles());
		this.getTablero().reiniciar();
		List<ObjetivoParaCumplir> objetivos = new ArrayList<ObjetivoParaCumplir>();
		for(Objetivo objetivo : nivel.getObjetivos())
			objetivos.add(new ObjetivoParaCumplir(this, objetivo));
		this.setObjetivos(objetivos);
		this.setRepresentacionVisual(this.tablero.representacionVisual());
		this.limpiarLogMovimiento();
	}
	
	/**
	 * Arma una partida que se haya construido desde 0
	 * PRECONDICIÓN: Debe habérsele agregado la dificultad y NivelParaJugar
	 */
	public void armarPartida(){
		Nivel nivel = this.nivelParaJugar.getNivel();
		this.puntosAlcanzados = 0;
		this.movimientosRestantes = nivel.getMovimientosDisponibles();
		this.tablero = new Tablero(this, nivel.getDimensionTablero(), this.getDificultad());
		this.objetivos = new ArrayList<ObjetivoParaCumplir>();
		for(Objetivo objetivo : nivel.getObjetivos())
			this.objetivos.add(new ObjetivoParaCumplir(this, objetivo));
		this.representacionVisual = this.tablero.representacionVisual();
		this.limpiarLogMovimiento();
		this.puedeMover = true;
	}
	
	// ****************************************************
	// * Accessors
	// ****************************************************	
	public int getMovimientosRestantes() {return movimientosRestantes;}
	public void setMovimientosRestantes(int movimientosRestantes) 
		{this.movimientosRestantes = movimientosRestantes;}
	public int getPuntosAlcanzados() {return puntosAlcanzados;}
	public void setPuntosAlcanzados(int puntosAlcanzados) 
		{this.puntosAlcanzados = puntosAlcanzados;}
	public Tablero getTablero() {return tablero;}
	public void setTablero(Tablero tablero) {this.tablero = tablero;}
	public List<ObjetivoParaCumplir> getObjetivos() {return objetivos;}
	public void setObjetivos(List<ObjetivoParaCumplir> objetivos) 
		{this.objetivos = objetivos;}
	public NivelParaJugar getNivelParaJugar() {return nivelParaJugar;}
	public void setNivelParaJugar(NivelParaJugar nivelParaJugar) 
		{this.nivelParaJugar = nivelParaJugar;}
	
	public boolean isSeleccion() {return seleccion;}
	public int getFila() {return fila;}
	public int getColumna() {return columna;}
	public void setSeleccion(boolean seleccion) {this.seleccion = seleccion;}
	public void setFila(int fila) {this.fila = fila;}
	public void setColumna(int columna) {this.columna = columna;}

	public String getLogMovimiento() {return logMovimiento;}
	public void setLogMovimiento(String logMovimiento) {this.logMovimiento = logMovimiento;}

	public boolean isPuedeMover() {return puedeMover;}
	public void setPuedeMover(boolean puedeMover) {this.puedeMover = puedeMover;}

	public Dificultad getDificultad() {return dificultad;}
	public void setDificultad(Dificultad dificultad) {this.dificultad = dificultad;}
	
	public String getRepresentacionVisual() {return representacionVisual;}
	public void setRepresentacionVisual(String rv) {this.representacionVisual = rv;}
	
}
