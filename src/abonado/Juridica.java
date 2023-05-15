package abonado;

public class Juridica extends Abonado {

	public Juridica(String nombre, int dni) {
		super(nombre, dni);
	}
	@Override
	public String toString() {
		return "tipoA: " + tipoAbonado() + super.toString();
	}
	public String tipoAbonado() {
		return "Juridica";
	}
	@Override
	public double valorTotal() {
		double suma = 0;
		int i = 0;
		for (i = 0; i < this.Lista.size(); i++) {
			if (i >= 2) {
				suma += this.Lista.get(i).getValorTotal() * 0.5;
			} else
				suma += this.Lista.get(i).getValorTotal();
		}
		return suma;
	}
	public Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException("la clase abonado juridico no puede ser clonado");
	}
}
