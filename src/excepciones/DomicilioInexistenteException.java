package excepciones;

import Domicilio.Domicilio;

@SuppressWarnings("serial")
public class DomicilioInexistenteException extends Exception {
	private Domicilio domicilio;

	public DomicilioInexistenteException(String mensaje, Domicilio domicilio) {
		super(mensaje);
		this.domicilio = domicilio;
	}

	public Domicilio getDomicilio() {
		return domicilio;
	}

}