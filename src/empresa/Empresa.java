package empresa;

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
 *esta clase es la encargada de manejar nuestro sistema, aniadir y quitar abonados, domicilios
 *facturas y contrataciones. junto con la posibilidad de poder clonar determinada factura
 */

public class Empresa {
	private static Empresa instance = null;
	private ArrayList<Abonado> listaEmpresa = new ArrayList<Abonado>();
	private ArrayList<Contrataciones> listaContrataciones = new ArrayList<Contrataciones>();
	private ArrayList<Factura> listaFactura = new ArrayList<Factura>();
	private FactoryPago creacion = new FactoryPago();

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
		listaEmpresa.add(abonado);
		crearFactura(abonado, tipodepago);
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
		DecoratorPago aux = creacion.getMetodoDePago(abonado, tipodepago);
		Factura factura = new Factura(aux);
		aniadirFactura(factura);
	}
	/**
	 * Este metodo cambia el metodo de pago de un abonado.<br>
	 * <br>
	 * <b>Pre</b>: la factura debe ser disntinto de null. El abonado debe ser
	 * distinto de null al igual que tipodepago.<br>
	 * <b>Inv</b>: la factura y el abonado estan en las listas.<br>
	 * <b>Post</b>: se actualiza el metodo de pago de la factura del abonado.<br>
	 * 
	 * @param factura    es la factura que cambiara su metodo de pago.<br>
	 * @param abonado    es el abonado de la factura.<br>
	 * @param tipodepago el metodo de pago nuevo para la factura.<br>
	 * @throws FactoryInvalidoException si el tipo de pago fue incorrecto o no
	 *                                  existente.<br>
	 */
	public void cambiarMetodoPago(Factura factura, Abonado abonado, String tipodepago) throws FactoryInvalidoException {
		DecoratorPago aux = creacion.getMetodoDePago(abonado, tipodepago);
		factura.setAbonado(aux);
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
		if(this.listaEmpresa.contains(abonado)==true)
			this.listaEmpresa.remove(abonado);
		else
			throw new AbonadoInexistenteException("el Abonado no se encuentra en la lista",abonado);
	}

	/**
	 * Este metodo quita una factura de la lista.<br>
	 * <br>
	 * <b>Pre</b>: la factura debe ser distinta de null.<br>
	 * <b>Inv</b>: la factura esta en la lista.<br>
	 * <b>Post</b>: se quita la factura de la lista.<br>
	 * 
	 * @param factura es la factura que queremos quitar del sistema.<br>
	 */
	public void EliminarFactura(Factura factura)throws FacturaInexistenteException {
		assert factura != null : "La factura debe ser distinto de null";
		if(this.listaFactura.contains(factura))
			this.listaFactura.remove(factura);
		else
			throw new FacturaInexistenteException("Factura inexistente",factura);		
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
			Contrataciones contrato = new Contrataciones(domicilio);
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
	 * Este metodo agrega una factura a la lista de abonados.<br>
	 * <br>
	 * <b>Pre</b>: la factura debe ser distinta de null.<br>
	 * <b>Inv</b>: la factura a agregar no esta en la lista.<br>
	 * <b>Post</b>: se añade una factura a la lista.<br>
	 * 
	 * @param factura es la factura nueva que vamos a añadir.<br>
	 */
	private void aniadirFactura(Factura factura) {
		assert factura!=null:"La factura debe ser distinta de null";
		this.listaFactura.add(factura);
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
	private void aniadirContratacion(Contrataciones contrato, Abonado abonado) {
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
	public Factura getFactura(Abonado abonado) {
		int i=0;
		assert abonado != null : "El abonado debe ser distinto de null";
		
		while (this.listaFactura.get(i).getAbonado().getAbonadotype()!=abonado)
			i++;
		if (this.listaFactura.get(i)!=null)
			return this.listaFactura.get(i);
		else
			return null;
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
	public Object ClonarFactura(Factura factura) throws CloneNotSupportedException {
		Factura clon = (Factura) factura.clone();
		return clon;
	}
}