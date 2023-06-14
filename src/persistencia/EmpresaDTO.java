package persistencia;

import java.io.Serializable;
import java.util.ArrayList;

import abonado.Abonado;
import empresa.Contratacion;
import empresa.Factura;
import empresa.IFactura;
import empresa.Tecnico;
import metodosdepago.FactoryPago;

public class EmpresaDTO implements Serializable {


	private ArrayList<Abonado> listaAbonado = new ArrayList<Abonado>();
	private ArrayList<Contratacion> listaContrataciones = new ArrayList<Contratacion>();
	private ArrayList<Tecnico> listaTecnico = new ArrayList<Tecnico>();
	private transient FactoryPago creacion = new FactoryPago();
	private int fechaMes;
	private int fechaAnio;
	private int fechaDia;
	
	public int getFechaMes() {
		return fechaMes;
	}
	public void setFechaMes(int fechaMes) {
		this.fechaMes = fechaMes;
	}
	public int getFechaAnio() {
		return fechaAnio;
	}
	public void setFechaAnio(int fechaAnio) {
		this.fechaAnio = fechaAnio;
	}
	public int getFechaDia() {
		return fechaDia;
	}
	public void setFechaDia(int fechaDia) {
		this.fechaDia = fechaDia;
	}
	public ArrayList<Abonado> getListaAbonado() {
		return listaAbonado;
	}
	public void setListaAbonado(ArrayList<Abonado> listaAbonado) {
		this.listaAbonado = listaAbonado;
	}
	public ArrayList<Contratacion> getListaContrataciones() {
		return listaContrataciones;
	}
	public void setListaContrataciones(ArrayList<Contratacion> listaContrataciones) {
		this.listaContrataciones = listaContrataciones;
	}

	public ArrayList<Tecnico> getListaTecnico() {
		return listaTecnico;
	}
	public void setListaTecnico(ArrayList<Tecnico> listaTecnico) {
		this.listaTecnico = listaTecnico;
	}
	public FactoryPago getCreacion() {
		return creacion;
	}
	public void setCreacion(FactoryPago creacion) {
		this.creacion = creacion;
	}
}
