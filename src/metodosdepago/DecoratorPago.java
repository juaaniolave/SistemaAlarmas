package metodosdepago;

import java.io.Serializable;
import java.util.ArrayList;

import abonado.Abonado;
import empresa.Contratacion;
import empresa.IFactura;
/*
 * esta clase funciona para poder aplicar el patron decorator a los abonados, encapsulando este
 * mismo y generando un abonado con un tipo de pago (tarjeta, cheque o efectivo), estas clases
 * puede acceder a determinada informacion de la clase abonado, tambien el abonado de la clase
 * factura sera de este tipo (Decorador).
 */
public abstract class DecoratorPago implements Cloneable, IFactura, Serializable {
	
	protected IFactura factura;

	/**
	 * <b>Pre</b>: Abonado debe ser distinto de null.<br>
	 * <b>Inv</b>:<br>
	 * <b>Post</b>: Encapsula el abonado.<br>
	 */
	public DecoratorPago(IFactura factura) {
		this.factura = factura;
	}


	/**
	 * <b>Pre</b>:<br>
	 * <b>Inv</b>:<br>
	 * <b>Post:</b>: Retorna el valor total sin el tipo de pago.<br>
	 */
	public double valorSinTipoPago() {
		return this.factura.getMonto();
	}
	

	
	public Object clone() throws CloneNotSupportedException{
		return this;
	};

}