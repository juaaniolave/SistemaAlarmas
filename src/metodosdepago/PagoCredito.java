package metodosdepago;

import abonado.Abonado;

public class PagoCredito extends DecoratorPago {

	public PagoCredito(Abonado abonado) {
		super(abonado);
		// TODO Auto-generated constructor stub
	}
	@Override
	public double valorDeTipoPago() {
		return valorSinTipoPago() * 1.05;
	}
	public String tipoDePago() {
		return "Tarjeta de credito";
	}
	@Override
	public Object clone() throws CloneNotSupportedException {
		PagoCredito clon = null;
		clon = (PagoCredito) super.clone();
		return clon;
	}
}