package main;


import controlador.ControladorEmpresa;
import empresa.Empresa;
import empresa.MesaDeSolicitudDeTecnicos;
import excepciones.FactoryInvalidoException;
import vista.IVista;
import vista.VistaEmpresa;

public class MainConVista {

	public static void main(String[] args) throws FactoryInvalidoException {
		
		Empresa empresa = Empresa.getInstance();
		IVista vista = new VistaEmpresa();
		MesaDeSolicitudDeTecnicos mesa = new MesaDeSolicitudDeTecnicos();
		ControladorEmpresa controlador= new ControladorEmpresa(empresa, vista,mesa);	
		Empresa.getInstance().setMesaDeSolicitudDeTecnicos(mesa);
	}

}
