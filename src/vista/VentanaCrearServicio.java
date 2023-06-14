package vista;

import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class VentanaCrearServicio extends JDialog {
	private ActionListener actionListener;
	private JTextField textField_camaras;
	private JLabel lbl_camaras;
	private JButton btn_aceptar;
	private JTextField textField_botones;
	private JTextField textField_acompaniamientos;
	private JLabel lbl_acompaniamientos;
	private JLabel lbl_Botones;
	
	
	public VentanaCrearServicio(ActionListener actionListener) {
		this.actionListener=actionListener;
		
		setSize(425, 305);
		setLocationRelativeTo(null);
		getContentPane().setLayout(null);
		
		lbl_camaras = new JLabel("Camaras:");
		lbl_camaras.setBounds(10, 11, 117, 14);
		getContentPane().add(lbl_camaras);
		
		textField_camaras = new JTextField();
		textField_camaras.setBounds(10, 36, 117, 20);
		getContentPane().add(textField_camaras);
		textField_camaras.setColumns(10);
		
		btn_aceptar = new JButton("Aceptar");
		btn_aceptar.setActionCommand("Agregar servicio");
		btn_aceptar.setBounds(159, 213, 89, 23);
		btn_aceptar.addActionListener(actionListener);
		getContentPane().add(btn_aceptar);
		
		lbl_Botones = new JLabel("Botones antipanico:");
		lbl_Botones.setBounds(10, 67, 117, 14);
		getContentPane().add(lbl_Botones);
		
		textField_botones = new JTextField();
		textField_botones.setColumns(10);
		textField_botones.setBounds(10, 92, 117, 20);
		getContentPane().add(textField_botones);
		
		lbl_acompaniamientos = new JLabel("Servicio de acompañamientos:");
		lbl_acompaniamientos.setBounds(10, 123, 152, 14);
		getContentPane().add(lbl_acompaniamientos);
		
		textField_acompaniamientos = new JTextField();
		textField_acompaniamientos.setColumns(10);
		textField_acompaniamientos.setBounds(10, 148, 117, 20);
		getContentPane().add(textField_acompaniamientos);
	}

	public void setActionListener(ActionListener actionListener) {
		this.actionListener=actionListener;
		btn_aceptar.addActionListener(actionListener);
		
	}
	
	public String getNombreDomicilio() {
		return this.textField_camaras.getText();
	}
	

	public ActionListener getActionListener() {
		return actionListener;
	}

	public JButton getBtn_aceptar() {
		return btn_aceptar;
	}

	public JTextField getTextField_camaras() {
		return textField_camaras;
	}

	public JTextField getTextField_botones() {
		return textField_botones;
	}

	public JTextField getTextField_acompaniamientos() {
		return textField_acompaniamientos;
	}


}
