package abonado;

import java.io.Serializable;
import java.time.LocalDate;

import empresa.Contratacion;
import empresa.IFactura;
import excepciones.ContratacionInvalidaException;
import excepciones.FacturaInexistenteException;

public class ConContratacion implements IState, Serializable {

private Fisica abonado;
	
	public ConContratacion(Fisica abonado) {
		this.abonado = abonado;
	}

	@Override
    public void pagarFactura(IFactura factura, LocalDate fechaDePago) throws FacturaInexistenteException { 
        if (abonado.cantidadDeFacturasImpagas()>= 2) 
        chequeaCambio();
    }

	@Override
	public void contratarServicio(Contratacion contrato) {
		abonado.aniadirContratacion(contrato);
		//No cambia de estado
	}

	@Override
	public void bajarServicio(Contratacion contrato) throws ContratacionInvalidaException {
		abonado.eliminaContratacion(contrato);
		if (abonado.listaDeContrataciones.size() == 0)
			abonado.setEstado(new SinContratacion(abonado));
	}
	@Override
	public void chequeaCambio() {
		if(abonado.cantidadDeFacturasImpagas()>1)
			abonado.setEstado(new Moroso(abonado));
		if (this.abonado.getListaDeContrataciones().isEmpty()) {
			this.abonado.setEstado(new SinContratacion(this.abonado));
		}
	}

	@Override
	public String toString() {
		return "C/C";
	}
	
	public double valorTotal() {
		double aux = 0;
		for(Contratacion contratacion :this.abonado.getListaDeContrataciones() )
			aux+=contratacion.getValorTotal();			
		return aux;
	}
	
}
