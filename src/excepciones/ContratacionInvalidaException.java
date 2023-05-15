package excepciones;

import Domicilio.Domicilio;
import abonado.Abonado;

@SuppressWarnings("serial")
public class ContratacionInvalidaException extends Exception {
	private Domicilio domicilio;
	private Abonado abonado;

	public ContratacionInvalidaException(String mensaje, Domicilio domicilio, Abonado abonado) {
		super(mensaje);
		this.abonado = abonado;
		this.domicilio = domicilio;
	}

	public Domicilio getDomicilio() {
		return this.domicilio;
	}

	public Abonado getAbonado() {
		return this.abonado;
	}
}