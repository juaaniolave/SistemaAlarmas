package vista;

import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class VentanaCrearContratacion extends JDialog {
	private ActionListener actionListener;
	private JTextField textField_Domicilio;
	private JLabel lbl_Domicilio;
	private JButton btn_aceptar;
	private JComboBox comboBox_domicilio_tipo;
	
	
	public VentanaCrearContratacion(ActionListener actionListener) {
		this.actionListener=actionListener;
		
		setSize(425, 305);
		setLocationRelativeTo(null);
		getContentPane().setLayout(null);
		
		lbl_Domicilio = new JLabel("Domicilio:");
		lbl_Domicilio.setBounds(10, 11, 46, 14);
		getContentPane().add(lbl_Domicilio);
		
		textField_Domicilio = new JTextField();
		textField_Domicilio.setBounds(10, 36, 359, 20);
		getContentPane().add(textField_Domicilio);
		textField_Domicilio.setColumns(10);
		
		btn_aceptar = new JButton("Aceptar");
		btn_aceptar.setActionCommand("Agregar contratacion");
		btn_aceptar.setBounds(159, 213, 89, 23);
		btn_aceptar.addActionListener(actionListener);
		getContentPane().add(btn_aceptar);
		
		comboBox_domicilio_tipo = new JComboBox();
		comboBox_domicilio_tipo.setModel(new DefaultComboBoxModel(new String[] {"Vivienda", "Comercio"}));
		comboBox_domicilio_tipo.setBounds(10, 123, 117, 22);
		getContentPane().add(comboBox_domicilio_tipo);
		
		JLabel lbl_domicilio_label = new JLabel("Tipo de domicilio:");
		lbl_domicilio_label.setBounds(10, 97, 102, 14);
		getContentPane().add(lbl_domicilio_label);
	}

	public void setActionListener(ActionListener actionListener) {
		this.actionListener=actionListener;
		btn_aceptar.addActionListener(actionListener);
		
	}
	
	public String getNombreDomicilio() {
		return this.textField_Domicilio.getText();
	}
	

	public ActionListener getActionListener() {
		return actionListener;
	}

	public JButton getBtn_aceptar() {
		return btn_aceptar;
	}

	public JComboBox getComboBox_domicilio_tipo() {
		return comboBox_domicilio_tipo;
	}
	
	
}
