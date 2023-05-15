package servicio;

public class ServicioAcompaniamiento extends Servicio {

	public double costo() {
		return 7500;
	}

	@Override
	public String getTipo() {
		return "Acompaniamiento";
	}

	public Object clone() throws CloneNotSupportedException {
		ServicioAcompaniamiento clon = null;
		clon = (ServicioAcompaniamiento) super.clone();
		return clon;
	}

}