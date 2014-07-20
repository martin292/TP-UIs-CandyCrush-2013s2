package mundo;

import java.awt.Point;
import java.io.Serializable;
import java.util.*;

import org.uqbar.commons.utils.Observable;
import objetivos.*;

@Observable
public class Nivel implements Serializable{
	
	// ****************************************************
	// * Variables
	// ****************************************************
	protected String nombre;
	protected Point dimensionTablero;
	protected int movimientosDisponibles;
	protected List<Objetivo> objetivos = new ArrayList<Objetivo>();
	
	// ****************************************************
	// * Constructores
	// ****************************************************
	/**
	 * @param nombre nombre del nivel
	 * @param dimensionTablero
	 * @param movimientosDisponibles
	 * @param objetivos
	 */
	public Nivel(String nombre, Point dimensionTablero,
			int movimientosDisponibles, List<Objetivo> objetivos) {
		super();
		this.nombre = nombre;
		this.dimensionTablero = dimensionTablero;
		this.movimientosDisponibles = movimientosDisponibles;
		this.objetivos = objetivos;
	}
	
	public Nivel(String nombre, int filas, int columnas,
			int movimientosDisponibles, List<Objetivo> objetivos) {
		this(nombre, new Point(columnas,filas), movimientosDisponibles, objetivos);
	}
	
	public Nivel() {
	}
	
	// ****************************************************
	// * Metodos
	// ****************************************************
	public void agregarObjetivo(Objetivo obj){
		this.objetivos.add(obj);
	}

	public void setFilas(int filas) {
		this.getDimensionTablero().setLocation(this.getDimensionTablero().x, filas - 1);
	}

	public void setColumnas(int columnas) {
		this.getDimensionTablero().setLocation(columnas - 1, this.getDimensionTablero().y);
	}
	
	public void eliminarObjetivo(String nombreObjetivo){
		Objetivo objetivoAEliminar = this.getObjetivos().get(0);
		for(Objetivo objetivo : this.getObjetivos()){
			if(objetivo.toString().equals(nombreObjetivo)){
				objetivoAEliminar = objetivo;
			}
		}
		this.getObjetivos().remove(objetivoAEliminar);
	}
	
	@Override
	public String toString(){
		return this.getNombre();
	}
	
	// ****************************************************
	// * Accessors
	// ****************************************************	
	public String getNombre() {return nombre;}
	public void setNombre(String nombre) {this.nombre = nombre;}
	public Point getDimensionTablero() {return dimensionTablero;}
	public void setDimensionTablero(Point dimensionTablero) 
		{this.dimensionTablero = dimensionTablero;}
	public int getMovimientosDisponibles() {return movimientosDisponibles;}
	public void setMovimientosDisponibles(int movimientosDisponibles) 
		{this.movimientosDisponibles = movimientosDisponibles;}
	public List<Objetivo> getObjetivos() {return objetivos;}
	public void setObjetivos(List<Objetivo> objetivos) {this.objetivos = objetivos;}
	
	// ----- Accessors adicionales ------
	public int getFilas()		{return this.getDimensionTablero().y;}
	public int getColumnas()	{return this.getDimensionTablero().x;}

}
