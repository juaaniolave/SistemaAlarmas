package vista;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JCalendar;

import abonado.Abonado;
import abonado.Fisica;
import abonado.Juridica;
import empresa.Contratacion;
import empresa.Factura;
import empresa.IFactura;
import empresa.Tecnico;
import servicio.Servicio;

public class VistaEmpresa extends JFrame implements KeyListener, IVista, MouseListener {

	private ActionListener actionListener;
    private ArrayList<Abonado> listaAbonados = new ArrayList<Abonado>();
	private ArrayList<Tecnico> listaTecnicos = new ArrayList<Tecnico>();
	private ArrayList<Contratacion> listaContrataciones = new ArrayList<Contratacion>();
	private ArrayList<Servicio> listaServicios = new ArrayList<Servicio>();
	private ArrayList<IFactura> listaFacturas = new ArrayList<IFactura>();
    private JTable table_abonado;
	private JButton btn_abonado_nuevo;
	private JButton btn_abonado_eliminar;
	private JScrollPane scrollPane_abonado;
	private JButton btn_contratacion_nuevo;
	private JButton btn_servicio_nuevo;
	private JButton btn_servicio_eliminar;
	private JButton btn_contratacion_eliminar;
	private JLabel lbl_abonado;
	private JLabel lbl_domicilio;
	private JLabel lbl_servicio;
	private JLabel lbl_factura;
	private JLabel lbl_tecnicos;
	private JScrollPane scrollPane_contrataciones;
	private JScrollPane scrollPane_servicio;
	private JScrollPane scrollPane_factura;
	private JScrollPane scrollPane_Tecnico;
	private JButton btn_tecnico_nuevo;
	private JButton btn_tecnico_eliminar;
	private JPanel panel_calendario;
	private JLabel lbl_calendario;
	private JTable table_tecnico;	
	private JTable table_factura;
	private JTable table_servicio;
	private JButton btn_calendario_simular_fecha;
	private JButton btn_guardar;
	private JButton btn_cargar;
	private JScrollPane scrollPane_consola;
	private JTextArea textArea_consola;
	private JTable table_contrataciones;
	private JButton btn_factura_pagar_factura;
	private JButton btn_abonado_solicitarReparacion;
	private JCalendar calendar;
	private JComboBox comboBox_promo;
	private DefaultTableModel model_contrataciones;
	private DefaultTableModel model_servicio;
	private DefaultTableModel model_tecnico;
	private DefaultTableModel model_factura;
	private DefaultTableModel model_abonado;
	private JLabel lblNewLabel;
	private JLabel lbl_fecha;
	
    public JTextArea getTextArea_consola() {
		return textArea_consola;
	}

	@SuppressWarnings({ "serial", "unchecked", "rawtypes" })
	public VistaEmpresa() {

    	setTitle("MainFrame");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //pack();
        setSize(1024, 768);
        setLocationRelativeTo(null);
        getContentPane().setLayout(null);
        
        scrollPane_abonado = new JScrollPane();
        scrollPane_abonado.setBounds(50, 66, 205, 408);
        getContentPane().add(scrollPane_abonado);
        
        table_abonado = new JTable();
        
        model_abonado = new DefaultTableModel(
            	new Object[][] {

            	},
            	new String[] {
            		"Nombre", "Dni", "Estado"
            	}
            ) {
            	boolean[] columnEditables = new boolean[] {
            		false, false, false
            	};
            	public boolean isCellEditable(int row, int column) {
            		return columnEditables[column];
            	}
            };
        
        table_abonado.setModel(model_abonado);
        table_abonado.getColumnModel().getColumn(0).setResizable(false);
        table_abonado.getColumnModel().getColumn(1).setPreferredWidth(80);
        table_abonado.getColumnModel().getColumn(2).setPreferredWidth(70);
        scrollPane_abonado.setViewportView(table_abonado);
        table_abonado.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        table_abonado.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseReleased(MouseEvent e) {
        		
        			if (getAbonadoSeleccionado()!=null) {
        				if ((getAbonadoSeleccionado() instanceof Fisica) &&(boolean)getAbonadoSeleccionado().getEstado().toString().equals("Moroso")) {
        					btn_contratacion_nuevo.setEnabled(false);
            				btn_contratacion_eliminar.setEnabled(false);
        				}
        				else {
        					btn_contratacion_nuevo.setEnabled(true);
        				}
        				btn_abonado_eliminar.setEnabled(true);
        				btn_abonado_solicitarReparacion.setEnabled(true);
        				actualizaListaContrataciones(getAbonadoSeleccionado().getListaDeContrataciones());
        				actualizaListaFacturas(getAbonadoSeleccionado().getListaDeFacturas());
        		}
        			else {
        				btn_contratacion_nuevo.setEnabled(false);
        				btn_abonado_eliminar.setEnabled(false);
        				btn_abonado_solicitarReparacion.setEnabled(false);
        				actualizaListaContrataciones(new ArrayList<Contratacion>());
        			}
        			actualizaListaServicios(new ArrayList<Servicio>());
        			table_contrataciones.clearSelection();
        			table_factura.clearSelection();
        			table_servicio.clearSelection();
        			deselectAllexceptAbonado();
        			enableButtons();
        			
        	}
        	
        });
        
        btn_abonado_nuevo = new JButton("Agregar");
        this.btn_abonado_nuevo.setActionCommand("Abrir ventana crear abonado");
        
        
        btn_abonado_nuevo.setBounds(50, 485, 89, 23);
        getContentPane().add(btn_abonado_nuevo);
        
        btn_abonado_eliminar = new JButton("Eliminar");
        btn_abonado_eliminar.setEnabled(false);
        btn_abonado_eliminar.setActionCommand("Eliminar abonado");
        btn_abonado_eliminar.setBounds(166, 485, 89, 23);
        getContentPane().add(btn_abonado_eliminar);
        
        btn_contratacion_nuevo = new JButton("Agregar");
        btn_contratacion_nuevo.setEnabled(false);
        btn_contratacion_nuevo.setActionCommand("Abrir ventana crear contratacion");
        btn_contratacion_nuevo.setBounds(275, 238, 89, 23);
        getContentPane().add(btn_contratacion_nuevo);
        
        btn_servicio_nuevo = new JButton("Agregar");
        btn_servicio_nuevo.setActionCommand("Abrir ventana crear servicio");
        btn_servicio_nuevo.setEnabled(false);
        btn_servicio_nuevo.setBounds(275, 485, 89, 23);
        getContentPane().add(btn_servicio_nuevo);
        
        btn_servicio_eliminar = new JButton("Eliminar");
        btn_servicio_eliminar.setActionCommand("Eliminar servicio");
        btn_servicio_eliminar.setEnabled(false);

        btn_servicio_eliminar.setBounds(385, 485, 89, 23);
        getContentPane().add(btn_servicio_eliminar);
        
        btn_contratacion_eliminar = new JButton("Eliminar");
        btn_contratacion_eliminar.setEnabled(false);
        btn_contratacion_eliminar.setActionCommand("Eliminar contratacion");
        btn_contratacion_eliminar.setBounds(385, 238, 89, 23);
        getContentPane().add(btn_contratacion_eliminar);
        
        lbl_abonado = new JLabel("Abonados");
        lbl_abonado.setBounds(103, 41, 114, 14);
        getContentPane().add(lbl_abonado);
        
        lbl_domicilio = new JLabel("Contrataciones");
        lbl_domicilio.setBounds(342, 41, 114, 14);
        getContentPane().add(lbl_domicilio);
        
        lbl_servicio = new JLabel("Servicios contratados");
        lbl_servicio.setBounds(316, 315, 114, 14);
        getContentPane().add(lbl_servicio);
        
        lbl_factura = new JLabel("Facturas");
        lbl_factura.setBounds(572, 41, 114, 14);
        getContentPane().add(lbl_factura);
        
        lbl_tecnicos = new JLabel("Tecnicos");
        lbl_tecnicos.setBounds(867, 41, 114, 14);
        getContentPane().add(lbl_tecnicos);
        
        scrollPane_contrataciones = new JScrollPane();
        scrollPane_contrataciones.setBounds(275, 66, 199, 146);
        getContentPane().add(scrollPane_contrataciones);
        
        table_contrataciones = new JTable();
        table_contrataciones.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseReleased(MouseEvent e) {
        			if (getContratacionSeleccionada()!=null) {
        				actualizaListaServicios(getContratacionSeleccionada().getListaServicio());
        		}
        			else {
        				actualizaListaServicios(new ArrayList<Servicio>());
        			}
        			enableButtons();
        	}
        		
        	}
        );
        model_contrataciones = new DefaultTableModel(
            	new Object[][] {
            	},
            	new String[] {
            		"Domicilio", "Tipo", "Promo", "Valor total"
            	}
            ) {
            	boolean[] columnEditables = new boolean[] {
            		false, false, false, false
            	};
            	public boolean isCellEditable(int row, int column) {
            		return columnEditables[column];
            	}
            
            };
        table_contrataciones.setModel(model_contrataciones);
        table_contrataciones.getColumnModel().getColumn(0).setPreferredWidth(88);
        table_contrataciones.getColumnModel().getColumn(2).setPreferredWidth(62);
        table_contrataciones.getColumnModel().getColumn(3).setPreferredWidth(70);
        table_contrataciones.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        scrollPane_contrataciones.setViewportView(table_contrataciones);
               
        scrollPane_servicio = new JScrollPane();
        scrollPane_servicio.setBounds(275, 340, 199, 134);
        getContentPane().add(scrollPane_servicio);
        
        table_servicio = new JTable();
        table_servicio.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseReleased(MouseEvent e) {
        		enableButtons();
        	}});
        
        model_servicio = new DefaultTableModel(
            	new Object[][] {
            	},
            	new String[] {
            		"Servicio", "Cantidad"
            	}
            ) {
            	boolean[] columnEditables = new boolean[] {
            		false, false
            	};
            	public boolean isCellEditable(int row, int column) {
            		return columnEditables[column];
            	}
            };
        
        table_servicio.setModel(model_servicio);
        table_servicio.getColumnModel().getColumn(0).setResizable(false);
        table_servicio.getColumnModel().getColumn(1).setResizable(false);
        table_servicio.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        scrollPane_servicio.setViewportView(table_servicio);
        
        scrollPane_factura = new JScrollPane();
        scrollPane_factura.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseReleased(MouseEvent e) {
        		enableButtons();
        	}
        });
        scrollPane_factura.setBounds(484, 66, 246, 408);
        getContentPane().add(scrollPane_factura);
        
        table_factura = new JTable();
        table_factura.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseReleased(MouseEvent e) {
        		enableButtons();

        	}
        });
        
		model_factura = new DefaultTableModel(
	        	new Object[][] {

	        	},
	        	new String[] {
	        		"Fecha emision", "Monto", "Estado","Vencida"
	        	}
	        ) {
	        	boolean[] columnEditables = new boolean[] {
	        		false, false, false, false
	        	};
	        	public boolean isCellEditable(int row, int column) {
	        		return columnEditables[column];
	        	}
	        };
        table_factura.setModel(model_factura);
        table_factura.getColumnModel().getColumn(0).setPreferredWidth(80);
        table_factura.getColumnModel().getColumn(3).setResizable(false);
        scrollPane_factura.setViewportView(table_factura);
        
        scrollPane_Tecnico = new JScrollPane();
        scrollPane_Tecnico.setBounds(795, 66, 203, 146);
        getContentPane().add(scrollPane_Tecnico);
        
        table_tecnico = new JTable();
        table_tecnico.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		enableButtons();
        	}
        });
        model_tecnico = new DefaultTableModel(
            	new Object[][] {
            	},
            	new String[] {
            		"Nombre", "Dni", "Disponiblidad"
            	}
            ) {
            	boolean[] columnEditables = new boolean[] {
            		false, false, false
            	};
            	public boolean isCellEditable(int row, int column) {
            		return columnEditables[column];
            	}
            };
        table_tecnico.setModel(model_tecnico);
        table_tecnico.getColumnModel().getColumn(0).setResizable(false);
        table_tecnico.getColumnModel().getColumn(1).setResizable(false);
        table_tecnico.getColumnModel().getColumn(2).setResizable(false);
        table_tecnico.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        scrollPane_Tecnico.setViewportView(table_tecnico);
        

        
        btn_tecnico_nuevo = new JButton("Agregar");
        btn_tecnico_nuevo.setActionCommand("Abrir ventana para crear tecnicos");
        btn_tecnico_nuevo.setBounds(795, 223, 89, 23);
        getContentPane().add(btn_tecnico_nuevo);
        
        btn_tecnico_eliminar = new JButton("Eliminar");
        btn_tecnico_eliminar.setActionCommand("Eliminar tecnico");
        btn_tecnico_eliminar.setBounds(909, 223, 89, 23);
        getContentPane().add(btn_tecnico_eliminar);
        
        panel_calendario = new JPanel();
        panel_calendario.setBounds(752, 288, 246, 186);
        getContentPane().add(panel_calendario);
        
        calendar = new JCalendar();
        panel_calendario.add(calendar);
        
        lbl_calendario = new JLabel("Calendario");
        lbl_calendario.setBounds(867, 272, 84, 14);
        getContentPane().add(lbl_calendario);
        
        btn_calendario_simular_fecha = new JButton("Simular fecha");
        btn_calendario_simular_fecha.setActionCommand("Cambiar fecha");
        btn_calendario_simular_fecha.setBounds(802, 485, 147, 23);
        getContentPane().add(btn_calendario_simular_fecha);
        
        btn_guardar = new JButton("Guardar");
        btn_guardar.setActionCommand("Persistir");
        btn_guardar.setBounds(806, 0, 89, 23);
        getContentPane().add(btn_guardar);
        
        btn_cargar = new JButton("Cargar");
        btn_cargar.setActionCommand("Despersistir");
        btn_cargar.setBounds(909, 0, 89, 23);
        getContentPane().add(btn_cargar);
        
        scrollPane_consola = new JScrollPane();
        scrollPane_consola.setBounds(10, 548, 988, 160);
        getContentPane().add(scrollPane_consola);
        
        textArea_consola = new JTextArea();
        textArea_consola.setEditable(false);
        scrollPane_consola.setViewportView(textArea_consola);
        textArea_consola.setColumns(10);
        
        btn_abonado_solicitarReparacion = new JButton("Solicitar Reparación");
        btn_abonado_solicitarReparacion.setEnabled(false);
        btn_abonado_solicitarReparacion.setActionCommand("Solicitar Reparación");
        btn_abonado_solicitarReparacion.setBounds(50, 514, 205, 23);
        getContentPane().add(btn_abonado_solicitarReparacion);
        
        btn_factura_pagar_factura = new JButton("Pagar factura");
        btn_factura_pagar_factura.setEnabled(false);
        btn_factura_pagar_factura.setActionCommand("Abrir ventana pagar factura");
        btn_factura_pagar_factura.setBounds(507, 485, 205, 23);
        getContentPane().add(btn_factura_pagar_factura);
        
        comboBox_promo = new JComboBox();
        comboBox_promo.setEnabled(false);
        comboBox_promo.addItemListener(new ItemListener() {
        	public void itemStateChanged(ItemEvent e) {
        		ActionEvent actionEvent = new ActionEvent(comboBox_promo, ActionEvent.ACTION_PERFORMED ,"Cambio de promo");
        		actionListener.actionPerformed(actionEvent);
        	}
        });
        
        comboBox_promo.setModel(new DefaultComboBoxModel(new String[] {"Sin promo", "Promo platino", "Promo dorada"}));
        comboBox_promo.setBounds(316, 282, 114, 22);
        getContentPane().add(comboBox_promo);
        
        lblNewLabel = new JLabel("Fecha de hoy:");
        lblNewLabel.setBounds(368, 4, 88, 14);
        getContentPane().add(lblNewLabel);
        
        lbl_fecha = new JLabel(LocalDate.now().toString());
        lbl_fecha.setBounds(451, 4, 67, 14);
        getContentPane().add(lbl_fecha);
        
        setVisible(true);
        
    }

    
	
	
	public JComboBox getComboBox_promo() {
		return comboBox_promo;
	}

	
 	
	@Override
	public void setActionListener(ActionListener actionListener) {
		this.actionListener=actionListener;
		this.btn_abonado_nuevo.addActionListener(actionListener);
		this.btn_tecnico_nuevo.addActionListener(actionListener);
		this.btn_guardar.addActionListener(actionListener);
		this.btn_cargar.addActionListener(actionListener);
		this.btn_tecnico_eliminar.addActionListener(actionListener);
		this.btn_abonado_eliminar.addActionListener(actionListener);
		this.btn_contratacion_nuevo.addActionListener(actionListener);
		this.btn_abonado_solicitarReparacion.addActionListener(actionListener);
		this.btn_contratacion_eliminar.addActionListener(actionListener);
		this.btn_servicio_nuevo.addActionListener(actionListener);
		this.btn_calendario_simular_fecha.addActionListener(actionListener);
		this.btn_factura_pagar_factura.addActionListener(actionListener);
		this.btn_servicio_eliminar.addActionListener(actionListener);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

    public ArrayList<Abonado> getListaAbonados() {
		return listaAbonados;
	}

	public ArrayList<Tecnico> getListaTecnicos() {
		return listaTecnicos;
	}

	public JTable getTable_tecnico() {
		return table_tecnico;
	}

	public JTable getTable_factura() {
		return table_factura;
	}

	public JTable getTable_servicio() {
		return table_servicio;
	}
	public JTable getTable_abonado() {
		return table_abonado;
	}

	@Override
	public void actualizaListaContrataciones(ArrayList<Contratacion> listaDeContrataciones) {
        int aux = this.table_contrataciones.getSelectedRow();
        if (this.getContratacionSeleccionada()!=null) {
        	this.comboBox_promo.setEnabled(true);
        }
        else {
        	this.comboBox_promo.setEnabled(false);
        }
		this.listaContrataciones.clear();
        for (Contratacion contratacion : listaDeContrataciones) {
            this.listaContrataciones.add(contratacion);
        }
        actualizarTablaDeContrataciones();
        if (aux >= 0 && aux < this.listaContrataciones.size())
        	this.table_contrataciones.setRowSelectionInterval(aux, aux);
	}

	
	private void actualizarTablaDeContrataciones() {
	    model_contrataciones.setRowCount(0);
	    for (Contratacion contratacion : this.listaContrataciones) {	 
	    	Object[] fila = {contratacion.getDomicilio().getNombre() , contratacion.getDomicilio().getTipoDom().charAt(0) ,contratacion.getPromo(), contratacion.getValorTotal()};
	    	model_contrataciones.addRow(fila);
	    }
	}
    

	
	public void actualizaListaServicios(ArrayList<Servicio> listaServicios) {
        int aux = this.table_servicio.getSelectedRow();
        if(this.getContratacionSeleccionada() == null || getContratacionSeleccionada().getPromo() == null)
			this.comboBox_promo.setSelectedItem("Sin promo");
        else if (this.getContratacionSeleccionada().getPromo().toString() == "Platino")
        	this.comboBox_promo.setSelectedItem("Promo platino");
        else if(this.getContratacionSeleccionada().getPromo().toString() == "Dorada" )
        	this.comboBox_promo.setSelectedItem("Promo dorada");
		this.listaServicios.clear();
        for (Servicio servicio : listaServicios) {
            this.listaServicios.add(servicio);
        }
        actualizarTablaDeServicios();
        if (aux >= 0 && aux < this.table_servicio.getRowCount())
        	this.table_servicio.setRowSelectionInterval(aux, aux);
    }
	
	private void actualizarTablaDeServicios() {
		
		this.model_servicio.setRowCount(0);
	    int cantidadDeCamaras = 0;
	    int cantidadDeBotones = 0;
	    int cantidadDeAcompaniamientos = 0;
	    for (Servicio servicio : this.listaServicios) {
	    	
	    	if (servicio.getTipo().equals("Acompaniamiento"))
	    		cantidadDeAcompaniamientos++;
	    	else if (servicio.getTipo().equals("Camara"))
	    		cantidadDeCamaras++;
	    	else if (servicio.getTipo().equals("Boton"))
	    		cantidadDeBotones++;
	    } 

	    	if (cantidadDeAcompaniamientos > 0) {	
		    	Object[] fila = {"Acompaniamiento" , cantidadDeAcompaniamientos};
		    	model_servicio.addRow(fila); 	

	    }
	    if (cantidadDeBotones > 0) {	
	    	Object[] fila = {"Boton" , cantidadDeBotones};
	    	model_servicio.addRow(fila); ;	

	    }
	    if (cantidadDeCamaras > 0) {	
	    	Object[] fila = {"Camara" , cantidadDeCamaras};
	    	model_servicio.addRow(fila); 	
	    }

	}
	
	public void actualizarListaAbonados(ArrayList<Abonado> listaAbonados) {
        int aux = this.table_abonado.getSelectedRow();
		this.listaAbonados.clear();
        for (Abonado abonado : listaAbonados) {
            this.listaAbonados.add(abonado);
        }
        actualizarTablaDeAbonados();
        if (aux >= 0 && aux < this.listaAbonados.size())
        	this.table_abonado.setRowSelectionInterval(aux, aux);
    }
	private void actualizarTablaDeAbonados() {
		
		this.model_abonado.setRowCount(0);
	    for (Abonado abonado : this.listaAbonados) {

	    	if (abonado instanceof Juridica) {
		    	Object[] fila = {abonado.getNombre() , abonado.getDni(), "N/A"};
		    	model_abonado.addRow(fila); 
	    	}
	    	else {
		    	Object[] fila = {abonado.getNombre() , abonado.getDni(), ((Fisica) abonado).getEstado().toString()};
		    	model_abonado.addRow(fila); 
	    	}
	    	
			
	    }

	}
	
	@Override
	public void actualizarListaTecnicos(ArrayList<Tecnico> listaTecnicos) {
        int aux = this.table_tecnico.getSelectedRow();
		this.listaTecnicos.clear();
        for (Tecnico tecnico : listaTecnicos) {
            this.listaTecnicos.add(tecnico);
        }
        actualizarTablaDeTecnicos();	
        if (aux >= 0 && aux < this.listaTecnicos.size())
        	this.table_tecnico.setRowSelectionInterval(aux, aux);
	}

	private void actualizarTablaDeTecnicos() {
		
		this.model_tecnico.setRowCount(0);
	    for (Tecnico tecnico : this.listaTecnicos) {
	    	if (tecnico.getAbonado()!=null) {
		    	Object[] fila = {tecnico.getNombre() , tecnico.getDni(), "Reparando"};
		    	model_tecnico.addRow(fila); 
	    	}
	    	else {
	    		Object[] fila = {tecnico.getNombre() , tecnico.getDni(), "Disponible"};
	    		model_tecnico.addRow(fila); 
	    	}
		    	
	    }
	};
	
	public void actualizaListaFacturas(ArrayList<IFactura> listaFacturas) {
		int[] aux = this.table_factura.getSelectedRows();
		this.listaFacturas.clear();
        for (IFactura factura : listaFacturas) {
            this.listaFacturas.add(factura);
            
        }
        actualizarTablaDeFacturas();
        if (aux!=null)
        	for (int intAux : aux)
        		if(this.table_factura.getRowCount() >= intAux)
        		this.table_factura.setRowSelectionInterval(intAux, intAux);
	}
	
	
	private void actualizarTablaDeFacturas() {
		this.model_factura.setRowCount(0);
	    for (IFactura factura : this.listaFacturas) {
	    	String estado;
	    	String estaMorosa;
	    	if (factura.getFechaDePago()==null) 
	    		estado="Impaga";	    	
	    	else 
		    	estado="Paga";		      	
	    	if (factura.isInteresPorMora()) 
	    		estaMorosa="Si";	    	
	    	else 
	    		estaMorosa="No";
	    	Object[] fila = {factura.getFechaDeEmision() , factura.getMontoSinTipoDePago(), estado, estaMorosa};
	    	model_factura.addRow(fila);
	    	}
	}

	@Override
	public JTable getTable_contratacion() {
		// TODO Auto-generated method stub
		return table_contrataciones;
	}
	public ArrayList<Contratacion> getListaContrataciones() {
		return listaContrataciones;
	}

	public ArrayList<IFactura> getListaFacturas() {
		return listaFacturas;
	}

	@Override
	public JButton getBtn_contratacion_nuevo() {
		// TODO Auto-generated method stub
		return this.btn_contratacion_nuevo;
	}
	
	public JButton getBtn_contratacion_eliminar() {
		return btn_contratacion_eliminar;
	}

	public JButton getBtn_abonado_eliminar() {
		return btn_abonado_eliminar;
	}

	public JButton getBtn_abonado_solicitarReparacion() {
		return btn_abonado_solicitarReparacion;
	}

	public JButton getBtn_servicio_nuevo() {
		return btn_servicio_nuevo;
	}

	public JButton getBtn_servicio_eliminar() {
		return btn_servicio_eliminar;
	}

	public JCalendar getCalendar() {
		return calendar;
	}

	@Override
	public JButton getBtn_pagar_factura() {
		return this.btn_factura_pagar_factura;
	}
	
	private IFactura getFacturaSeleccionada() {
		IFactura factura = null;
		if (getTable_factura().getSelectedRow()!= -1 && getTable_factura().getSelectedRow() < getListaFacturas().size() ){
			factura = (getListaFacturas().get(getTable_factura().getSelectedRow()));
		}
		return factura;
	
	}
	
	private Tecnico getTecnicoSeleccionado() {
		Tecnico tecnico = null;
		if (getTable_tecnico().getSelectedRow()!= -1 && getTable_tecnico().getSelectedRow() < getListaTecnicos().size() ){
			tecnico = (getListaTecnicos().get(getTable_tecnico().getSelectedRow()));
		}
		return tecnico;
	
	}
	
	private Contratacion getContratacionSeleccionada() {
		Contratacion contratacion = null;
		if (getTable_contratacion().getSelectedRow()!= -1 && getTable_contratacion().getSelectedRow() < getListaContrataciones().size() ){
			contratacion = (getListaContrataciones().get(getTable_contratacion().getSelectedRow()));
		}
		return contratacion;
	
	}
	
	private Servicio getServicioSeleccionado() {
		Servicio servicio = null;
		if (getTable_servicio().getSelectedRow()!= -1 && getTable_servicio().getSelectedRow() < getListaServicios().size() ){
			servicio = (getListaServicios().get(getTable_servicio().getSelectedRow()));
		}
		return servicio;
	
	}
	private Abonado getAbonadoSeleccionado() {
		Abonado abonado = null;
		if (getTable_abonado().getSelectedRow()!= -1 && getTable_abonado().getSelectedRow() < getListaAbonados().size() ){
			abonado = (getListaAbonados().get(getTable_abonado().getSelectedRow()));
		}
		return abonado;
	
	}

	public ArrayList<Servicio> getListaServicios() {
		return this.listaServicios;
	}

	
	public void refrescarVista(ArrayList<Abonado> listaAbonado, ArrayList<Tecnico> listaTecnico) {
		actualizarListaAbonados(listaAbonado);
		actualizarListaTecnicos(listaTecnico);
		if (getContratacionSeleccionada()!=null) {
			actualizaListaServicios(this.getContratacionSeleccionada().getListaServicio());
			this.comboBox_promo.setEnabled(true);
		}
		else {
			this.comboBox_promo.setEnabled(false);
		}
		if (getAbonadoSeleccionado()!=null) {
			actualizaListaFacturas(this.getAbonadoSeleccionado().getListaDeFacturas());
			actualizaListaContrataciones(this.getAbonadoSeleccionado().getListaDeContrataciones());
			if (getContratacionSeleccionada()!=null)
				this.actualizaListaServicios(getContratacionSeleccionada().getListaServicio());			
			else
				this.actualizaListaServicios(new ArrayList<Servicio>());
		}

	}
	
	public void enableButtons() {
		if (this.getAbonadoSeleccionado()!=null){
			if ((getAbonadoSeleccionado() instanceof Fisica) &&(boolean)getAbonadoSeleccionado().getEstado().toString().equals("Moroso")) {
				btn_contratacion_nuevo.setEnabled(false);
				btn_contratacion_eliminar.setEnabled(false);
			}
			else {
				btn_contratacion_nuevo.setEnabled(true);
			}
			btn_abonado_eliminar.setEnabled(true);
			if (this.getAbonadoSeleccionado().getListaDeContrataciones().isEmpty())
				btn_abonado_solicitarReparacion.setEnabled(false);
			else
				btn_abonado_solicitarReparacion.setEnabled(true);
		}
		else {
			btn_contratacion_nuevo.setEnabled(false);
			btn_abonado_eliminar.setEnabled(false);
			btn_abonado_solicitarReparacion.setEnabled(false);
			comboBox_promo.setEnabled(false);
		}
		
		if (this.getContratacionSeleccionada()!=null) {
			comboBox_promo.setEnabled(true);
			btn_servicio_nuevo.setEnabled(true);
			if ((getAbonadoSeleccionado() instanceof Juridica) || ((getAbonadoSeleccionado() instanceof Fisica) &&!getAbonadoSeleccionado().getEstado().toString().equals("Moroso"))) {
				btn_contratacion_eliminar.setEnabled(true);
				btn_servicio_nuevo.setEnabled(true);
			}
			else {
				btn_contratacion_eliminar.setEnabled(false);
				btn_servicio_nuevo.setEnabled(false);
				comboBox_promo.setEnabled(false);
			}
		}
		else {
			comboBox_promo.setEnabled(false);
			btn_servicio_nuevo.setEnabled(false);
			btn_contratacion_eliminar.setEnabled(false);
		}
		
		if (this.getServicioSeleccionado()!=null && (!(getAbonadoSeleccionado() instanceof Fisica) || !getAbonadoSeleccionado().getEstado().toString().equals("Moroso")))
			this.btn_servicio_eliminar.setEnabled(true);
		else
			this.btn_servicio_eliminar.setEnabled(false);
			
		if(!this.getFacturasSeleccionadas().isEmpty()) {
			this.btn_factura_pagar_factura.setEnabled(true);
			for (IFactura factura : getFacturasSeleccionadas()) {
					if (factura.isPago()) {
					this.btn_factura_pagar_factura.setEnabled(false);
					break;
				}
			}
		}
		else {
			this.btn_factura_pagar_factura.setEnabled(false);
		}
		if (this.getTecnicoSeleccionado()!=null) 
			this.btn_tecnico_eliminar.setEnabled(true);
		else
			this.btn_tecnico_eliminar.setEnabled(false);	
}
	
	private ArrayList<IFactura> getFacturasSeleccionadas() {
	    ArrayList<IFactura> facturasSeleccionadas = new ArrayList<>();
	    
	    int[] filasSeleccionadas = getTable_factura().getSelectedRows();
	    for (int fila : filasSeleccionadas) {
	        if (fila >= 0 && fila < getListaFacturas().size()) {
	            IFactura factura = getListaFacturas().get(fila);
	            facturasSeleccionadas.add(factura);
	        }
	    }	    
	    return facturasSeleccionadas;
	}
	
	public void deselectAll() {
		this.table_contrataciones.clearSelection();
		this.table_factura.clearSelection();
		this.table_servicio.clearSelection();
		this.table_abonado.clearSelection();
	}
	public void deselectAllexceptAbonado() {
		this.table_contrataciones.clearSelection();
		this.table_factura.clearSelection();
		this.table_servicio.clearSelection();
	}
	
	public void actualizarFecha(LocalDate fecha) {
		this.lbl_fecha.setText(fecha.toString());
	}
}
