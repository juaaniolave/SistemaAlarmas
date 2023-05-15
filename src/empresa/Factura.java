package empresa;

import abonado.Abonado;
import metodosdepago.DecoratorPago;
/*
 * esta clase es la encargada de recopilar todas las contrataciones de un determinado abonado
 * y operarlas de forma tal que nos devuelva informacion valiosa, en este caso, lo que debera pagar
 * el abonado
 */
public class Factura implements Cloneable {
	private DecoratorPago abonado;
	
	/**
	 * Constructor de la clase <br>
	 * <br>
	 * <b>Pre</b>: Se espera que abonado sea distinto de null y exista ".<br>
	 * <b>Inv</b>: abonado.<br>
	 * <b>Post</b>: Se setea el valor del atributo abonado<br>
	 */
	public Factura(DecoratorPago abonado) {
		this.abonado = abonado;
	}

	/**
	 * Este metodo le asigna una instancia de abonado al atributo abonado.</br>
	 * </br>
	 * <b>Pre</b>:<br>
	 * El abonado debe ser distinto de null y debe existir.<br>
	 * <b>Inv</b>:<br>
	 * <b>Post</b>: Se asigna el atributo abonado.<br>
	 * 
	 * @param Abonado es el abonado que se le asigna al atributo.
	 */
	public void setAbonado(DecoratorPago abonado) {
		this.abonado = abonado;
	}

	/**
	 * <b>Pre</b>:<br>
	 * <b>Inv</b>:<br>
	 * <b>Post<b>: Retorna el abonado. <br>
	 */
	public DecoratorPago getAbonado() {
		return abonado;
	}

	/**
	 * Este metodo da todas las caracteristicas de la factura, tanto abonado como
	 * domicilios, contrataciones y servicios junto con los respectivos valores.
	 * </br>
	 * <br>
	 * <b>Pre</b>:<br>
	 * <b>Inv</b>:<br>
	 * <b>Post</b>: Devuelve un tipo String con toda la informacion.<br>
	 * 
	 * @return toda la informacion de la factura.
	 */
	public String ImprimeFactura() {
		String aux = "";
		aux += this.abonado.toString() + "|tipo de pago: " + this.abonado.tipoDePago() + "\n";
		for (int i = 0; i < this.abonado.getLista().size(); i++) {
			aux += this.abonado.getLista().get(i).descripcion() + "\n";
		}
		aux += "Precio neto(sin metodo de pago):" + this.abonado.valorSinTipoPago() + "\n";
		aux += "Precio con metodo de pago:" + this.abonado.valorDeTipoPago() + "\n";
		return aux;
	}

	/**
	 * Este metodo se dedica a crear y devolver un clon de clase factura</br>
	 * </br>
	 * <b>Pre</b>:<br>
	 * <b>Inv</b>:<br>
	 * <b>Post</b>: Devuelve un clon de la factura en caso de ser posible. <br>
	 * 
	 * @throws CloneNotSupportedException solamente si algun atriburo de tipo objeto
	 *                                    no fuera clonable
	 * @return clon un clon de la factura
	 */
	public Object clone() throws CloneNotSupportedException {
		Factura clon = null;
		clon = (Factura) super.clone();
		clon.abonado = (DecoratorPago) this.abonado.clone();
		return clon;
	}
}