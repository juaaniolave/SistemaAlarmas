package empresa;

import java.time.LocalDate;

import abonado.Abonado;

public interface IFactura {
	
	double getMonto();
	
	double getMontoSinTipoDePago();
	
	IFactura getFactura();

	LocalDate getFechaDePago();

	void setFechaDePago(LocalDate fechaDePago);
	 
	LocalDate getFechaDeEmision();
	
	public boolean isPago();

	public Abonado getAbonado();

	Object clone() throws CloneNotSupportedException;
	
	public void setInteresPorMora(boolean interesPorMora);
	
	public boolean isInteresPorMora();
}
