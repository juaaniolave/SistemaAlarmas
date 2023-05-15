package promo;

import Domicilio.Domicilio;
/*
 * clase implementada para funcionar como doble dispach con la clase domicilio
 * la cual hace que la contratacion mediante un domicilio permita acceder a una determinada
 * promo, las cuales se encargaran de hacer un descuento al total de esta.
 */
public interface Promo {
	/**
	 * <b>Pre</b>: El domicilio debe ser distinto de null.<br>
	 * <b>Inv</b>: Domicilio.<br>
	 * <b>Post</b>: Se aplica la promo al correspondiente domicilio.<br>
	 */
	public abstract double aplicarPromo(Domicilio domicilio);

	String toString();

	/**
	 * El metodo se dedica a crear un clon de una promocion<br>
	 * 
	 * @return clon de la promocion
	 */
	Object clone() throws CloneNotSupportedException;
}