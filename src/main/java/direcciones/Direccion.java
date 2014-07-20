package direcciones;

import java.awt.Point;
import java.util.*;

public abstract class Direccion {
	
	// ****************************************************
	// * Variables estaticas
	// ****************************************************
	public static final Arriba ARRIBA = new Arriba();
	public static final Abajo ABAJO = new Abajo();
	public static final Derecha DERECHA = new Derecha();
	public static final Izquierda IZQUIERDA = new Izquierda();
	
	protected static List<Direccion> direcciones = Arrays.asList(
			ARRIBA, ABAJO, DERECHA, IZQUIERDA);
	
	// ****************************************************
	// * Metodos
	// ****************************************************
	/**@param celda posicion a evaluar movimiento
	 * @param dimension tamaño del tablero a evaluar
	 * @return si puede mover hacia la direccion
	 */
	public abstract boolean puedeMover(Point point, Point dimension);
	
	/**@param point posicion desde la cual se obtendra una nueva
	 * @return un nuevo Punto, 1 unidad hacia la direccion
	 */
	public abstract Point puntoEnDireccion(Point point);
	
	/**
	 * @return la Direccion opuesta
	 */
	public abstract Direccion opuesta();
	
	/**
	 * @param point Desde donde devolver los puntos
	 * @param cantidad de puntos a devolver
	 * @return cantidad de puntos en la direccion
	 */
	public List<Point> puntosA(Point point, int cantidad) {
		List<Point> puntos = new ArrayList<Point>();
		Point celdaActual = point;
		for(int i = 0; i < cantidad; i++)
		{	celdaActual = this.puntoEnDireccion(celdaActual);
			puntos.add(celdaActual);
		}
		return puntos;
	}
	
	/**
	 * @return si la direccion es Arriba
	 */
	public boolean esArriba(){
		return false;
	}

	// ****************************************************
	// * Accessors
	// ****************************************************	
	public static List<Direccion> getDirecciones() {return direcciones;}
	
}
