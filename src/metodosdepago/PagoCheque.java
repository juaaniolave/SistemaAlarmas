package metodosdepago;

import java.math.BigDecimal;
import java.time.LocalDate;

import abonado.Abonado;
import empresa.IFactura;

public class PagoCheque extends DecoratorPago implements IFactura {

	public PagoCheque(IFactura factura) {
		super(factura);
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
	@Override
	public double getMonto() {
		BigDecimal numeroRedondeado = new BigDecimal(factura.getMonto()* 1.1).setScale(2, BigDecimal.ROUND_HALF_UP);		
		return numeroRedondeado.doubleValue();
	}
	@Override
	public LocalDate getFechaDePago() {

		return factura.getFechaDePago();
	}
	@Override
	public void setFechaDePago(LocalDate fechaDePago) {
		factura.setFechaDePago(fechaDePago);	
	}
	@Override
	public LocalDate getFechaDeEmision() {
		return factura.getFechaDeEmision();
	}
	@Override
	public boolean isPago() {
		return factura.isPago();
	}
	@Override
	public Abonado getAbonado() {
		return factura.getAbonado();
	}
	public IFactura getFactura() {
		return this.factura;
	}
	@Override
	public double getMontoSinTipoDePago() {
		return this.factura.getMonto();
	}

	public void setInteresPorMora(boolean interesPorMora) {
		this.factura.setInteresPorMora(interesPorMora);
		}
	
	public boolean isInteresPorMora() {
		return factura.isInteresPorMora();
	}
}