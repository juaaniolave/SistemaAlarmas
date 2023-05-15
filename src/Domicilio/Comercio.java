package Domicilio;

public class Comercio extends Domicilio {
	public Comercio(String nombre) {
		super(nombre);
	}

	@Override
	public String getTipoDom() {
		return "Comercio";
	}
	@Override
	public double valorBase() {
		return 10000;
	}

	@Override
	public double promoDorada() {
		return valorBase() - 2500;
	}

	@Override
	public double promoPlatino() {
		// TODO Auto-generated method stub
		return valorBase() * 0.65;
	}
	
	public Object clone() throws CloneNotSupportedException {
		Comercio clon = (Comercio) super.clone();
		return clon;
	}


}