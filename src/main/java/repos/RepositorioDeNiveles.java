package repos;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import mundo.Nivel;

import org.uqbar.commons.utils.Observable;

import dulces.Dulce;
import objetivos.ExplosionesPorColor;
import objetivos.GrandesExplosiones;
import objetivos.Objetivo;

@Observable
public class RepositorioDeNiveles  { 

	// *****************************************************************
	// * VARIABLES
	// *****************************************************************
	
	private List<Nivel> niveles = new ArrayList<Nivel>();
	private static RepositorioDeNiveles instance;
	
	public static synchronized RepositorioDeNiveles getInstance() {
		if (instance == null) {
			instance = new RepositorioDeNiveles();
		}
		return instance;
	}
	
	// *****************************************************************
	// * CONSTRUCTOR
	// *****************************************************************
	public RepositorioDeNiveles(){
		//Nivel 1 Chupetin de manzana
		ExplosionesPorColor obj1Nivel1 = new ExplosionesPorColor(Dulce.ROJO, 5);
		List<Objetivo> l1 = new ArrayList<Objetivo>();
		l1.add(obj1Nivel1);
		
		Nivel nivel1 = new Nivel("Chupetin de manzana", new Point(5, 5), 100, l1);
		
		//Nivel 2 Chocolate con almendras
		ExplosionesPorColor obj1Nivel2 = new ExplosionesPorColor(Dulce.AMARILLO, 6);
		GrandesExplosiones obj2Nivel2 = new GrandesExplosiones (Dulce.CELESTE, 5);
		List<Objetivo> l2 = new ArrayList<Objetivo>();
		l2.add(obj1Nivel2);
		l2.add(obj2Nivel2);
		
		Nivel nivel2 = new Nivel("Chocolate con almendras", new Point(6, 6), 90, l2);
		
		//Nivel 3 Malvaviscos
		ExplosionesPorColor obj1Nivel3 = new ExplosionesPorColor(Dulce.VERDE, 6);
		GrandesExplosiones obj2Nivel3 = new GrandesExplosiones (Dulce.NARANJA, 5);
		GrandesExplosiones obj3Nivel3 = new GrandesExplosiones (Dulce.VIOLETA, 6);
		List<Objetivo> l3 = new ArrayList<Objetivo>();
		l3.add(obj1Nivel3);
		l3.add(obj2Nivel3);
		l3.add(obj3Nivel3);
		
		Nivel nivel3 = new Nivel("Malvaviscos", new Point(8, 7), 50, l3);
		
		//Agregar niveles
		this.create(nivel1);
		this.create(nivel2);
		this.create(nivel3);
	}
	
	// *****************************************************************
	// * METODOS
	// *****************************************************************
	
	/**
	 * Agrega un nivel a la coleccion
	 * @param n
	 */
	public void create(Nivel n){
		this.niveles.add(n);
		//this.validarNivel(n);
	}
	
	/**
	 * borra un nivel de la coleccion
	 * @param n
	 */
	public void delete(Nivel n){
		this.niveles.remove(n);
	}
	
	/**
	 * retorna la lista de niveles
	 * @return
	 */
	public List<Nivel> retNiveles(){
		return this.niveles;
	}
	
	public void validarNiveles(){
		for(Nivel n: this.getNiveles()){
			this.validarNivel(n);
		}
	}
	
	public void validarNivel(Nivel n){
		n.setNombre(this.validarNombre(n.getNombre()));
		n.setDimensionTablero(this.validarDimension(n.getDimensionTablero()));
		n.setMovimientosDisponibles(this.validarMovimientos(n.getMovimientosDisponibles()));
		n.setObjetivos(this.validarObjetivos(n.getObjetivos()));
	}
	
	private List<Objetivo> validarObjetivos(List<Objetivo> obj) {
		
		List<Objetivo> ret = new ArrayList<Objetivo>();
		
		return obj.size() == 0 ? ret : obj;
	}
	private int validarMovimientos(int mov) {
		return mov > 10 ? mov : 100;
	}
	private Point validarDimension(Point dim) {		
		return dim.x >= 3 && dim.y >= 3 ? dim : new Point(3,3);
	}
	private String validarNombre(String nombre) {
		return nombre == null ? "Nuevo nivel" : nombre;
	}

	// *****************************************************************
	// * ACCESSORS
	// *****************************************************************
	public List<Nivel> getNiveles() {return niveles;}
	public void setNiveles(List<Nivel> niveles) {this.niveles = niveles;}
	public static void setInstance(RepositorioDeNiveles instance) {
		RepositorioDeNiveles.instance = instance;}
	
}
