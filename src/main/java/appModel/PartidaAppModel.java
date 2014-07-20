package appModel;

import java.util.ArrayList;
import java.util.List;

import objetivos.Objetivo;

import mundo.NivelParaJugar;
import dificultad.Dificultad;
import explosiones.Explosion;

import partida.Celda;
import partida.Partida;

public class PartidaAppModel extends Partida{
	
	// ****************************************************
	// * Variables
	// ****************************************************
	protected List<Fila> filas;
	protected List<Log> logs;
	
	// ****************************************************
	// * Constructores
	// ****************************************************
	public PartidaAppModel(NivelParaJugar nivelParaJugar, Dificultad dificultad) {
		super(nivelParaJugar, dificultad);
		this.filas = this.filasDeTablero();
		this.logs = new ArrayList<Log>();
	}
	
	// ****************************************************
	// * Metodos
	// ****************************************************
	/**
	 * @return lista donde cada elemento es una lista de celdas de una fila
	 */
	public List<List<Celda>> celdasTablero(){
		List<List<Celda>> celdas = new ArrayList<List<Celda>>();
		for(int fila = 0; fila < this.filasTablero(); fila++){
			List<Celda> celdasFila = new ArrayList<Celda>();
			for(int col = 0; col < this.columnasTablero(); col++){
				celdasFila.add(this.getTablero().getCelda(col, fila));
			}
			celdas.add(celdasFila);
		}
		return celdas;
	}
	
	public List<Fila> filasDeTablero(){
		List<Fila> filas = new ArrayList<Fila>();
		for(int fila = 0; fila < this.filasTablero(); fila++){
			List<Celda> celdasFila = new ArrayList<Celda>();
			for(int col = 0; col < this.columnasTablero(); col++){
				celdasFila.add(this.getTablero().getCelda(col, fila));
			}
			filas.add(new Fila(celdasFila));
		}
		return filas;
	}
	
	@Override
	public void limpiarLogMovimiento(){
		this.setLogs(new ArrayList<Log>());
	}
	
	@Override
	public void agregarAlLog(Explosion explosion){
		Log log = new Log("BOOM! " + explosion + " " + "puntos: " + explosion.puntos());
		this.getLogs().add(log);
	}
	
	@Override
	public void agregarAlLog(Objetivo objetivo){
		Log log = new Log("\"" + objetivo + "\" completado!");
		this.getLogs().add(log);
	}

	// ****************************************************
	// * Accessors
	// ****************************************************	
	public List<Fila> getFilas() {return filas;}
	public void setFilas(List<Fila> filas) {this.filas = filas;}
	public List<Log> getLogs() {return logs;}
	public void setLogs(List<Log> logs) {this.logs = logs;}
	
}
