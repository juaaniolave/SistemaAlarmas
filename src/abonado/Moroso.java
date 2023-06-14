package abonado;

import java.io.Serializable;
import java.time.LocalDate;

import empresa.Contratacion;
import empresa.IFactura;
import excepciones.ContratacionInvalidaException;

public class Moroso implements IState, Serializable {

private Fisica abonado;
	
	public Moroso(Fisica abonado) {
		super();
		this.abonado = abonado;
		for (IFactura factura : abonado.getListaDeFacturas()) {
			if (!factura.isPago()) {
				
			}
			
		}
	}

	@Override
    public void pagarFactura(IFactura factura, LocalDate fechaDePago) {
        //Paga la factura con un recargo del 30%
        if (abonado.cantidadDeFacturasImpagas()< 2);
        chequeaCambio();
    }

	@Override
	public void contratarServicio(Contratacion contrato) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void bajarServicio(Contratacion contrato) throws ContratacionInvalidaException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void chequeaCambio() {
		if(abonado.cantidadDeFacturasImpagas()<2)
			abonado.setEstado(new ConContratacion(abonado));
		
	}
	@Override
	public String toString() {
		return "Moroso";
	}

	@Override
	public double valorTotal() {
		double aux = 0;
		for(Contratacion contratacion :this.abonado.getListaDeContrataciones() )
			aux+=contratacion.getValorTotal();			
		return aux;
	}
	
	
}
