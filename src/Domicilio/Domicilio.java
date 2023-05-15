package Domicilio;
/*
 *es el domicilio junto con su tipo (vivienda o comercio) el cual sera unico
 */
public abstract class Domicilio implements Cloneable {
	private String nombre;
	private boolean agregado = false;

	/**
	 * <b>Pre</b>: se espera que el nombre no sea null y " ".<br>
	 * <b>Inv</b>:<br>
	 * <b>Post</b>:<br>
	 */
	public Domicilio(String nombre) {
		assert nombre != null : "El nombre no puede ser null";
		assert nombre != "" : "El nombre no puede ser vacio";
		this.nombre = nombre;
	}

	/**
	 * Este metodo nos ayuda a asegurar que nuestro domicilio no se ingrese en otra
	 * lista de domicilios/contrataciones.<br>
	 * <br>
	 * <b>Pre</b>:<br>
	 * <b>Inv</b>:<br>
	 * <b>Post</b>: rectifica que se agrego cambiando el valor agregado a true.<br>
	 */
	public void setAgregado(boolean type) {
		this.agregado = type;
	}

	/**
	 * <b>Pre</b>:<br>
	 * <b>Inv</b>:<br>
	 * <b>Post</b>: da a conocer si el domicilio esta agregado en una lista.<br>
	 */
	public boolean isAgregado() {
		return agregado;
	}

	/**
	 * <b>Pre</b>:<br>
	 * <b>Inv</b>:<br>
	 * <b>Post</b>: retorna el valor base del tipo de domicilio.<br>
	 */
	public abstract double valorBase();

	/**
	 * <b>Pre</b>:<br>
	 * <b>Inv</b>:<br>
	 * <b>Post</b>: retorna el descuento de la promoDorada dependiendo su tipo de
	 * domicilio.<br>
	 */
	public abstract double promoDorada();

	/**
	 * <b>Pre</b>:<br>
	 * <b>Inv</b>:<br>
	 * <b>Post</b>: retorna el descuento de la promoPlatino dependiendo su tipo de
	 * domicilio.<br>
	 */
	public abstract double promoPlatino();

	/**
	 * <b>Pre</b>:<br>
	 * <b>Inv</b>:<br>
	 * <b>Post</b>: retorna el tipo de domicilio.<br>
	 */
	public abstract String getTipoDom();

	/**
	 * <b>Pre</b>:<br>
	 * <b>Inv</b>:<br>
	 * <b>Post</b>: retorna el nombre del domicilio.<br>
	 */
	@Override
	public String toString() {
		return "Domicilio '" + nombre;
	}

	/**
	 * <b>Pre</b>:<br>
	 * <b>Inv</b>:<br>
	 * <b>Post</b>: clona domicilio.<br>
	 */
	public Object clone() throws CloneNotSupportedException {
		Domicilio clon = (Domicilio) super.clone();
		return clon;
	}

}