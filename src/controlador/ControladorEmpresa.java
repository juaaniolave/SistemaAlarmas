package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JComboBox;

import Domicilio.Comercio;
import Domicilio.Domicilio;
import Domicilio.Vivienda;
import abonado.Abonado;
import abonado.Fisica;
import abonado.Juridica;
import empresa.Contratacion;
import empresa.Empresa;
import empresa.IFactura;
import empresa.MesaDeSolicitudDeTecnicos;
import empresa.Tecnico;
import excepciones.AbonadoInexistenteException;
import excepciones.ContratacionInvalidaException;
import excepciones.DomicilioExistenteException;
import excepciones.FactoryInvalidoException;
import persistencia.EmpresaDTO;
import persistencia.IPersistencia;
import persistencia.PersistenciaBIN;
import persistencia.UtilPersistencia;
import promo.PromoDorada;
import promo.PromoPlatino;
import servicio.Servicio;
import servicio.ServicioAcompaniamiento;
import servicio.ServicioBoton;
import servicio.ServicioCamara;
import vista.IVista;
import vista.VentanaCrearAbonado;
import vista.VentanaCrearContratacion;
import vista.VentanaCrearServicio;
import vista.VentanaCrearTecnico;
import vista.VentanaPagarFactura;

public class ControladorEmpresa implements ActionListener, Observer {

    private Empresa empresa;
    private IVista vista;
    private VentanaCrearAbonado ventanaCrearAbonado;
    private VentanaCrearTecnico ventanaCrearTecnico;
    private MesaDeSolicitudDeTecnicos mesa;
	private VentanaCrearContratacion ventanaCrearContratacion;
	private VentanaCrearServicio ventanaCrearServicio;
	private VentanaPagarFactura ventanaPagarFactura;

    public ControladorEmpresa(Empresa empresa, IVista vista,MesaDeSolicitudDeTecnicos mesa) {
        this.empresa = empresa;
        this.vista = vista;
        this.vista.setActionListener(this);
    	this.mesa = mesa;
    	mesa.addObserver(this);
        

        // Actualizar la vista con la lista inicial de abonados
        vista.actualizarListaAbonados(empresa.getListaAbonado());
    }
    
    public void addObservable (MesaDeSolicitudDeTecnicos mesa) {
    	this.mesa = mesa;
    	mesa.addObserver(this);
    }
	@Override
	public void update(Observable o, Object arg) throws IllegalArgumentException {

			if(o != mesa) {
				throw new IllegalArgumentException("El objeto no esta siendo observado por"+this);
			}
			else
			{
				String mensaje = (String) arg;
				this.vista.getTextArea_consola().append(mensaje+"\n");
			}
			vista.actualizarListaTecnicos(Empresa.getInstance().getListaTecnico());
	}

    public void agregarAbonado(Abonado abonado) throws FactoryInvalidoException {
        empresa.agregaAbonado(abonado, "Efectivo");
        vista.actualizarListaAbonados(empresa.getListaAbonado());
    }

    public void eliminarAbonado(Abonado abonado) throws AbonadoInexistenteException {
        empresa.quitaAbonado(abonado);
        vista.actualizarListaAbonados(empresa.getListaAbonado());
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getActionCommand().equals("Abrir ventana crear abonado")) {
			
			ventanaCrearAbonado = new VentanaCrearAbonado(this);
			ventanaCrearAbonado.setModal(true);
			ventanaCrearAbonado.setVisible(true);
			
		}
		else if (e.getActionCommand().equals("Agregar abonado")) {
			Abonado abonado = null;
			if((String)this.ventanaCrearAbonado.getComboBox_tipo_de_abonado().getSelectedItem() == "Físico") {
				abonado = new Fisica(this.ventanaCrearAbonado.getNombreAbonado(), Integer.parseInt(this.ventanaCrearAbonado.getDNI()),empresa.getMesaDeSolicitudDeTecnicos());
			}
			else {
				abonado = new Juridica(this.ventanaCrearAbonado.getNombreAbonado(), Integer.parseInt(this.ventanaCrearAbonado.getDNI()),empresa.getMesaDeSolicitudDeTecnicos());							
			}
			if (abonado!=null)
				this.empresa.addAbonado(abonado);
			this.ventanaCrearAbonado.dispose();
			this.vista.actualizarListaAbonados(empresa.getListaAbonado());	
		}
		
		else if(e.getActionCommand().equals("Eliminar abonado")) {
				Abonado abonado = getAbonadoSeleccionado();
				if (abonado!=null) {
				try {
					empresa.quitaAbonado(abonado);
				} catch (AbonadoInexistenteException e1) {
					e1.printStackTrace();
				}
				vista.actualizarListaAbonados(empresa.getListaAbonado());
				if (this.getAbonadoSeleccionado()!=null) {
					vista.actualizaListaFacturas(this.getAbonadoSeleccionado().getListaDeFacturas());
					vista.actualizaListaContrataciones(this.getAbonadoSeleccionado().getListaDeContrataciones());
					if(this.getContratacionSeleccionada()!=null) {
						vista.actualizaListaServicios(getContratacionSeleccionada().getListaServicio());
					}
					else {
						vista.actualizaListaServicios(new ArrayList<Servicio>());
					}
				}
				else {
					vista.actualizaListaFacturas(new ArrayList<IFactura>());
					vista.actualizaListaContrataciones(new ArrayList<Contratacion>());
					vista.actualizaListaServicios(new ArrayList<Servicio>());
				}
			}		
		}
		
		else if (e.getActionCommand().equals("Solicitar Reparación")) {
			Abonado abonadoSeleccionado = empresa.getListaAbonado().get((this.vista.getTable_abonado().getSelectedRow()));
			abonadoSeleccionado.solicitarReparacion();
			vista.actualizarListaTecnicos(Empresa.getInstance().getListaTecnico());
		}
					
		else if(e.getActionCommand().equals("Abrir ventana para crear tecnicos")) {
			this.ventanaCrearTecnico = new VentanaCrearTecnico(this);
			ventanaCrearTecnico.setModal(true);
			ventanaCrearTecnico.setVisible(true);
		}
		else if(e.getActionCommand().equals("Agregar tecnico")) {
			Tecnico tecnico = new Tecnico(this.ventanaCrearTecnico.getNombreTecnico(), Integer.parseInt(this.ventanaCrearTecnico.getDNI()), empresa.getMesaDeSolicitudDeTecnicos());
			empresa.addTecnico(tecnico);
			vista.actualizarListaTecnicos(empresa.getListaTecnico());
			this.ventanaCrearTecnico.dispose();
		}
		else if(e.getActionCommand().equals("Eliminar tecnico")) {
			
			if (vista.getTable_tecnico().getSelectedRow()!= -1 && vista.getTable_tecnico().getSelectedRow() < vista.getListaTecnicos().size() ){
				Tecnico tecnico = (vista.getListaTecnicos().get(vista.getTable_tecnico().getSelectedRow()));
				empresa.removeTecnico(tecnico);
				vista.actualizarListaTecnicos(empresa.getListaTecnico());
			}
		}
		else if(e.getActionCommand().equals("Persistir")) {
			IPersistencia persistencia = new PersistenciaBIN();
			try
	        {
	        
	        	persistencia.abrirOutput("Empresa.bin");
	            System.out.println("Crea archivo escritura");
	            persistencia.escribir(UtilPersistencia.empresaDTOFromEmpresa(empresa));
	            System.out.println("Empresa grabada exitosamente");
	            persistencia.cerrarOutput();
	            System.out.println("Archivo cerrado");
	        
	        } catch (IOException e2)
	        {
	            // TODO Auto-generated catch block
	            System.out.println(e2.getLocalizedMessage());
	        }	
		}
		else if (e.getActionCommand().equals("Despersistir")){
			IPersistencia persistencia = new PersistenciaBIN();
	        try
	        {
	            persistencia.abrirInput("Empresa.bin");
	            System.out.println("Archivo abierto");
	            EmpresaDTO empresaDTO = (EmpresaDTO) persistencia.leer();
	            UtilPersistencia.empresaFromEmpresaDTO(empresaDTO);
	            System.out.println("Empresa recuperada");
	            persistencia.cerrarInput();
	            System.out.println("Archivo cerrado");
	            
	            for (Tecnico tecnico : empresa.getListaTecnico()) {
	            	tecnico.start();
	            }
	            
	            refrescarVista();
	        } catch (IOException e3)
	        {
	            // TODO Auto-generated catch block
	            System.out.println(e3.getMessage());
	        } catch (ClassNotFoundException e4)
	        {
	            // TODO Auto-generated catch block
	            System.out.println(e4.getMessage());
	        }
	        vista.deselectAll();
	        vista.enableButtons();
		}
		
		else if(e.getActionCommand().equals("Abrir ventana crear contratacion")){
			ventanaCrearContratacion = new VentanaCrearContratacion(this);
			ventanaCrearContratacion.setModal(true);
			ventanaCrearContratacion.setVisible(true);
			
		}
		else if (e.getActionCommand().equals("Agregar contratacion")) {
			Domicilio domicilio = null;
			if (this.ventanaCrearContratacion.getComboBox_domicilio_tipo().getSelectedItem() == "Vivienda") {
				 domicilio = new Vivienda(this.ventanaCrearContratacion.getNombreDomicilio());
			}
			else {
				 domicilio = new Comercio(this.ventanaCrearContratacion.getNombreDomicilio());
			}
			try {
				Abonado abonadoSeleccionado = empresa.getListaAbonado().get((this.vista.getTable_abonado().getSelectedRow()));
				try {
					abonadoSeleccionado.aniadirDomicilio(domicilio);
				} catch (DomicilioExistenteException e1) {
					e1.printStackTrace();
				}
				this.empresa.CrearContratacion(domicilio, abonadoSeleccionado);
			} catch (ContratacionInvalidaException e1) {
				e1.printStackTrace();
			}
		
			this.ventanaCrearContratacion.dispose();
			this.vista.actualizaListaContrataciones(empresa.getListaAbonado().get((this.vista.getTable_abonado().getSelectedRow())).getListaDeContrataciones());
			vista.actualizarListaAbonados(empresa.getListaAbonado());
		}

		else if (e.getActionCommand().equals("Eliminar contratacion")) {

			this.empresa.eliminarContratacion(getAbonadoSeleccionado(),getContratacionSeleccionada());
			vista.actualizaListaContrataciones(getAbonadoSeleccionado().getListaDeContrataciones());
			vista.actualizarListaAbonados(empresa.getListaAbonado());
				if (this.getContratacionSeleccionada()!=null) 
					vista.actualizaListaServicios(this.getContratacionSeleccionada().getListaServicio());
				else
					vista.actualizaListaServicios(new ArrayList<Servicio>());
				
			
		}
		
	/*	else if (e.getActionCommand().equals("Clic en tabla de contrataciones")) {
			if (this.getContratacionSeleccionada()!=null) {
				vista.getBtn_servicio_nuevo().setEnabled(true);
				vista.getBtn_contratacion_eliminar().setEnabled(true);
				vista.actualizaListaServicios(this.getContratacionSeleccionada().getListaServicio());
		}
			else {
				vista.getBtn_servicio_nuevo().setEnabled(false);
				vista.getBtn_contratacion_eliminar().setEnabled(false);
				vista.actualizaListaServicios(new ArrayList<Servicio>());
			}
			
		}*/
		
		else if (e.getActionCommand().equals("Cambio metodo de pago")) {
			ArrayList<IFactura> arrayAux = new ArrayList<IFactura>();
			for (IFactura factura : getFacturasSeleccionadas()) {
				factura = this.empresa.cambiarMetodoPago(factura, (String) this.ventanaPagarFactura.getComboBox_tipo_de_pago().getSelectedItem());
				arrayAux.add(factura);	
			}
			this.ventanaPagarFactura.actualizarListaDeFacturas(arrayAux);
		}
		
		else if(e.getActionCommand().equals("Abrir ventana crear servicio")) {
			this.ventanaCrearServicio = new VentanaCrearServicio(this);
			this.ventanaCrearServicio.setModal(true);
			this.ventanaCrearServicio.setVisible(true);
		}
		else if(e.getActionCommand().equals("Abrir ventana pagar factura")) {
			this.ventanaPagarFactura = new VentanaPagarFactura(this, this.getFacturasSeleccionadas(),this.getAbonadoSeleccionado());
			this.ventanaPagarFactura.setModal(true);
			this.ventanaPagarFactura.setVisible(true);
		}
		
		else if(e.getActionCommand().equals("Agregar servicio")) {
			int i;
			if(!this.ventanaCrearServicio.getTextField_acompaniamientos().getText().equals(""))
				for(i=0; i< Integer.parseInt(this.ventanaCrearServicio.getTextField_acompaniamientos().getText());i++) {
					getContratacionSeleccionada().agregarServicio(new ServicioAcompaniamiento());
				}
			if(!this.ventanaCrearServicio.getTextField_botones().getText().equals(""))
				for(i=0; i< Integer.parseInt(this.ventanaCrearServicio.getTextField_botones().getText());i++) {
					getContratacionSeleccionada().agregarServicio(new ServicioBoton());
				}
			if(!this.ventanaCrearServicio.getTextField_camaras().getText().equals(""))
				for(i=0; i< Integer.parseInt(this.ventanaCrearServicio.getTextField_camaras().getText());i++) {
					getContratacionSeleccionada().agregarServicio(new ServicioCamara());
				}
			this.vista.actualizaListaContrataciones(getAbonadoSeleccionado().getListaDeContrataciones());
			vista.actualizaListaServicios(getContratacionSeleccionada().getListaServicio());
			this.ventanaCrearServicio.dispose();
		}
		
		else if(e.getActionCommand().equals("Cambiar fecha")) {
			
	        ZoneId defaultZoneId = ZoneId.systemDefault();
	        Instant instant = vista.getCalendar().getDate().toInstant();
	        LocalDate localDate = instant.atZone(defaultZoneId).toLocalDate();
	        try {
				empresa.actualizaEstado(localDate);
			} catch (FactoryInvalidoException e1) {
				e1.printStackTrace();
			}        
	        if (getAbonadoSeleccionado()!=null) {
	        	vista.actualizaListaFacturas(this.getAbonadoSeleccionado().getListaDeFacturas());	        	
	        }
	        this.vista.actualizarListaAbonados(this.empresa.getListaAbonado());
	        
	        this.vista.actualizarFecha(Empresa.getInstance().getFecha());
		}
		else if (e.getActionCommand().equals("Pagar factura")) {
			for ( IFactura factura : getFacturasSeleccionadas()) {
				this.empresa.pagaFactura(getAbonadoSeleccionado(), factura);
			}
			this.vista.actualizaListaFacturas(this.getAbonadoSeleccionado().getListaDeFacturas());
			this.vista.actualizarListaAbonados(this.empresa.getListaAbonado());
			this.ventanaPagarFactura.dispose();
		}
		else if(e.getActionCommand().equals("Eliminar servicio")) {
			if (this.getContratacionSeleccionada()!=null) {
				for(Servicio aux :vista.getListaServicios()) {
					if (aux.getTipo().equals(getServicioSeleccionado()))
					empresa.eliminarServicio(getContratacionSeleccionada(), aux);
				}
				vista.actualizaListaServicios(this.getContratacionSeleccionada().getListaServicio());
			}
		}
		else if(e.getActionCommand().equals("Cambio de promo")) {
			 JComboBox aux = (JComboBox)e.getSource();
			 if (aux.getSelectedItem().equals("Promo platino")) {
				 this.getContratacionSeleccionada().setPromo(new PromoPlatino());
			 }
			 else if (aux.getSelectedItem().equals("Promo dorada")) {
				 this.getContratacionSeleccionada().setPromo(new PromoDorada());
			 }
			 else if(aux.getSelectedItem().equals("Sin promo")) {
				 if (getContratacionSeleccionada()!=null)
				 this.getContratacionSeleccionada().setPromo(null);
			 }
			 vista.actualizaListaContrataciones(getAbonadoSeleccionado().getListaDeContrataciones());
			 
		}
		
	//	vista.refrescarVista(empresa.getListaAbonado(), empresa.getListaTecnico());
		vista.enableButtons();
	}
	private void refrescarVista() {

		
		if (getContratacionSeleccionada()!=null)
			vista.actualizaListaServicios(this.getContratacionSeleccionada().getListaServicio());
		if (getAbonadoSeleccionado()!=null)
			vista.actualizaListaContrataciones(this.getAbonadoSeleccionado().getListaDeContrataciones());
		if (getAbonadoSeleccionado()!=null)
			vista.actualizaListaFacturas(this.getAbonadoSeleccionado().getListaDeFacturas());
		vista.actualizarListaTecnicos(empresa.getListaTecnico());
		vista.actualizarListaAbonados(empresa.getListaAbonado());
	}
	
	private Abonado getAbonadoSeleccionado() {
		Abonado respuesta=null;
		
		if (vista.getTable_abonado().getSelectedRow()!= -1 && vista.getTable_abonado().getSelectedRow() < vista.getListaAbonados().size() ){
			respuesta = (vista.getListaAbonados().get(vista.getTable_abonado().getSelectedRow()));
		}
		return respuesta;		
	}
	
	private Contratacion getContratacionSeleccionada() {
		Contratacion contratacion = null;
		if (vista.getTable_contratacion().getSelectedRow()!= -1 && vista.getTable_contratacion().getSelectedRow() < vista.getListaContrataciones().size() ){
			contratacion = (vista.getListaContrataciones().get(vista.getTable_contratacion().getSelectedRow()));
		}
		return contratacion;
	}
	
	private ArrayList<IFactura> getFacturasSeleccionadas() {
	    ArrayList<IFactura> facturasSeleccionadas = new ArrayList<>();
	    
	    int[] filasSeleccionadas = vista.getTable_factura().getSelectedRows();
	    for (int fila : filasSeleccionadas) {
	        if (fila >= 0 && fila < vista.getListaFacturas().size()) {
	            IFactura factura = vista.getListaFacturas().get(fila);
	            facturasSeleccionadas.add(factura);
	        }
	    }	    
	    return facturasSeleccionadas;
	}
	private String getServicioSeleccionado() {
		String servicio = null;
		if (vista.getTable_servicio().getSelectedRow()!= -1 && vista.getTable_servicio().getSelectedRow() < vista.getListaServicios().size() ){
			servicio = (String) (vista.getTable_servicio().getModel().getValueAt(vista.getTable_servicio().getSelectedRow(), 0));
		}
		return servicio;
	
	}
}
