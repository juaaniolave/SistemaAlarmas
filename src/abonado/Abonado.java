package abonado;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

import Domicilio.Domicilio;
import empresa.Contratacion;
import empresa.Factura;
import empresa.IFactura;
import empresa.MesaDeSolicitudDeTecnicos;
import excepciones.ContratacionInvalidaException;
import excepciones.DomicilioExistenteException;
import excepciones.DomicilioInexistenteException;
/*
 * la clase abonado, la cual tiene 2 variantes (fisica y juridica) es
 * respondable de encapsular sus respectivos domicilios y contrataciones
 * para que a la hora de generar la factura del abonado se poseea toda la informacion necesaria
 */
public abstract class Abonado extends Thread implements Cloneable, Serializable, Runnable {
	protected String nombre;
	protected int dni;
	protected ArrayList<Contratacion> listaDeContrataciones = new ArrayList<Contratacion>();
	protected ArrayList<Domicilio> listaDeDomicilios = new ArrayList<Domicilio>();
	protected ArrayList<IFactura> listaDeFacturas = new ArrayList<IFactura>();
	protected boolean necesitaReparacion;
	protected MesaDeSolicitudDeTecnicos mesa;

	/**
	 * Constructor de la clase <br>
	 * <br>
	 * <b>Pre</b>: Se espera que nombre sea distinto de null y " ".DNI mayor a
	 * 0.<br>
	 * <b>Inv</b>: Nombre y dni.<br>
	 * <b>Post</b>: Se setean los valores de los atributos nombre y dni.<br>
	 */
	public Abonado(String nombre, int dni, MesaDeSolicitudDeTecnicos mesa) {
		assert nombre != null : "El nombre no puede ser null";
		assert !nombre.equals("") : "El nombre no puede estar vacio";
		assert dni > 0 : "El DNI debe ser mayor a 0";
		this.dni = dni;
		this.nombre = nombre;
		this.necesitaReparacion=false;
		this.mesa=mesa;
		this.start();
	}
	/**
	 * <b>Pre</b>:<br>
	 * <b>Inv</b>:<br>
	 * <b>Post</b>: Retorna el tipo de abonado.<br>
	 */
	public abstract String tipoAbonado();

	/**
	 * Recibe un domicilio, chequea si ya estaba agregado y lo agrega. Si ya estaba,
	 * tira excepcion. <br>
	 * <br>
	 * <b>Pre</b>: Se espera que domicilio sea distinto de null.<br>
	 * <b>Inv</b>: Domicilio.<br>
	 * <b>Post</b>: Agrega el domicilio a la lista de domicilios del abonado.<br>
	 */
	public void aniadirDomicilio(Domicilio domicilio) throws DomicilioExistenteException {
		assert domicilio != null : "El domicilo no puede ser null";
		if (domicilio.isAgregado() == false) {
			this.listaDeDomicilios.add(domicilio);
			domicilio.setAgregado(true);
		} else
			throw new DomicilioExistenteException("El domicilio ya estaba agregado a la lista", domicilio);
	}

	/*
	 * Devuelve la lista de contrataciones del abonado
	 */
	public ArrayList<Contratacion> getListaDeContrataciones() {
		return listaDeContrataciones;
	}
	/**
	 * El metodo recibe un tipo abonado y devuelve la suma de los valores de las
	 * contrataciones netas, segun el tipo de abonado, sin descuentos de tipo metodo
	 * de pago.<br>
	 * <br>
	 *
	 * <b>Pre</b>: El abonado debe ser valido y distinto de null. <br>
	 * <b>Inv</b>: La lista de servicios. <br>
	 * <b>Post</b>: Devuelve la sumatoria de los valores.<br>
	 * 
	 * @param abonado indica el tipo de abonado.
	 * @return suma es sumatoria de los valores.
	 */
	public abstract double valorTotal();

	/**
	 * Este metodo añade una contratacion a la lista.<br>
	 * <br>
	 *
	 * <b>Pre</b>: La contratacion debe ser distinta de null.<br>
	 * <b>Inv</b>:<br>
	 * <b>Post</b>:Se añade una contratacion a la lista.<br>
	 * 
	 * @param contrato es la contratacion que aniadimos en la lista.
	 */
	public void aniadirContratacion(Contratacion contrato) {
		assert contrato != null : "El contrato debe ser distinto de null";
		this.listaDeContrataciones.add(contrato);
		this.cambiaEstado();
	}

	/**
	 * <b>Pre</b>: Domicilio debe ser distinto null. <br>
	 * <b>Inv</b>: Domicilio.<br>
	 * <b>Post</b>: Se quita el domicilio de la lista de domicilios.<br>
	 */
	public void quitarDomicilio(Domicilio domicilio) throws DomicilioInexistenteException {
		assert domicilio != null : "El domicilio debe ser distinto de null";
		if (existeDomicilio(domicilio) == true && domicilio.isAgregado() == true) {
			this.listaDeDomicilios.remove(domicilio);
			domicilio.setAgregado(false);
		} else if (existeDomicilio(domicilio) == false)
			throw new DomicilioInexistenteException("Domicilio inexistente en la lista", domicilio);
	}

	@Override
	public String toString() {
		return "|nombre: " + nombre + "|dni: " + dni;
	}

	/**
	 * El metodo recibe un domicilio por parametro y verifica si existe o no,
	 * devolviendo un booleano. <br>
	 * <br>
	 * <b>Pre</b>: El domicilio debe ser distinto de null.<br>
	 * <b>Inv</b>:<br>
	 * <b>Post</b>: Se verifica la existencia del domicilio.<br>
	 * 
	 */
	public boolean existeDomicilio(Domicilio domicilio) {
		assert domicilio != null : "El domicilio debe ser distinto de null";
		return this.listaDeDomicilios.contains(domicilio);
	}
	/**
	 * El metodo recibe un contrato por parametro y verifica si existe o no,
	 * devolviendo un booleano. <br>
	 * <br>
	 * <b>Pre</b>: El contrato debe ser distinto de null.<br>
	 * <b>Inv</b>:<br>
	 * <b>Post</b>: Se verifica la existencia del contrato.<br>
	 * 
	 */
	public boolean existeContratacion(Contratacion contrato) {
		assert contrato != null : "El domicilio debe ser distinto de null";
		return this.listaDeContrataciones.contains(contrato);
	}
	
	public void eliminaContratacion(Contratacion contrato) throws ContratacionInvalidaException {
		assert contrato != null : "El contrato debe ser distinto de null";
		if (existeContratacion(contrato) == true) {
			this.listaDeContrataciones.remove(contrato);

		} 
		else 
		   if (existeContratacion(contrato) == false)
		      throw new ContratacionInvalidaException("Contrato inexistente en la listaDeContratos",null, this);
		this.cambiaEstado();
	}
	

	public LocalDate fechaReciente(){
		 LocalDate fechaMasReciente;
		 
		 if(!getListaDeFacturas().isEmpty()) {
			 fechaMasReciente=getListaDeFacturas().get(0).getFechaDeEmision();
			 for(int k=1;k<getListaDeFacturas().size();k++) {
				 if(getListaDeFacturas().get(k).getFechaDeEmision().isAfter(fechaMasReciente))
					 fechaMasReciente=getListaDeFacturas().get(k).getFechaDeEmision();

			 }
		 }
		 else {
			 fechaMasReciente=getListaDeContrataciones().get(0).getFechaContratacion();
			 for(int p=1;p<getListaDeContrataciones().size();p++) {
				 if(getListaDeContrataciones().get(p).getFechaContratacion().isAfter(fechaMasReciente))
					 fechaMasReciente=getListaDeContrataciones().get(p).getFechaContratacion();
			 }
		 }
		return fechaMasReciente;
	}
	
	public abstract void cambiaEstado();
	public abstract void PagoEstado(IFactura factura,LocalDate fechaDePago);
	/**
	 * El metodo clona el objeto de tipo abonado. <br>
	 * <br>
	 * <b>Pre</b><br>
	 * <b>Inv</b><br>
	 * <b>Post</b>: Se clona el objeto<br>
	 * @throws CloneNotSupportedException si el objeto no puede ser clonable
	 */
	public Object clone() throws CloneNotSupportedException {
		Abonado clon = null;
		clon = (Abonado) super.clone();
		clon.listaDeDomicilios = (ArrayList<Domicilio>) this.listaDeDomicilios.clone();
		clon.listaDeDomicilios.clear();
		for (int i = 0; i < this.listaDeDomicilios.size(); i++)
			clon.listaDeDomicilios.add((Domicilio) this.listaDeDomicilios.get(i).clone());
		
		clon.listaDeContrataciones = (ArrayList<Contratacion>) this.listaDeContrataciones.clone();
		clon.listaDeContrataciones.clear();
		for (int i = 0; i < this.listaDeContrataciones.size(); i++)
			clon.listaDeContrataciones.add((Contratacion) this.listaDeContrataciones.get(i).clone());
		
		clon.listaDeFacturas = (ArrayList<IFactura>) this.listaDeFacturas.clone();
		clon.listaDeFacturas.clear();
		for (int i = 0; i < this.listaDeFacturas.size(); i++)
			clon.listaDeFacturas.add((Factura) this.listaDeFacturas.get(i).clone());
		return clon;
	}
	public int getDni() {
		// TODO Auto-generated method stub
		return this.dni;
	}
	public String getNombre() {
		// TODO Auto-generated method stub
		return this.nombre;
	}
	public ArrayList<Domicilio> getListaDeDomicilios() {
		return listaDeDomicilios;
	}
	public ArrayList<IFactura> getListaDeFacturas() {
		return listaDeFacturas;
	}
	
	public void addFactura (IFactura factura) {
		this.listaDeFacturas.add(factura);
	}
	
	public void run() {
	};

	public boolean isNecesitaReparacion() {
		return necesitaReparacion;
	}
	public void setNecesitaReparacion(boolean necesitaReparacion) {	
		this.necesitaReparacion = necesitaReparacion;
	}
	public void solicitarReparacion() {
		if (this.necesitaReparacion==false) {
			this.setNecesitaReparacion(true);
			this.mesa.solicitarReparacion(this);
		}
	}
	public IState getEstado() {
		// TODO Auto-generated method stub
		return null;
	};
	
	public int cantidadDeFacturasImpagas() {
		int respuesta = 0 ;
		for (IFactura factura : this.getListaDeFacturas()) {
			if (factura.getFechaDePago()==null)
				respuesta++;
		}
		return respuesta;
			
	}
	
}
