package excepciones;

import empresa.Factura;

public class FacturaInexistenteException extends Exception {
	private Factura factura;
	public FacturaInexistenteException(String mensaje,Factura factura) {
		super(mensaje);
		this.factura=factura;
	}
	public Factura getFactura() {
		return factura;
	}
	
}
