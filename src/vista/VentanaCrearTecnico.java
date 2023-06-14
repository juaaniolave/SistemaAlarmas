package vista;

import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class VentanaCrearTecnico extends JDialog {
	
	private ActionListener actionListener;
	private JTextField textField_nombre;
	private JTextField textField_DNI;
	private JLabel lbl_Nombre;
	private JLabel lbl_DNI;
	private JButton btn_aceptar;
	
	public VentanaCrearTecnico(ActionListener actionListener) {
		
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
		btn_aceptar.setActionCommand("Agregar tecnico");
		btn_aceptar.setBounds(154, 162, 89, 23);
		btn_aceptar.addActionListener(actionListener);
		getContentPane().add(btn_aceptar);
	}

	public void setActionListener(ActionListener actionListener) {
		this.actionListener=actionListener;
		btn_aceptar.addActionListener(actionListener);
		
	}
	
	public String getNombreTecnico() {
		return this.textField_nombre.getText();
	}
	
	public String getDNI() {
		return this.textField_DNI.getText();
	}

	
}
