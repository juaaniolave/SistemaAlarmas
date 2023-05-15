package metodosdepago;

import abonado.Abonado;

public class PagoEfectivo extends DecoratorPago {

	public PagoEfectivo(Abonado abonado) {
		super(abonado);
		// TODO Auto-generated constructor stub
	}

	@Override
	public double valorDeTipoPago() {
		return valorSinTipoPago() * 0.8;
	}

	public String tipoDePago() {
		return "Efectivo";
	}
	@Override
	public Object clone() throws CloneNotSupportedException {
		PagoEfectivo clon = null;
		clon = (PagoEfectivo) super.clone();
		return clon;
	}
}