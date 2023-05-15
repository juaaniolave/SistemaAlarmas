package promo;

import Domicilio.Domicilio;

public class PromoDorada implements Promo, Cloneable {

	public String toString() {
		return "Dorada";
	}
	@Override
	public double aplicarPromo(Domicilio domicilio) {
		assert domicilio != null : "Domicilio null";
		return domicilio.promoDorada();
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		Promo clon = null;
		clon = (Promo) super.clone();
		return clon;
	}
}