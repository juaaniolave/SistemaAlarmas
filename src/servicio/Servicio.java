package servicio;
/*
 * clase responsable de la creacion de servicios de cualquier tipo (camara, boton y acompaniamiento)
 */
public abstract class Servicio implements Cloneable {
	public String id;
	protected double valor;

	/**
	 * <b>Pre</b>:<br>
	 * <b>Inv</b>:<br>
	 * <b>Post</b>: Este metodo devuelve el valor constante del servicio especifico agregado.<br>
	 */
	public abstract double costo();

	/**
	 * <b>Pre</b>:<br>
	 * <b>Inv</b>:<br>
	 * <b>Post</b>: El metodo devuelve el tipo del hijo servicio en forma de String.<br>
	 */
	public abstract String getTipo();

	/**
	 * Es el metodo encargado de la clonacion del servicio<br>
	 * <br>
	 * <b>Pre</b>:<br>
	 * <b>Inv</b>:<br>
	 * <b>Post</b>: Devuelve un clon de tipo servicio<br>
	 * 
	 * @throws CloneNotSupportedException si algun atributo de tipo objeto no fuera
	 *                                    clonable
	 */
	public Object clone() throws CloneNotSupportedException {
		Servicio clon = null;
		clon = (Servicio) super.clone();
		return clon;
	}
}