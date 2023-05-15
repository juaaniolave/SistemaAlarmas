package excepciones;

import abonado.Abonado;
/*
 * Esta clase 
 */
public class AbonadoInexistenteException extends Exception {
	private Abonado abonado;
	public AbonadoInexistenteException(String mensaje,Abonado abonado) {
		super(mensaje);
		this.abonado=abonado;
	}
	public Abonado getAbonado() {
		return abonado;
	}
	
}
