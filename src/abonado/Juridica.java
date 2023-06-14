package abonado;

import java.time.LocalDate;

import empresa.IFactura;
import empresa.MesaDeSolicitudDeTecnicos;

public class Juridica extends Abonado {

	public Juridica(String nombre, int dni, MesaDeSolicitudDeTecnicos mesa) {
		super(nombre, dni, mesa);
	}
	@Override
	public String toString() {
		return "tipoA: " + tipoAbonado() + super.toString();
	}
	public String tipoAbonado() {
		return "Juridica";
	}
	@Override
	public double valorTotal() {
		double suma = 0;
		int i = 0;
		for (i = 0; i < this.listaDeContrataciones.size(); i++) {
			if (i >= 2) {
				suma += this.listaDeContrataciones.get(i).getValorTotal() * 0.5;
			} else
				suma += this.listaDeContrataciones.get(i).getValorTotal();
		}
		return suma;
	}
	public Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException("la clase abonado juridico no puede ser clonado");
	}
	@Override
	public void cambiaEstado() {
		//no hace nada ya que el tipo juridico no tiene estado
	}
	@Override
	public void PagoEstado(IFactura factura, LocalDate fechaDePago) {
		//no hace nada ya que el tipo juridico no tiene estado
		
	}

	
	
}
