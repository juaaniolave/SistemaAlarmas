package vista;

import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class VentanaCrearAbonado extends JDialog {
	private ActionListener actionListener;
	private JTextField textField_nombre;
	private JTextField textField_DNI;
	private JLabel lbl_Nombre;
	private JLabel lbl_DNI;
	private JButton btn_aceptar;
	private JComboBox comboBox_tipo_de_abonado;
	private JLabel lbl_tipo_de_abonado;
	
	
	public VentanaCrearAbonado(ActionListener actionListener) {
		this.actionListener=actionListener;
		
		setSize(425, 305);
		setLocationRelativeTo(null);
		getContentPane().setLayout(null);
		
		lbl_Nombre = new JLabel("Nombre");
		lbl_Nombre.setBounds(10, 11, 46, 14);
		getContentPane().add(lbl_Nombre);
		
		textField_nombre = new JTextField();
		textField_nombre.setBounds(10, 36, 359, 20);
		getContentPane().add(textField_nombre);
		textField_nombre.setColumns(10);
		
		lbl_DNI = new JLabel("DNI");
		lbl_DNI.setBounds(10, 67, 46, 14);
		getContentPane().add(lbl_DNI);
		
		textField_DNI = new JTextField();
		textField_DNI.setBounds(10, 92, 359, 20);
		getContentPane().add(textField_DNI);
		textField_DNI.setColumns(10);
		
		btn_aceptar = new JButton("Aceptar");
		btn_aceptar.setActionCommand("Agregar abonado");
		btn_aceptar.setBounds(159, 213, 89, 23);
		btn_aceptar.addActionListener(actionListener);
		getContentPane().add(btn_aceptar);
		
		comboBox_tipo_de_abonado = new JComboBox();
		comboBox_tipo_de_abonado.setModel(new DefaultComboBoxModel(new String[] {"Físico", "Jurídico"}));
		comboBox_tipo_de_abonado.setBounds(135, 151, 138, 22);
		getContentPane().add(comboBox_tipo_de_abonado);
		
		lbl_tipo_de_abonado = new JLabel("Tipo de abonado:");
		lbl_tipo_de_abonado.setBounds(167, 126, 106, 14);
		getContentPane().add(lbl_tipo_de_abonado);
	}

	public void setActionListener(ActionListener actionListener) {
		this.actionListener=actionListener;
		btn_aceptar.addActionListener(actionListener);
		
	}
	
	public String getNombreAbonado() {
		return this.textField_nombre.getText();
	}
	
	public String getDNI() {
		return this.textField_DNI.getText();
	}

	public ActionListener getActionListener() {
		return actionListener;
	}

	public JTextField getTextField_nombre() {
		return textField_nombre;
	}

	public JTextField getTextField_DNI() {
		return textField_DNI;
	}

	public JButton getBtn_aceptar() {
		return btn_aceptar;
	}

	public JComboBox getComboBox_tipo_de_abonado() {
		return comboBox_tipo_de_abonado;
	}
	
	
}
