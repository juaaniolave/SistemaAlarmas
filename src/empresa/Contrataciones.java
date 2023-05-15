package empresa;

import java.util.ArrayList;

import Domicilio.Domicilio;
import promo.Promo;
import servicio.Servicio;
/*
 * esta clase determinar las carateristicas de la contratacion, si tiene o no promo,
 * cual/es servicios posee y bajo que domicilio funciona
 */
public class Contrataciones implements Cloneable {
	protected Promo promo = null;
	private ArrayList<Servicio> listaServicio = new ArrayList<Servicio>();
	private Domicilio domicilio;
	public static int counter = 0;
	private int id;

	/**
	 * Este es el contructor de la clase el cual aumenta el id en 1 por cada
	 * instancia.<br>
	 * <br>
	 * <b>Pre</b>: ser distinto de null.<br>
	 * <b>Inv</b>:<br>
	 * <b>Post</b>: se le asigna un valor al atributo.<br>
	 * 
	 * @param domicilio es el valor que se le asignara al atributo
	 */
	public Contrataciones(Domicilio domicilio) {
		assert domicilio != null : "El domicilio debe ser distinto de NULL";
		this.domicilio = domicilio;
		counter++;
		this.id = counter;
	}

	/**
	 * Este metodo agregara un servicio a la lista<br>
	 * <br>
	 * <b>Pre</b>: ser distinto de null.<br>
	 * <b>Inv</b>: la lista de servicios esta inicializada.<br>
	 * <b>Post</b>: se añade el servicio a la lista.<br>
	 * 
	 * @param servicio es el objeto que se aniadira a la lista
	 */
	public void agregarServicio(Servicio servicio) {
		assert servicio != null : "El servicio debe ser distinto de null";
		this.listaServicio.add(servicio);
	}

	/**
	 * Este metodo devolvera la suma de los valores de los servicios agregados junto
	 * con el valor base del domicilio y su valor alterado por una promo <br>
	 * <br>
	 * <b>Pre</b>:<br>
	 * <b>Inv</b>: la lista de servicios esta inicializada.<br>
	 * <b>Post</b>: devuelve la sumatoria de los valores.<br>
	 * 
	 * @return suma la sumatoria de los valores.<br>
	 */
	public double getValorTotal() {
		assert !this.listaServicio.isEmpty() : "La lista de servicios no debe estar vacia";
		int i;
		double suma = this.getValorBase(this.domicilio);
		for (i = 0; i < listaServicio.size(); i++) {
			suma += this.listaServicio.get(i).costo();
		}
		return suma;
	}

	/**
	 * Este metodo devolvera el valor base de un domicilio segun su tipo y su
	 * promo.<br>
	 * <br>
	 * <b>Pre</b>: el domicilio debe ser distinto de null.<br>
	 * <b>Inv</b>: los valores sin promo no varia.<br>
	 * <b>Post</b>: devuelve el valor base del domicilio.<br>
	 * 
	 * @param domicilio es el encargado de determinar que valor dara segun el
	 *                  tipo.<br>
	 * @return aux valor base del domicilio.<br>
	 */
	private double getValorBase(Domicilio domicilio) {
		assert domicilio != null : "El domicilio debe ser distinto de NULL";
		double aux = 0;
		if (promo == null)
			aux = this.domicilio.valorBase();
		else
			aux = promo.aplicarPromo(this.domicilio);
		return aux;
	}

	/**
	 * Este metodo crea la lista de servicios agregados vinculados al domicilio de
	 * la contratacion.<br>
	 * <br>
	 * <b>Pre</b>: la lista no debe ser nula.<br>
	 * <b>Inv</b>: la lista de servicios esta inicializada.<br>
	 * <b>Post</b>: devuelve la lista de servicios agregados.<br>
	 * 
	 * @return aux lista de servicios agregados.<br>
	 */
	public String listadoDeSerivicios() {
		assert !this.listaServicio.isEmpty() : "La lista de servicios no debe estar vacia";
		String aux = "";
		for (int i = 0; i < listaServicio.size(); i++) {
			aux += this.listaServicio.get(i).getTipo() + "\n";
		}
		return aux;

	}

	/**
	 * Este metodo permite aniadir un tipo de promo a la contratacion.<br>
	 * <br>
	 * <b>Pre</b>: la promo no debe ser nula y debe existir.<br>
	 * <b>Inv</b>:<br>
	 * <b>Post</b>: setea el atributo promo.<br>
	 * 
	 * @param promo es el valor que se le dara al atributo.<br>
	 */
	public void setPromo(Promo promo) {
		this.promo = promo;
	}

	/**
	 * El metodo generara la descripcion de la contratacion, brindando informacion
	 * como el tipo de abonado, su nombre y dni, el id de la contratacion, el
	 * domicilio, su tipo y promo y el valor total de la contratacion.<br>
	 * <br>
	 * <b>Pre</b>:<br>
	 * <b>Inv</b>:<br>
	 * <b>Post</b>: devuelve un String con la descripcion.<br>
	 * 
	 * @return aux descripcion.<br>
	 */
	public String descripcion() {
		String aux = "";
		aux += "ID=" + this.id + " \n";
		aux += this.domicilio.toString() + "'|tipo: " + this.domicilio.getTipoDom() + "|promo: " + this.promo + "\n";
		aux += this.listadoDeSerivicios();
		aux += "Valor final del domicilio:" + this.getValorTotal() + "\n";
		return aux;
	}

	/**
	 * <b>Pre</b>:<br>
	 * <b>Inv</b>:<br>
	 * <b>Post</b>: retorna la id de la contratacion.<br>
	 */
	public int getId() {
		return id;
	}
	/**
	 * <b>Pre</b>:<br>
	 * <b>Inv</b>:<br>
	 * <b>Post</b>: retorna el domicilio de la contratacion.<br>
	 */
	public Domicilio getDomicilio() {
		return this.domicilio;
	}
	/**
	 * Es el metodo encargado de la clonacion de la contratacion.<br>
	 * <br>
	 * <b>Pre</b>:<br>
	 * <b>Inv</b>:<br>
	 * <b>Post</b>: devuelve un clon.<br>
	 * 
	 * @throws CloneNotSupportedException si algun atributo de tipo objeto no fuera
	 *                                    clonable.<br>
	 * @return clon un clon de la contratacion.<br>
	 */
	public Object clone() throws CloneNotSupportedException {
		Contrataciones clon = (Contrataciones) super.clone();
		clon.domicilio = (Domicilio) this.domicilio.clone();
		if (this.promo!=null)
			clon.promo = (Promo) this.promo.clone();
		else
			clon.promo=null;
		clon.listaServicio = (ArrayList<Servicio>) this.listaServicio.clone();
		clon.listaServicio.clear();
		for (int i = 0; i < this.listaServicio.size(); i++) {
			clon.listaServicio.add((Servicio) this.listaServicio.get(i).clone());
		}
		return clon;
	}
}