package empresa;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;

import Domicilio.Domicilio;
import abonado.Abonado;
import excepciones.AbonadoInexistenteException;
import excepciones.ContratacionInvalidaException;
import excepciones.DomicilioExistenteException;
import excepciones.DomicilioInexistenteException;
import excepciones.DomicilioInexistenteExeption;
import excepciones.DomicilioVinculadoException;
import excepciones.FactoryInvalidoException;
import excepciones.FacturaInexistenteException;
import metodosdepago.DecoratorPago;
import metodosdepago.FactoryPago;
import promo.Promo;
import servicio.Servicio;

/**
 * 
 * @author bruno trinitario,Alan Juares,Juan Basualdo,Juan Olave
 *esta clase es la encargada de manejar nuestro sistema, añadir y quitar abonados, domicilios
 *facturas y contrataciones. junto con la posibilidad de poder clonar determinada factura
 */

public class Empresa {
	private static Empresa instance = null;
	private ArrayList<Abonado> listaAbonado = new ArrayList<Abonado>();
	private ArrayList<Contratacion> listaContrataciones = new ArrayList<Contratacion>();
	private ArrayList<Tecnico> listaTecnico = new ArrayList<Tecnico>();
	private FactoryPago creacion = new FactoryPago();
	private LocalDate fecha = LocalDate.now();
	private MesaDeSolicitudDeTecnicos mesaDeSolicitudDeTecnicos;


	/**
	 * Este metodo garantiza que solo exista una instancia de la clase empresa<br>
	 * <br>
	 */
	public static Empresa getInstance() {
		if (instance == null)
			instance = new Empresa();
		return instance;
	}
	/**
	 * Este metodo agrega un abonado a la lista de abonados.<br>
	 * <br>
	 * <b>Pre</b>: el abonado debe ser distinto de null al igual que tipodepago.<br>
	 * <b>Inv</b>: el abonado a agregar no esta en la lista.<br>
	 * <b>Post</b>: se aniade un abonado a la lista y se crea una factura para el
	 * mismo.<br>
	 * 
	 * @param abonado    es el abonado a agregar.<br>
	 * @param tipodepago va a ser la cadena que indicara el metodo de pago.<br>
	 * @throws FactoryInvalidoException si el tipo de pago fue incorrecto o no
	 *                                  existente.<br>
	 */
	public void agregaAbonado(Abonado abonado, String tipodepago) throws FactoryInvalidoException {
		assert abonado != null : "El abonado debe ser distinto de null";
		assert tipodepago != null : "El tipo de pago debe ser distinto de null";
		listaAbonado.add(abonado);
	}
	/**
	 * Este metodo crea y agrega una factura a la lista de facturas.<br>
	 * <b>Pre</b>: el abonado debe ser distinto de null al igual que tipodepago.<br>
	 * <b>Inv</b>: la factura a agregar no esta en la lista.<br>
	 * <b>Post</b>: se crea una factura para el abonado con su tipo de pago y se
	 * añade a la lista.<br>
	 * 
	 * @param abonado    es el abonado para el cual se crea la factura.<br>
	 * @param tipodepago es el String que indica que metodo de pago va a tener la
	 *                   factura.<br>
	 * @throws FactoryInvalidoException si el tipo de pago fue incorrecto o no
	 *                                  existente.<br>
	 */
	private void crearFactura(Abonado abonado, String tipodepago) throws FactoryInvalidoException {

		assert abonado != null : "El abonado debe ser distinto de null";
		assert tipodepago != null : "El tipo de pago debe ser distinto de null";
		IFactura factura = new Factura(abonado);
		abonado.addFactura(factura);
	}
	public void pagaFactura(Abonado abonado,IFactura factura) {
        factura.setFechaDePago(this.fecha);
        abonado.PagoEstado(factura, fecha);
    }

	/**
	 * Este metodo agrega domicilio un abonado.<br>
	 * <br>
	 * <b>Pre</b>: el Domicilio y el abonado deben ser distintos de
	 * null<br>
	 * <b>Inv</b>: el abonado esta en la lista.<br>
	 * <b>Post</b>: se añade un domicilio al abonado.<br>
	 * 
	 * @param domicilio es el domicilio que vamos a agregar.<br>
	 * @param abonado   indica el abonado que recibe el domicilio nuevo.<br>
	 * @throws DomicilioVinculadoException si el domicilio ya esta previamente
	 *                                     vinculado.<br>
	 * @throws DomicilioExistenteException si el domicilio si el abonado ya posee
	 *                                     ese domicilio.<br>
	 */
	public void agregaDom(Domicilio domicilio, Abonado abonado) throws DomicilioVinculadoException, DomicilioExistenteException {
		assert abonado != null : "El abonado debe ser distinto de null";
		assert domicilio != null : "El domicilio debe ser distinto de null";
		if (abonado.existeDomicilio(domicilio) == false)
			abonado.aniadirDomicilio(domicilio);
		else
			throw new DomicilioExistenteException("El domicilio ya estaba agregado a la lista", domicilio);
	}
	/**
	 * Este metodo quita domicilio de un abonado.<br>
	 * <br>
	 * <b>Pre</b>: el Domicilio y el abonado deben ser distintos de
	 * null.<br>
	 * <b>Inv</b>: el abonado esta en la lista.<br>
	 * <b>Post</b>: se quita el domicilio al abonado.<br>
	 * 
	 * @param domicilio es el domicilio que queremos eliminar.<br>
	 * @param abonado   es el abonado el cual eliminamos el domicilio.<br>
	 * @throws DomicilioInexistenteExeption si el domicilio no existe para ese
	 *                                      abonado.<br>
	 */
	public void QuitarDom(Domicilio domicilio, Abonado abonado) throws DomicilioInexistenteException {
		assert abonado != null : "El abonado debe ser distinto de null";
		assert domicilio != null : "El domicilio debe ser distinto de null";
		if (abonado.existeDomicilio(domicilio))
			abonado.quitarDomicilio(domicilio);
		else
			throw new DomicilioInexistenteException("El domicilio que se quiere eliminar no existe", domicilio);
	}
	/**
	 * Este metodo quita un abonado de la lista.<br>
	 * <br>
	 * <b>Pre</b>: el abonado debe ser disntinto de null.<br>
	 * <b>Inv</b>: el abonado esta en la lista.<br>
	 * <b>Post</b>: se quita el abonado de la lista.<br>
	 * 
	 * @param abonado es el abonado que queremos quitar del sistema.<br>
	 */
	public void quitaAbonado(Abonado abonado) throws AbonadoInexistenteException{
		assert abonado != null : "El abonado debe ser distinto de null";
		if(this.listaAbonado.contains(abonado)==true)
			this.listaAbonado.remove(abonado);
		else
			throw new AbonadoInexistenteException("el Abonado no se encuentra en la lista",abonado);
	}
	

	/**
	 * Este metodo crea una contratacion la añade a la lista y a la facutra
	 * correspondiente.<br>
	 * <br>
	 * <b>Pre</b>: el Domicilio y el abonado deben ser distintos de
	 * null.<br>
	 * <b>Inv</b>: el abonado esta en la lista, y el domicilio esta vinculado al
	 * abonado.<br>
	 * <b>Post</b>: se crea una contratacion la añade a la lista y a la facutra
	 * correspondiente.<br>
	 * 
	 * @param domicilio es el domicilio que pertenecera a la contratacion.<br>
	 * @param abonado   este indicara para que factura sera agregada la
	 *                  contratacion.<br>
	 * @throws ContratacionInvalidaException si el abonado no poseia el domicilio
	 *                                       y/o el domicilio no estaba
	 *                                       vinculado.<br>
	 */
	public void CrearContratacion(Domicilio domicilio, Abonado abonado) throws ContratacionInvalidaException {
		assert abonado != null : "El abonado debe ser distinto de null";
		assert domicilio != null : "El domicilio debe ser distinto de null";
		if (abonado.existeDomicilio(domicilio) == true && domicilio.isAgregado() == true) {
			Contratacion contrato = new Contratacion(domicilio);
			abonado.aniadirContratacion(contrato);
			aniadirContratacion(contrato, abonado);
		} else
			throw new ContratacionInvalidaException("No se pudo crear la contratacion", domicilio, abonado);
	}
	/**Este metodo se encarga de añadir servicios un respectivo domicilio.
	 * <br>
	 * <b>Pre</b>:domicilio y servicio deben ser distintos de null.<br>
	 * <b>Inv</b>:<br>
	 * <b>Post</b>:servicio agregado al domicilio que le corresponda <br>
	 * @param domicilio es el domicilio que pertenece a la contratacion.<br>
	 * @param servicio es el servicio que se desea agregar a un contrato.<br>
	 */
	public void aniadirServicio(Domicilio domicilio,Servicio servicio) {
		assert servicio!= null : "El servicio debe ser distinto de null";
		assert domicilio != null : "El domicilio debe ser distinto de null";
		int i=0;
		while (this.listaContrataciones.get(i).getDomicilio()!=null && this.listaContrataciones.get(i).getDomicilio()!=domicilio)
			i++;
		if(this.listaContrataciones.get(i).getDomicilio()!=null)
			this.listaContrataciones.get(i).agregarServicio(servicio);
	}
	/**Este metodo se encarga de añadir una promo a una contrato.
	 * <br>
	 * <b>Pre</b>:domicilio y promo deben ser distintos de null.<br>
	 * <b>Inv</b>:<br>
	 * <b>Post</b>:servicio agregado al domicilio que le corresponda. <br>
	 * @param domicilio es el domicilio que pertenece a la contratacion. <br>
	 * @param promo es la promo que se desea agregar a un determinado contrato.<br>
	 */
	public void aniadirPromo(Domicilio domicilio,Promo promo) {
		int i=0;
		assert promo != null : "La promo debe ser distinta de null";
		assert domicilio != null : "El domicilio debe ser distinto de null";
		while (this.listaContrataciones.get(i).getDomicilio()!=null && this.listaContrataciones.get(i).getDomicilio()!=domicilio)
			i++;
		if(this.listaContrataciones.get(i).getDomicilio()!=null)
			this.listaContrataciones.get(i).setPromo(promo);
	}

	/**
	 * Este metodo agrega una contratacion a la lista de contrataciones.<br>
	 * <br>
	 * <b>Pre</b>:<br>
	 * <b>Inv</b>:<br>
	 * <b>Post</b>: se añade el contrato de la lista.<br>
	 * 
	 * @param contrato es el contrato que se añade.<br>
	 */
	private void aniadirContratacion(Contratacion contrato, Abonado abonado) {
		this.listaContrataciones.add(contrato);
	}
	/**
	 * Este metodo muestra retorna la factura de cierto abonado.<br>
	 * <br>
	 * <b>Pre</b>:abonado distino de null<br>
	 * <b>Inv</b>:<br>
	 * <b>Post</b>: se añade el contrato de la lista.<br>
	 * 
	 * @param contrato es el contrato que se añade.<br>
	 */
	
	public void actualizaEstado(LocalDate fechaRecibida) throws FactoryInvalidoException {
		LocalDate fechaMasReciente;
		Period periodo;
		LocalDate fechaAux=fecha;

		 for(int i=0;i<listaAbonado.size();i++) {
			 if (!listaAbonado.get(i).getListaDeContrataciones().isEmpty()) {
				 fecha=fechaAux;
				 fechaMasReciente=listaAbonado.get(i).fechaReciente();
				 periodo = Period.between(fechaMasReciente,fechaRecibida);
				 for(int j=0;j<(periodo.getMonths()+periodo.getYears()*12);j++) {
					 fecha=fecha.plusMonths(1);
					 crearFactura(listaAbonado.get(i),"Impago");
				 }
				 listaAbonado.get(i).cambiaEstado();
				 }
		 }
		 fecha=fechaRecibida;
		 for (Abonado abonado : listaAbonado)
			 for (IFactura factura : abonado.getListaDeFacturas()){
				 if (!factura.isPago()) {
					 Period fechaAux2 = Period.between(factura.getFechaDeEmision(),fecha);
					 if (fechaAux2.getMonths()+fechaAux2.getYears()*12 > 0)
						 factura.setInteresPorMora(true);
			 }
		 }

	}
	/**
	 * Devuelve el factory pago<br>
	 * <br>
	 * <b>Pre</b>.<br>
	 * <b>Inv</b>:<br>
	 * <b>Post</b>:<br>
	 * 
	 * @return factory pago <br>
	 */
	
	public FactoryPago getCreacion() {
		return creacion;
	}
	
	/**
	 * Setea el factory pago<br>
	 * <br>
	 * <b>Pre</b>: <br>
	 * <b>Inv</b>: FactoryPago<br>
	 * <b>Post</b>: Se setea el factory pago<br>
	 * 
	 * @param FactoyPago.<br>
	 */
	public void setCreacion(FactoryPago creacion) {
		this.creacion = creacion;
	}

	/**
	 * Se setea la lista de abonados.<br>
	 * <br>
	 * <b>Pre</b>: <br>
	 * <b>Inv</b>: Lista de abonados<br>
	 * <b>Post</b>: Se setea la lista de abonados<br>
	 * 
	 * @param array list de abonados.<br>
	 */
	public void setListaAbonado(ArrayList<Abonado> listaAbonado) {
		this.listaAbonado = listaAbonado;
	}
	
	/**
	 * Setea la lista de contrataciones.<br>
	 * <br>
	 * <b>Pre</b>: <br>
	 * <b>Inv</b>: lista de contrataciones<br>
	 * <b>Post</b>:<br>
	 * 
	 * @param Array list de contrataciones.<br>

	 */
	public void setListaContrataciones(ArrayList<Contratacion> listaContrataciones) {
		this.listaContrataciones = listaContrataciones;
	}

	/**
	 * Devuelve la lista de abonados.<br>
	 * <br>
	 * <b>Pre</b>: <br>
	 * <b>Inv</b>: <br>
	 * <b>Post</b>: Devuelva la lista de abonados<br>
	 * 
	 * @return Array list de abonados.<br>
	 */
	public ArrayList<Abonado> getListaAbonado() {
		return listaAbonado;
	}
	public ArrayList<Contratacion> getListaContrataciones() {
		return listaContrataciones;
	}

	public ArrayList<Tecnico> getListaTecnico() {
		return listaTecnico;
	}
	public void setListaTecnico(ArrayList<Tecnico> listaTecnico) {
		this.listaTecnico = listaTecnico;
	}
	
	public void addTecnico(Tecnico tecnico) {
		this.listaTecnico.add(tecnico);
	}
	
	public void removeTecnico(Tecnico tecnico) {
		this.listaTecnico.remove(tecnico);
	}
	/**
	 * Este metodo clona una determinada factura.<br>
	 * <br>
	 * <b>Pre</b>: la factura deben existir y ser distinta de null.<br>
	 * <b>Inv</b>: la factura esta en la lista.<br>
	 * <b>Post</b>: se clona una determinada factura.<br>
	 * 
	 * @param factura es la factura que vamos a clonar.<br>
	 * @throws CloneNotSupportedException no se pudo clonar la factura.<br>
	 * @return clon determinada factura.<br>
	 */
	public Object ClonarFactura(IFactura factura) throws CloneNotSupportedException {
		IFactura clon = (IFactura) factura.clone();
		return clon;
	}
	public LocalDate getFecha() {
		return fecha;
	}
	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}
	public MesaDeSolicitudDeTecnicos getMesaDeSolicitudDeTecnicos() {
		// TODO Auto-generated method stub
		return this.mesaDeSolicitudDeTecnicos;
	}
	public void setMesaDeSolicitudDeTecnicos(MesaDeSolicitudDeTecnicos mesaDeSolicitudDeTecnicos) {
		this.mesaDeSolicitudDeTecnicos = mesaDeSolicitudDeTecnicos;
	}
	public void eliminarContratacion(Abonado abonado, Contratacion contratacion) {

		abonado.getListaDeContrataciones().remove(contratacion);
		this.listaContrataciones.remove(contratacion);
		abonado.cambiaEstado();
	}
	
	public void eliminarServicio(Contratacion contratacion, Servicio servicio) {
		contratacion.eliminarServicio(servicio);
	}
	public IFactura cambiarMetodoPago(IFactura factura, String tipoDePago) {
		FactoryPago f = new FactoryPago();
		IFactura aux=null;
		try {
			aux = f.getMetodoDePago(factura, tipoDePago);
		} catch (FactoryInvalidoException e) {
			e.printStackTrace();
		}
		return aux;
		
	}
	public void addAbonado(Abonado abonado) {
		this.listaAbonado.add(abonado);	
	}

}