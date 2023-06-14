package excepciones;

import abonado.Abonado;

public class ReparacionYaSolicitadaException extends Exception{

	private Abonado abonado;

	public ReparacionYaSolicitadaException(String message, Abonado abonado) {
		super(message);
		this.abonado = abonado;
	}

	public Abonado getAbonado() {
		return abonado;
	}
	
	
	
}
