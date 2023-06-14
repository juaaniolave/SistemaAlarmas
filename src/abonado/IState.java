package abonado;

import java.time.LocalDate;

import empresa.Contratacion;
import empresa.Factura;
import empresa.IFactura;
import excepciones.ContratacionInvalidaException;
import excepciones.FacturaInexistenteException;

public interface IState {

	void pagarFactura(IFactura factura, LocalDate fechaDatePago) throws FacturaInexistenteException;
	void contratarServicio(Contratacion contrato);
	void bajarServicio(Contratacion contrato)throws ContratacionInvalidaException;
	void chequeaCambio();
	double valorTotal();
}
