package servicio;

public class ServicioBoton extends Servicio {
	public double costo() {
		return 2000;
	}

	@Override
	public String getTipo() {
		return "Boton";
	}

	public Object clone() throws CloneNotSupportedException {
		ServicioBoton clon = null;
		clon = (ServicioBoton) super.clone();
		return clon;
	}
}