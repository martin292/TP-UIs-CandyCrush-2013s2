package objetivos;


import java.io.Serializable;

import org.uqbar.commons.utils.Observable;

import partida.Partida;

import explosiones.Explosion;

@Observable
public class ObjetivoParaCumplir implements Serializable{

	// ***************************************
	// * Variables
	// ***************************************
	protected Objetivo objetivoACumplir;
	protected EstadoObjetivo estado;
	protected int valorPartida;
	
	protected Partida partida;
	
	// ***************************************
	// * Constructores
	// ***************************************
	public ObjetivoParaCumplir(Partida partida, Objetivo objetivo){
		this.objetivoACumplir = objetivo;
		this.valorPartida = 0;
		this.estado = EstadoObjetivo.INCOMPLETO;
		this.partida = partida;
	}
	
	public ObjetivoParaCumplir(Objetivo objetivo){
		this.setObjetivoACumplir(objetivo);
		this.setValorPartida(0);
		this.setEstado(EstadoObjetivo.INCOMPLETO);
	}
	
	// ***************************************
	// * Metodos
	// ***************************************
	
	/**
	 * 
	 * @param Explosion e
	 */
	public void chequearRealizacion(Explosion explosion){
		if(this.getEstado().chequear(this, explosion))
		{	this.getPartida().agregarAlLog(this.getObjetivoACumplir());
		}
	}
			
	/**
	 * Incrementa la variable valorPartida en 1
	 */
	public void incrementarValorPartida(){
		this.setValorPartida(this.getValorPartida() + 1);		
	}
	
	public void incrementarValorPartida(int cantidad){
		this.setValorPartida(this.getValorPartida() + cantidad);		
	}
	
	/**
	 * Retorna true si esta completo
	 */
	public boolean objetivoRealizado(){
		return this.getEstado().estaCompleto();
	}

	@Override
	public String toString(){
		return this.getObjetivoACumplir().toString();
	}
	
	public void notificarObjetivoCompletado(Objetivo objetivo){
		this.getPartida().agregarAlLog(objetivo);
	}
	
	// ***************************************
	// * Accessors
	// ***************************************
	public Objetivo getObjetivoACumplir() {
		return objetivoACumplir;
	}
	public void setObjetivoACumplir(Objetivo objetivoACumplir) {
		this.objetivoACumplir = objetivoACumplir;
	}
	public int getValorPartida() {
		return valorPartida;
	}
	public void setValorPartida(int valorPartida) {
		this.valorPartida = valorPartida;
	}
	public EstadoObjetivo getEstado() {
		return estado;
	}
	public void setEstado(EstadoObjetivo estado) {
		this.estado = estado;
	}
	
	public Partida getPartida() {
		return partida;
	}
	public void setPartida(Partida partida) {
		this.partida = partida;
	}
	
}
