package excepciones;

@SuppressWarnings("serial")
public class FactoryInvalidoException extends Exception {

	private String tipopago;

	public FactoryInvalidoException(String mensaje, String tipopago) {
		super(mensaje);
		this.tipopago = tipopago;
	}

	public String getTipoPagoInvalido() {
		return tipopago;
	}
}