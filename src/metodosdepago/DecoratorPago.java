package metodosdepago;

import java.util.ArrayList;

import abonado.Abonado;
import abonado.iAbonado;
import empresa.Contrataciones;
/*
 * esta clase funciona para poder aplicar el patron decorator a los abonados, encapsulando este
 * mismo y generando un abonado con un tipo de pago (tarjeta, cheque o efectivo), estas clases
 * puede acceder a determinada informacion de la clase abonado, tambien el abonado de la clase
 * factura sera de este tipo (Decorador).
 */
public abstract class DecoratorPago implements Cloneable, iAbonado {
	protected Abonado abonado;

	/**
	 * <b>Pre</b>: Abonado debe ser distinto de null.<br>
	 * <b>Inv</b>:<br>
	 * <b>Post</b>: Encapsula el abonado.<br>
	 */
	public DecoratorPago(Abonado abonado) {
		this.abonado = abonado;
	}

	/**
	 * <b>Pre</b>:<br>
	 * <b>Inv</b>:<br>
	 * <b>Post</b>: Retorna el valor total con el tipo de pago asignado.<br>
	 */
	public abstract double valorDeTipoPago();

	/**
	 * <b>Pre</b>:<br>
	 * <b>Inv</b>:<br>
	 * <b>Post:</b>: Retorna el valor total sin el tipo de pago.<br>
	 */
	public double valorSinTipoPago() {
		return this.abonado.valorTotal();
	}

	/**
	 * <b>Pre</b>:<br>
	 * <b>Inv</b>:<br>
	 * <b>Post</b>: Retorna lista de contrataciones de cada abonado.<br>
	 */
	public ArrayList<Contrataciones> getLista() {
		return this.abonado.getLista();
	}

	/**
	 * <b>Pre</b>:<br>
	 * <b>Inv</b>:<br>
	 * <b>Post</b>: Retorna el tipo de pago que realiza el abonado.<br>
	 */
	public abstract String tipoDePago();
	public Abonado getAbonadotype() {
		return this.abonado;
	}
	/**
	 * <b>Pre</b>:<br>
	 * <b>Inv</b>:<br>
	 * <b>Post</b>: Clona el DecoratorPago.<br>
	 */
	public Object clone() throws CloneNotSupportedException {
		DecoratorPago clon = null;
		clon = (DecoratorPago) super.clone();
		clon.abonado=(Abonado)this.abonado.clone();
		return clon;
	}

	public String toString() {
		return this.abonado.toString();
	}
}