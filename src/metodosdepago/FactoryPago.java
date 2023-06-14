package metodosdepago;

import abonado.Abonado;
import empresa.IFactura;
import excepciones.FactoryInvalidoException;
/*
 * esta clase implementa el patron de creacion 'factory' el cual mediante un abonado y un 
 * 'String' que especifica que clase de metodo de pago tendra crea un 'Decoratorpago'
 */
public class FactoryPago {
	/**
	 * <b>Pre</b>: Abonado y tipopago deben ser distinto de null.<br>
	 * <b>Inv</b>:<br>
	 * <b>Post</b>: Retorna la instancia del tipo de pago.<br>
	 */
	public IFactura getMetodoDePago(IFactura factura, String tipopago) throws FactoryInvalidoException {
		IFactura aux = null;
		if (tipopago.equals("Tarjeta"))
			aux = new PagoCredito(factura);

		else 
			if (tipopago.equals("Efectivo"))
				aux = new PagoEfectivo(factura);

		else 
			if (tipopago.equals("Cheque"))
				aux = new PagoCheque(factura);
			else
				throw new FactoryInvalidoException("No existe el tipo de pago", tipopago);

		return aux;
	}
}