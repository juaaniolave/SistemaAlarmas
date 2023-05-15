package metodosdepago;

import abonado.Abonado;

public class PagoCheque extends DecoratorPago {

	public PagoCheque(Abonado abonado) {
		super(abonado);
	}
	@Override
	public double valorDeTipoPago() {
		return valorSinTipoPago() * 1.1;
	}
	public String tipoDePago() {
		return "Cheque";
	}
	@Override
	public Object clone() throws CloneNotSupportedException {
		PagoCheque clon = null;
		clon = (PagoCheque) super.clone();
		return clon;
	}

}