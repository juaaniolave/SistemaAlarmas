package promo;

import Domicilio.Domicilio;

public class PromoPlatino implements Promo, Cloneable {

	public String toString() {
		return "Platino";
	}
	@Override
	public double aplicarPromo(Domicilio domicilio) {
		assert domicilio != null : "Domicilio null";
		return domicilio.promoPlatino();
	}
	@Override
	public Object clone() throws CloneNotSupportedException {
		Promo clon = null;
		clon = (Promo) super.clone();
		return clon;
	}
}