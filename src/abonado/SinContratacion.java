package abonado;

import java.io.Serializable;
import java.time.LocalDate;

import empresa.Contratacion;
import empresa.IFactura;
import excepciones.ContratacionInvalidaException;

public class SinContratacion implements IState, Serializable {


	private Fisica abonado;
	
	
	
	public SinContratacion(Fisica abonado) {
		super();
		this.abonado = abonado;
	}

	@Override
	public void pagarFactura(IFactura factura,LocalDate fechaDePago) {
		//No puede pagar ya que no contrato nada
		//Mensaje a traves de la ventana
	}

	@Override
	public void contratarServicio(Contratacion contrato) {
		abonado.aniadirContratacion(contrato);
		abonado.setEstado(new ConContratacion(this.abonado));
	}

	@Override
	public void bajarServicio(Contratacion contrato) throws ContratacionInvalidaException {
		//No puede bajar servicio ya que no contrato nada
		//Mensaje a traves de la ventana
	}

	@Override
	public void chequeaCambio() {
		if (!this.abonado.getListaDeContrataciones().isEmpty()) {
			this.abonado.setEstado(new ConContratacion(this.abonado));
		}
	}
	
	@Override
	public String toString() {
		return "S/C";
	}
	
	public double valorTotal() {		
		return 0;
	}
}
