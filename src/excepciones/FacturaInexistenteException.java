package excepciones;

import empresa.Factura;
import empresa.IFactura;

public class FacturaInexistenteException extends Exception {
	private IFactura factura;
	public FacturaInexistenteException(String mensaje,IFactura factura) {
		super(mensaje);
		this.factura=factura;
	}
	public IFactura getFactura() {
		return factura;
	}
	
}
