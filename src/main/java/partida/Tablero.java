package partida;

import java.awt.Point;
import java.io.Serializable;
import java.util.*;


import dificultad.Dificultad;
import direcciones.*;
import dulces.Dulce;
import explosiones.*;

public class Tablero implements Serializable{

	// ****************************************************
	// * Variables
	// ****************************************************
	protected Point dimension;
	protected Dificultad dificultad;
	protected Partida partida;
	protected Celda[][] celdas;
	
	protected boolean enJuego;
	
	// ****************************************************
	// * Constructores
	// ****************************************************
	public Tablero(Partida partida, Point dimension, Dificultad dificultad){
		super();
		this.dimension = dimension;
		this.dificultad = dificultad;
		this.celdas = new Celda[dimension.y + 1][dimension.x + 1];
		for(int fila = 0; fila <= dimension.y; fila++)
		{	for(int columna = 0; columna <= dimension.x; columna++)
			{	this.celdas[fila][columna] = new Celda(fila, columna);
			}
		}
		this.partida = partida;
		this.enJuego = false;
		this.llenarTablero();
		this.enJuego = true;
	}
	
	
	// ****************************************************
	// * Metodos
	// ****************************************************
	/**
	 * @param celda
	 * @return Dulce en celda
	 */
	public Dulce dulceEn(Point point){
		return this.getCeldas()[point.y][point.x].getDulce();
		
	}
	
	public Celda getCelda(Point point){
		return this.getCeldas()[point.y][point.x];
	}
	
	public Celda getCelda(int columna, int fila){
		return this.getCeldas()[fila][columna];
	}
	
	/**
	 * Pone celda en punto
	 * @param dulce
	 * @param point
	 */
	protected void setCelda(Celda celda, Point point){
		this.getCeldas()[point.y][point.x] = celda;
	}
	
	/**
	 * Pone dulce en celda. Analogo a setCelda
	 * @param dulce
	 * @param celda
	 */
	protected void ponerDulceEn(Dulce dulce, Point point){
		this.getCeldas()[point.y][point.x].setDulce(dulce);
		this.esperarSiEnJuego(100);
	}
	
	/**
	 * @param celda
	 * @return si la celda por parametro esta sin dulce
	 */
	protected boolean esCeldaVacia(Point point){
		return this.getCelda(point).estaVacia();
	}
	
	/**
	 * Elimina el contenido de la celda
	 * @param celda a destruir
	 */
	protected void destruirCelda(Point point){
		this.getCelda(point).destruir();
	}
	
	/**
	 * @return Punto origen (inferior izquierdo) del tablero
	 */
	protected Point puntoOrigen(){
		return new Point(0,0);
	}

	/** 
	 * Como moverCeldas, pero dada una celda y una direccion. Se chequea
	 * que se pueda realizar el movimiento desde la celda en la direccion
	 * @param celda
	 * @param direccion
	 * @return
	 */
	public boolean moverCeldaA(Point celda, Direccion direccion) {
	//metodo no utilizado en el modelo actual
		boolean movimientoExitoso = 
				direccion.puedeMover(celda, this.getDimension());
		if(movimientoExitoso)
		{	Point nuevaPosicion = this.intercambiarCeldaA(celda, direccion);
			List <Point> celdasMovidas = new ArrayList<Point>();
			celdasMovidas.add(celda);
			celdasMovidas.add(nuevaPosicion);
			List <Point> celdasADestruir = 
					this.buscarYNotificarExplosionesDesde(celdasMovidas);
			if(celdasADestruir.isEmpty())
			{	this.intercambiarCeldas(nuevaPosicion, celda);
				movimientoExitoso = false;
			}
			else
			{	this.realizarExplosionesYNotificar(celdasADestruir, true);
			}
		}
		return movimientoExitoso;
	}
	
	/** Intercambia el contenido de las celdas y revisa si hay explosion en ellas.
	 * Si no hay, vuelve a intercambiar las celdas a su estado original.
	 * Si hay explosion, produce la misma. Si ambas celdas producen explosion, se
	 * efectuan al mismo tiempo pero se reportan como 2 explosiones separadas.
	 * Luego de efectuada la explosión, se bajan los dulces y se reponen en el tablero.
	 * A partir de aqui, se buscara una explosion y se la ira efectuando y reportando,
	 * bajando y reponiendo dulces por cada vez, asi hasta que no queden explosiones 
	 * por realizar. 
	 * PRECONDICION: celda1 y celda2 pertenecen al tablero
	 * @param celda1
	 * @param celda2
	 * @return si el movimiento ha producido explosion
	 */
	public boolean moverCeldas(Point celda1, Point celda2) {
		this.intercambiarCeldas(celda1, celda2);
		List <Point> celdasMovidas = new ArrayList<Point>();
		celdasMovidas.add(celda1);
		celdasMovidas.add(celda2);
		List <Point> celdasADestruir = 
				this.buscarYNotificarExplosionesDesde(celdasMovidas);
		boolean movimientoExitoso = ! celdasADestruir.isEmpty();
		if(movimientoExitoso)
		{	this.realizarExplosionesYNotificar(celdasADestruir, true);
		}
		else
		{	this.intercambiarCeldas(celda2, celda1);
		}
		return movimientoExitoso;
	}
	
	/**
	 * Intercambia el dulce de la celda con el que se encuentra en la
	 *  direccion dada
	 * @param celda
	 * @param direccion
	 * @return la posicion con la que se intercambio
	 */
	protected Point intercambiarCeldaA(Point celda, Direccion direccion){
		Point posicionAIntercambiar = direccion.puntoEnDireccion(celda);
		this.intercambiarCeldas(celda, posicionAIntercambiar);
		this.esperarSiEnJuego(40);
		return posicionAIntercambiar;
	}
	
	/**
	 * Intercambia los dulces de ambas celdas
	 * @param celda1
	 * @param celda2
	 */
	protected void intercambiarCeldas(Point celda1, Point celda2){
		Dulce dulceACambiar = this.dulceEn(celda2);
		this.ponerDulceEn(this.dulceEn(celda1), celda2);
		this.ponerDulceEn(dulceACambiar, celda1);
		this.esperarSiEnJuego(80);
	}
	
	/**
	 * Revisa si las celdas por parametro deben explotar y notifica
	 *  explosión en caso de producirse
	 * @param celdas de las 2 posiciones involucradas en un movimiento
	 * @return posiciones de las celdas de la explocion
	 * 	Puede retornar una lista vacia
	 */
	protected List<Point> buscarYNotificarExplosionesDesde(List<Point> celdas){
		List<Point> celdasDeExplosion = new ArrayList<Point>();
		for(Point celda : celdas)
		{	celdasDeExplosion.addAll(this.buscarYNotificarExplosionEn(celda));
		}
		return celdasDeExplosion;
	}
	
	/**
	 * Revisa si la celda forma parte de una explosion y notifica en
	 * caso afirmativo
	 * @param celda
	 * @return posiciones de las celdas de la explocion
	 */
	protected List<Point> buscarYNotificarExplosionEn(Point celda){
		List<Point> celdasDeExplosion = new ArrayList<Point>();
		int aciertosArriba = this.aciertosDesdeCeldaHacia(celda, Direccion.ARRIBA);
		int aciertosAbajo = this.aciertosDesdeCeldaHacia(celda, Direccion.ABAJO);
		int aciertosDerecha = this.aciertosDesdeCeldaHacia(celda, Direccion.DERECHA);
		int aciertosIzquierda = this.aciertosDesdeCeldaHacia(celda, Direccion.IZQUIERDA);
		
		if(aciertosArriba + aciertosAbajo > 1)
		{	celdasDeExplosion.addAll(Direccion.ABAJO.puntosA(celda, aciertosAbajo));
			celdasDeExplosion.addAll(Direccion.ARRIBA.puntosA(celda, aciertosArriba));
		}
		if(aciertosDerecha + aciertosIzquierda > 1)
		{	celdasDeExplosion.addAll(Direccion.IZQUIERDA.puntosA(celda, aciertosIzquierda));
			celdasDeExplosion.addAll(Direccion.DERECHA.puntosA(celda, aciertosDerecha));
		}
		
		boolean hayExplosion = ! celdasDeExplosion.isEmpty();
		if(hayExplosion)
		{	celdasDeExplosion.add(celda);
			Explosion explosion = this.crearExplosion(
					this.dulceEn(celda), celdasDeExplosion.size());
			this.getPartida().agregarExplosion(explosion);
		}
		return celdasDeExplosion;
	}
	
	/**
	 * @param celda
	 * @param direccion 
	 * @return la cantidad de celdas compatibles con el parametro en la 
	 * direccion dada
	 */
	protected int aciertosDesdeCeldaHacia(Point celda, Direccion direccion){
		int aciertos = 0;
		Point celdaAEvaluar = celda;
		boolean rachaAciertos = true;
		while(rachaAciertos && 
				direccion.puedeMover(celdaAEvaluar, this.getDimension()))
		{	celdaAEvaluar = direccion.puntoEnDireccion(celdaAEvaluar);
			rachaAciertos = this.dulceEn(celda).
					compatibleCon(this.dulceEn(celdaAEvaluar));
			if(rachaAciertos)	//se sigue en racha
			{	aciertos++;
			}
		}
		return aciertos;
	}
	
	/**
	 * @param dulce
	 * @param magnitud
	 * @return Una nueva explosion del tipo dado por los parametros
	 */
	protected Explosion crearExplosion(Dulce dulce, int magnitud){
		Explosion explosion;
		if(magnitud > 3)
		{	explosion = new GranExplosion(dulce, magnitud);
		}
		else
		{	explosion = new Explosion(dulce);
		}
		return explosion;
	}
	
	/**
	 * Destruye las celdas, baja los dulces, repone los caramelos, busca una 
	 * nueva explosion y repite el proceso mientras hayan explosiones
	 * @param celdasADestruir
	 */
	protected void realizarExplosionesYNotificar(
			List<Point> celdasADestruir, boolean notificar){
		List<Point> aDestruir = celdasADestruir;
		while(! aDestruir.isEmpty())
		{	this.ordenarCeldas(aDestruir);
			this.destruirCeldas(aDestruir);
			this.moverCeldasPorDestruccion(aDestruir);
			this.reponerDulces(aDestruir);
			aDestruir = this.buscarExplosionYNotificar(notificar);
		}
	}
	
	/**
	 * Ordena los points por valor y
	 * @param points
	 */
	protected void ordenarCeldas(List<Point> points){
		for(int i = 1; i < points.size(); i++)
		{	if(points.get(i).y < points.get(i-1).y)
			{	Point pAux = points.get(i-1);
				points.set(i-1, points.get(i));
				points.set(i, pAux);
				if(i > 1)
				{	i -= 2;
				}
			}
		}
	}
	
	/**
	 * Elimina el contenido de las celdas del parametro
	 * @param celdasADestruir
	 * 
	 */
	protected void destruirCeldas(List<Point> celdasADestruir){
		this.esperarSiEnJuego(150);
		for(Point celda : celdasADestruir)
		{	this.destruirCelda(celda);
		}
		this.esperarSiEnJuego(150);
	}
	
	/**
	 * Mueve las celdas vacias hacia arriba (se bajan los dulces)
	 * @param celdasDestruidas celdas vacias
	 */
	protected void moverCeldasPorDestruccion(List<Point> celdasDestruidas){
		Arriba arriba = Direccion.ARRIBA;
		for(Point celda : celdasDestruidas)
		{	if(this.esCeldaVacia(celda))
			{	Point celdaARevisar = celda;
				Point celdaALlenar = celda;
				while(arriba.puedeMover(celdaARevisar, this.getDimension()))
				{	celdaARevisar = arriba.puntoEnDireccion(celdaARevisar);
					if(! this.esCeldaVacia(celdaARevisar))
					{	this.intercambiarCeldas(celdaALlenar, celdaARevisar);
						celdaALlenar = arriba.puntoEnDireccion(celdaALlenar);
					}
				}
			}
		}
	}
	
	/**
	 * Repone dulces en las celdas superiores del parametro que se 
	 *  encuentren vacias
	 * @param celdasMovidas posiciones que fueron explotadas y bajaron
	 *  (en caso de haber) los dulces en ellas
	 */
	protected void reponerDulces(List<Point> celdasMovidas){
		Point celdaARevisar;
		Arriba arriba = Direccion.ARRIBA;
		for(Point celda : celdasMovidas)
		{	celdaARevisar = celda;
			while(arriba.puedeMover(celdaARevisar, this.getDimension()))
			{	if(this.esCeldaVacia(celdaARevisar))
				{	this.ponerDulceEn(this.getDificultad().dulceRandom(), celdaARevisar);
				}
				celdaARevisar = arriba.puntoEnDireccion(celdaARevisar);
			}
			if(this.esCeldaVacia(celdaARevisar))
			{	this.ponerDulceEn(this.getDificultad().dulceRandom(), celdaARevisar);
			}
		}
	}
	
	/**
	 * Busca en el tablero una explosión y la puede notificar 
	 * en caso de haber
	 *  @param notificar si se notificara la explosion
	 * @return celdas de explosion encontrada. Devuelve la lista 
	 * vacia en caso de no producirse explosion
	 */
	protected List<Point> buscarExplosionYNotificar(boolean notificar){
		List<Point> celdasAExplotar = 
				buscarExplosionDesdeOrigenEnDireccion(Direccion.DERECHA);
		if(celdasAExplotar.isEmpty())
		{	celdasAExplotar = 
					buscarExplosionDesdeOrigenEnDireccion(Direccion.ARRIBA);
		}
		if(notificar && ! celdasAExplotar.isEmpty())
		{	Dulce dulceExplosion = this.dulceEn(celdasAExplotar.get(0));
			Explosion explosion = this.crearExplosion(dulceExplosion, celdasAExplotar.size());
			this.getPartida().agregarExplosion(explosion);
		}
		return celdasAExplotar;
	}
	
	/**
	 * Busca desde el origen una explosion en una direccion
	 * OBSERVACION: Devuelve solo explosiones horizontales o verticales
	 * @param direccion a realizar la busqueda
	 * @return celdas de explosion encontrada. Devuelve la lista 
	 *  vacia en caso de no producirse explosion
	 */
	protected List<Point> buscarExplosionDesdeOrigenEnDireccion(Direccion direccion){
		List<Point> celdasAExplotar = new ArrayList<Point>();
		Point celda = this.puntoOrigen();
		Direccion otraDireccion;
		if(direccion.esArriba())
		{	otraDireccion = Direccion.DERECHA;
		}
		else
		{	otraDireccion = Direccion.ARRIBA;
		}
		boolean fin = false;
		boolean hayExplosion = false;
		while(! fin)
		{	Point celdaAEvaluar = celda;
			int aciertos = 0;
			while(! fin && direccion.puedeMover(celdaAEvaluar, this.getDimension()))
			{	Point celdaEnDireccion = direccion.puntoEnDireccion(celdaAEvaluar);
				if(dulceEn(celdaAEvaluar).compatibleCon(dulceEn(celdaEnDireccion)))
				{	if(hayExplosion)
					{	celdasAExplotar.add(celdaEnDireccion);
					}
					else
					{	aciertos++;
						hayExplosion = aciertos > 1;
						if(hayExplosion)
						{	Point celdaEnDireccionOpuesta = 
									direccion.opuesta().puntoEnDireccion(celdaAEvaluar);
							celdasAExplotar.add(celdaEnDireccionOpuesta);
							celdasAExplotar.add(celdaAEvaluar);
							celdasAExplotar.add(celdaEnDireccion);
						}
					}
				}
				else
				{	aciertos = 0;
					if(hayExplosion)
					{	fin = true;
					}
				}
				celdaAEvaluar = celdaEnDireccion;
			}
			if(! hayExplosion && otraDireccion.puedeMover(celda, this.getDimension()))
			{	celda = otraDireccion.puntoEnDireccion(celda);
			}
			else
			{	fin = true;
			}
		}
		return celdasAExplotar;
	}
	
	/**
	 * Pone un dulce en cada celda. El tablero resultante no
	 *  posee explosiones a realizar
	 */
	protected void llenarTablero(){
		this.llenarTableroConPotencialesExplosiones();
		
//		Las siguientes explosiones no se notificaran
		List<Point> celdasADestruir = this.buscarExplosionYNotificar(false);
		this.realizarExplosionesYNotificar(celdasADestruir, false);
	}
	
	/**
	 * Pone un dulce en cada celda. El tablero resultante puede
	 *  poseer explosiones a realizar
	 */
	protected void llenarTableroConPotencialesExplosiones(){
		Point celda = this.puntoOrigen();
		Arriba arriba = Direccion.ARRIBA;
		Derecha derecha = Direccion.DERECHA;
		boolean finTablero = false;
		while(! finTablero)
		{	Point celdaALlenar = celda;
			this.ponerDulceEn(this.getDificultad().dulceRandom(), celdaALlenar);
			while(derecha.puedeMover(celdaALlenar, this.getDimension()))
			{	celdaALlenar = derecha.puntoEnDireccion(celdaALlenar);
				this.ponerDulceEn(this.getDificultad().dulceRandom(), celdaALlenar);
			}
			if(arriba.puedeMover(celda, this.getDimension()))
			{	celda = arriba.puntoEnDireccion(celda);
			}
			else
			{	finTablero = true;
			}
		}
	}
	
	protected void esperarSiEnJuego(int milisegundos){
		if(this.isEnJuego())
		{	this.partida.setRepresentacionVisual(this.representacionVisual());
//			try {Thread.sleep(milisegundos);}
//				catch (InterruptedException e) {e.printStackTrace();}
		}
	}
	
	/**
	 * 
	 * @param celda1
	 * @param celda2
	 * @return si la celda a la que apuntan las variables de instancia
	 * es adyacente a la celda apuntada por las variables
	 */
	public boolean esCeldaAdyacente(Point celda1, Point celda2){
		return 1 == ( Math.abs(celda1.x - celda2.x) +
				Math.abs(celda1.y - celda2.y) );
	}
	
	public String representacionVisual(){
		Point celda = new Point(0, this.dimension.y);
		Point celdaAEvaluar;
		Direccion abajo = Direccion.ABAJO;
		Direccion derecha = Direccion.DERECHA;
		String tablero = "";
		boolean end = false;
		while(! end)
		{	celdaAEvaluar = celda;
			tablero += this.dulceEn(celdaAEvaluar);
			while(derecha.puedeMover(celdaAEvaluar, this.getDimension()))
			{	celdaAEvaluar = derecha.puntoEnDireccion(celdaAEvaluar);
				tablero += " | ";
				tablero += this.dulceEn(celdaAEvaluar);
			}
			if(abajo.puedeMover(celda, this.getDimension()))
			{	celda = abajo.puntoEnDireccion(celda);
				tablero += "\n <br />";
			}
			else
			{	end = true;
			}
		}
		return tablero;
	}
	
	public void reiniciar(){
		this.setEnJuego(false);
		this.llenarTablero();
		this.setEnJuego(true);
	}
	
	// ****************************************************
	// * Accessors
	// ****************************************************	
	public Point getDimension() {return dimension;}
	public void setDimension(Point dimension) {this.dimension = dimension;}
	public Dificultad getDificultad() {return dificultad;}
	public void setDificultad(Dificultad dificultad) 
		{this.dificultad = dificultad;}
	public Celda[][] getCeldas() {return celdas;}
	public void setCeldas(Celda[][] celdas) {this.celdas = celdas;}
	
	public Partida getPartida() {return partida;}
	public void setPartida(Partida partida) {this.partida = partida;}
	public boolean isEnJuego() {return enJuego;}
	public void setEnJuego(boolean enJuego) {this.enJuego = enJuego;}
	
}
