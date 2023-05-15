package excepciones;

import Domicilio.Domicilio;

@SuppressWarnings("serial")
public class DomicilioExistenteException extends Exception {

	Domicilio domicilio;

	public DomicilioExistenteException(String mensaje, Domicilio domicilio) {
		super(mensaje);
		this.domicilio = domicilio;
	}

	public Domicilio getDomicilio() {
		return domicilio;
	}
}
