package appModel;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import mundo.Nivel;

import org.uqbar.commons.model.ObservableUtils;
import org.uqbar.commons.utils.Observable;

import dificultad.Dificultad;
import dulces.Dulce;

import objetivos.Objetivo;
import repos.RepositorioDeNiveles;

@Observable
public class AdministradorDeNiveles {
	
	// *****************************************************************
	// * VARIABLES
	// *****************************************************************
	
	private String nombre;
	private int filas;
	private int columnas;
	private int cantMovimientos;
	private Objetivo objSeleccionado;
	private List<Objetivo> objetivos = new ArrayList<Objetivo>();
	private List<Nivel> niveles = new ArrayList<Nivel>();
	private Nivel nivelSeleccionado;	
	
	private Dulce color;
	private int cantidad;
	
	private Dificultad dificultad = Dificultad.MEDIO;
		
	// *****************************************************************
	// * Contructor
	// *****************************************************************
	public AdministradorDeNiveles(){
		this.setNivelSeleccionado(null);
		this.actualizar();
	}

	// *****************************************************************
	// * METODOS
	// *****************************************************************

	/**
	 * Crea un nuevo nivel y le setea algunos parametros
	 * selecciona el nuevo nivel
	 * lo agrega al repositorio de niveles
	 * actualiza la lista de niveles	 * 
	 */
	
	public void agregarNivel() {
		Nivel nuevoNivel = new Nivel();
		
		nuevoNivel.setNombre("");
		nuevoNivel.setDimensionTablero(new Point(0,0));
		nuevoNivel.setMovimientosDisponibles(0);
		
		this.setNivelSeleccionado(nuevoNivel);
		
		this.setCantMovimientos(this.nivelSeleccionado.getMovimientosDisponibles());
		this.setColumnas(this.nivelSeleccionado.getDimensionTablero().x);
		this.setFilas(this.nivelSeleccionado.getDimensionTablero().y);
		this.setNombre(this.nivelSeleccionado.getNombre());
		this.actualizarObjetivos();
		this.setObjSeleccionado(null);
		
		RepositorioDeNiveles.getInstance().create(nuevoNivel);
		this.actualizar();
	}
	
	/**
	 * setea las variables (nombre, filas, columnas, cantMovimientos, objetivos) con los datos del nivel seleccionado
	 */
	public void editarNivel() {
		this.setNombre(this.nivelSeleccionado.getNombre());
		this.setFilas(this.nivelSeleccionado.getDimensionTablero().y);
		this.setColumnas(this.nivelSeleccionado.getDimensionTablero().x);
		this.setCantMovimientos(this.nivelSeleccionado.getMovimientosDisponibles());
		this.setObjetivos(this.nivelSeleccionado.getObjetivos());
		this.setObjSeleccionado(null);
		
		ObservableUtils.firePropertyChanged(this, "niveles", this.getNiveles());
	}
	
	/**
	 * elimina el nivel seleccionado del repositorio de niveles
	 */
	public void borrarNivel() {
		RepositorioDeNiveles.getInstance().delete(this.getNivelSeleccionado());
		this.setNivelSeleccionado(null);
		 this.actualizar();
		 ObservableUtils.firePropertyChanged(this, "niveles", this.getNiveles());
	}
		
	/**
	 * borra el objetivo seleccionado
	 */
	public void borrarObjetivo(){
		this.setCantidad(0);
		this.getNivelSeleccionado().getObjetivos().remove(this.getObjSeleccionado());
		this.setObjSeleccionado(null);
		this.actualizarObjetivos();
		ObservableUtils.firePropertyChanged(this, "objetivos", this.getObjetivos());
		ObservableUtils.firePropertyChanged(this, "nivelValido", this.isObjetivoValido());
	}
	
	/**
	 * setea las varables color cantidad con los valores del objetivo seleccionado
	 */
	public void editarObjetivo() {
		this.setColor(this.objSeleccionado.getColor());
		this.setCantidad(this.objSeleccionado.getCantidad());
	}
	
	/**
	 * Actualiza la lista de niveles
	 */
	public void actualizar(){
		RepositorioDeNiveles.getInstance().validarNiveles();
		this.setNiveles(RepositorioDeNiveles.getInstance().retNiveles());
	}
	
	
	/**
	 * selecciona un nuevo objetivo
	 * @param obj
	 */
	public void seleccionarNuevoObjetivo(Objetivo obj){
		this.setObjSeleccionado(obj);
		this.getNivelSeleccionado().agregarObjetivo(obj);
	}
	
	/**
	 * actualiza la lista de objetivos
	 */
	public void actualizarObjetivos(){
		this.setObjetivos(this.nivelSeleccionado.getObjetivos());
	}	
	
	public boolean isObjetivoValido(){
		return this.getCantidad() > 0 && this.getColor() != null;
	}
	
	public boolean isNivelValido(){
		return this.getFilas() > 2 && this.getColumnas() > 2 &&
				this.getCantMovimientos() > 0 && ! this.getObjetivos().isEmpty() &&
				! this.getNombre().trim().equals("");
	}

	// *****************************************************************
	// * ACCESSORS
	// *****************************************************************
	public Dificultad getDificultad() {
		return dificultad;
	}
	public void setDificultad(Dificultad dificultad) {
		this.dificultad = dificultad;
	}	
	public Dulce getColor() {
		return color;
	}
	public void setColor(Dulce color) {
		this.color = color;
		this.objSeleccionado.setDulce(this.color);
		ObservableUtils.firePropertyChanged(this, "objetivoValido", this.isObjetivoValido());
	}
	public int getCantidad() {
		return cantidad;
	}
	public void setCantidad(int cant) {
		this.cantidad = cant;
		this.objSeleccionado.agregarCantidad(this.cantidad);
		ObservableUtils.firePropertyChanged(this, "objetivoValido", this.isObjetivoValido());
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
		this.getNivelSeleccionado().setNombre(nombre);
		ObservableUtils.firePropertyChanged(this, "nivelValido", this.isNivelValido());
	}
	public int getFilas() {
		return filas;
	}
	public void setFilas(int filas) {
		this.filas = filas;
		ObservableUtils.firePropertyChanged(this, "nivelValido", this.isNivelValido());
		this.nivelSeleccionado.setFilas(filas);
	}
	public int getColumnas() {
		return columnas;
	}
	public void setColumnas(int columnas) {
		this.columnas = columnas;
		ObservableUtils.firePropertyChanged(this, "nivelValido", this.isNivelValido());
		this.nivelSeleccionado.setColumnas(columnas);
	}
	public int getCantMovimientos() {
		return cantMovimientos;
	}
	public void setCantMovimientos(int cantMovimientos) {
		this.cantMovimientos = cantMovimientos;
		ObservableUtils.firePropertyChanged(this, "nivelValido", this.isNivelValido());
		this.nivelSeleccionado.setMovimientosDisponibles(cantMovimientos);
	}
	public Objetivo getObjSeleccionado() {
		return objSeleccionado;
	}
	public void setObjSeleccionado(Objetivo objSeleccionado) {
		this.objSeleccionado = objSeleccionado;
	}
	public List<Objetivo> getObjetivos() {
		return objetivos;
	}
	public void setObjetivos(List<Objetivo> objetivos) {
		this.objetivos = objetivos;
		ObservableUtils.firePropertyChanged(this, "nivelValido", this.isNivelValido());
	}
	public List<Nivel> getNiveles() {
		return niveles;
	}
	public void setNiveles(List<Nivel> niveles) {
		this.niveles = niveles;
	}
	public Nivel getNivelSeleccionado() {
		return nivelSeleccionado;
	}
	public void setNivelSeleccionado(Nivel nivelSeleccionado) {
		this.nivelSeleccionado = nivelSeleccionado;
	}
	
}

