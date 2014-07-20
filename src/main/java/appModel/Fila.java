package appModel;

import java.util.List;

import partida.Celda;

public class Fila {
	
	protected List<Celda> celdas;

	public Fila(List<Celda> celdas) {
		super();
		this.celdas = celdas;
	}
	
	public List<Celda> getCeldas() {return celdas;}
	public void setCeldas(List<Celda> celdas) {this.celdas = celdas;}
	
	
}

