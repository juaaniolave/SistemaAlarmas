package metodosdepago;

import java.time.LocalDate;

import abonado.Abonado;
import empresa.IFactura;

public class PagoEfectivo extends DecoratorPago implements IFactura {

	public PagoEfectivo(IFactura factura) {
		super(factura);
		// TODO Auto-generated constructor stub
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

	@Override
	public double getMonto() {
		
		return factura.getMonto()*0.8;
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