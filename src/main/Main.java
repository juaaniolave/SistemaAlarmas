package main;

import Domicilio.Comercio;
import Domicilio.Domicilio;
import Domicilio.Vivienda;
import abonado.Abonado;
import abonado.Fisica;
import abonado.Juridica;
import empresa.Contrataciones;
import empresa.Empresa;
import empresa.Factura;
import excepciones.ContratacionInvalidaException;
import excepciones.DomicilioExistenteException;
import excepciones.DomicilioVinculadoException;
import excepciones.FactoryInvalidoException;
import metodosdepago.DecoratorPago;
import metodosdepago.PagoEfectivo;
import promo.Promo;
import promo.PromoDorada;
import promo.PromoPlatino;
import servicio.Servicio;
import servicio.ServicioAcompaniamiento;
import servicio.ServicioBoton;
import servicio.ServicioCamara;

public class Main {

	public static void main(String[] args) throws DomicilioVinculadoException {
		Empresa e=Empresa.getInstance();
		
		Servicio s1 = new ServicioCamara();
		Servicio s2 = new ServicioBoton();
		Servicio s3 = new ServicioAcompaniamiento();
		
		Promo p1 = new PromoDorada();
		Promo p2 = new PromoPlatino();
		
		Domicilio d1 = new Vivienda("dom1");
		Domicilio d2 = new Comercio("dom2");
		Domicilio d3 = new Vivienda("dom3");
		Domicilio d4 = new Vivienda("dom4");
		Domicilio d5 = new Comercio("dom5");
		Domicilio d6 = new Vivienda("dom6");
		Domicilio d7 = new Vivienda("dom7");
		Domicilio d8 = new Comercio("dom8");
		Domicilio d9 = new Vivienda("dom9");
		Domicilio d10 = new Vivienda("dom10");
		Domicilio d11 = new Comercio("dom11");
		Domicilio d12 = new Vivienda("dom12");

		System.out.println("FACTURA 1------------------");
		Abonado a1 = new Fisica("abonado1", 123);
		try {
			e.agregaAbonado(a1,"Efectivo");
		} catch (FactoryInvalidoException e5) {}
		try {
			e.agregaDom(d1, a1);
			e.agregaDom(d2, a1);
			e.agregaDom(d3, a1);
		} catch (DomicilioExistenteException e1) {}
		
		try {
			e.CrearContratacion(d1, a1);
			e.aniadirServicio(d1, s1);
			e.aniadirServicio(d1, s1);
			e.aniadirServicio(d1, s2);
			e.aniadirPromo(d1, p1);
		} catch (ContratacionInvalidaException c1) {}
		try {
			e.CrearContratacion(d2, a1);
			e.aniadirServicio(d2, s2);
			e.aniadirServicio(d2, s2);
			e.aniadirServicio(d2, s3);
			e.aniadirPromo(d2, p2);
		} catch (ContratacionInvalidaException e1) {}
		
		try {
			e.CrearContratacion(d3, a1);
			e.aniadirServicio(d3, s3);
		} catch (ContratacionInvalidaException e1) {}
			
		Factura fac1=e.getFactura(a1);
		System.out.println(fac1.ImprimeFactura());
		
		System.out.println("FACTURA 2------------------");
		Abonado a2 = new Fisica("abonado2", 456);
		try {
			e.agregaAbonado(a2, "Tarjeta");
		} catch (FactoryInvalidoException e1) {}
		try {
			e.agregaDom(d4, a2);
			e.agregaDom(d5, a2);
			e.agregaDom(d6, a2);
		} catch (DomicilioExistenteException e2) {}
			try {
				e.CrearContratacion(d4, a2);
				e.aniadirServicio(d4, s1);
				e.aniadirServicio(d4, s1);
				e.aniadirServicio(d4, s2);
				e.aniadirPromo(d4, p1);
			} catch (ContratacionInvalidaException e1) {}
		
			try {
				e.CrearContratacion(d5, a2);
				e.aniadirServicio(d5, s2);
				e.aniadirServicio(d5, s2);
				e.aniadirServicio(d5, s3);
				e.aniadirPromo(d5, p2);
			} catch (ContratacionInvalidaException e1) {}
			try {
				e.CrearContratacion(d6, a2);
				e.aniadirServicio(d6, s3);
			} catch (ContratacionInvalidaException e1) {}
			
			Factura fac2=e.getFactura(a2);
			System.out.println(fac2.ImprimeFactura());
		
		System.out.println("FACTURA 3------------------");
		Abonado a3 = new Juridica("abonado1", 789);
		try {
			e.agregaAbonado(a3,"Cheque");
		} catch (FactoryInvalidoException e1) {}
		try {
			e.agregaDom(d7, a3);
			e.agregaDom(d8, a3);
			e.agregaDom(d9, a3);
		} catch (DomicilioExistenteException e3) {}
		try {
			e.CrearContratacion(d7,a3);
			e.aniadirServicio(d7,s1);
			e.aniadirServicio(d7,s1);
			e.aniadirServicio(d7,s2);
			e.aniadirPromo(d7, p1);
		} catch (ContratacionInvalidaException e1) {}
		try {
			e.CrearContratacion(d8, a3);
			e.aniadirServicio(d8, s2);
			e.aniadirServicio(d8, s2);
			e.aniadirServicio(d8, s3);
			e.aniadirPromo(d8, p2);
		} catch (ContratacionInvalidaException e1) {}
		try {
			e.CrearContratacion(d9, a3);
			e.aniadirServicio(d9, s3);
		} catch (ContratacionInvalidaException e1) {}
		
		Factura fac3=e.getFactura(a3);
		System.out.println(fac3.ImprimeFactura());
		
		System.out.println("FACTURA 4------------------");
		Abonado a4 = new Juridica("abonado2", 101112);
		try {
			e.agregaAbonado(a4,"Efectivo");
		} catch (FactoryInvalidoException e1) {}
		try {
			e.agregaDom(d10, a4);
			e.agregaDom(d11, a4);
			e.agregaDom(d12, a4);
		} catch (DomicilioExistenteException e4) {}
		try {
			e.CrearContratacion(d10,a4);
			e.aniadirServicio(d10, s1);
			e.aniadirServicio(d10, s1);
			e.aniadirServicio(d10, s2);
			e.aniadirPromo(d10, p1);
		} catch (ContratacionInvalidaException e1) {}
		try {
			e.CrearContratacion(d11, a4);
			e.aniadirServicio(d11, s2);
			e.aniadirServicio(d11, s2);
			e.aniadirServicio(d11, s3);
			e.aniadirPromo(d11, p2);
		} catch (ContratacionInvalidaException e1) {}
		try {
			e.CrearContratacion(d12, a4);
			e.aniadirServicio(d12, s3);
		} catch (ContratacionInvalidaException e1) {}
		Factura fac4=e.getFactura(a4);
		System.out.println(fac4.ImprimeFactura());
		
		System.out.println("CLONES------------------");
		try {
			Factura fac1clon=(Factura)e.ClonarFactura(fac1);
		} catch (CloneNotSupportedException e1) {
			System.out.println(e1.getMessage());
		}
		try {
			Factura fac2clon=(Factura)e.ClonarFactura(fac2);
		} catch (CloneNotSupportedException e1) {
			System.out.println(e1.getMessage());
		}
		try {
			Factura fac3clon=(Factura)e.ClonarFactura(fac3);
		} catch (CloneNotSupportedException e1) {
			System.out.println(e1.getMessage());
		}
		try {
			Factura fac4clon=(Factura)e.ClonarFactura(fac4);
		} catch (CloneNotSupportedException e1) {
			System.out.println(e1.getMessage());
		}
		
	}

}
