package dulces;

import java.io.Serializable;
import java.util.*;

import org.uqbar.commons.utils.Observable;

@Observable
public class Dulce implements Serializable {

	// ****************************************************
	// * Variables estaticas
	// ****************************************************
	public static final Dulce VERDE = new Dulce("VE");
	public static final Dulce ROJO = 	new Dulce("RO");
	public static final Dulce AMARILLO= new Dulce("AM");
	public static final Dulce NARANJA = new Dulce("NA");
	public static final Dulce VIOLETA = new Dulce("VI");
	public static final Dulce CELESTE = new Dulce("CE");
	
	protected static List<Dulce> dulces = Arrays.asList(
			VERDE, ROJO, AMARILLO, NARANJA, VIOLETA, CELESTE);
	
	// ****************************************************
	// * Variables
	// ****************************************************
	private String color;

	// ****************************************************
	// * Constructores
	// ****************************************************
	public Dulce(String color) {
		super();
		this.color = color;
	}
	
	public Dulce() {
		super();
	}

	// ****************************************************
	// * Metodos estaticos
	// ****************************************************
	public static List<Dulce> getDulces(int cantidad){
		return Dulce.getDulces().subList(0, cantidad);
	}
	
	// ****************************************************
	// * Metodos
	// ****************************************************
	/**
	 * Compara el color con el color del dulce pasado por parametro
	 * @param d
	 * @return
	 */
	public boolean compatibleConColor(Dulce d){
		return this.color.equals(d.getColor());
	}
	public boolean compatibleCon(Dulce d){
		return this.color.equals(d.getColor());
	}
	
	@Override
	public String toString() {
		return this.getColor();
	}
	
	// ****************************************************
	// * Accessors
	// ****************************************************	
	public static Dulce getVerde() 		{return VERDE;}
	public static Dulce getRojo() 		{return ROJO;}
	public static Dulce getAmarillo() 	{return AMARILLO;}
	public static Dulce getNaranja() 	{return NARANJA;}
	public static Dulce getVioleta()	{return VIOLETA;}
	public static Dulce getCeleste() 	{return CELESTE;}
	public static List<Dulce> getDulces(){return dulces;}
	
	public String getColor() {return color;}
	public void setColor(String color) {this.color = color;}
	
}
