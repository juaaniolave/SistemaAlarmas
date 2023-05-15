package abonado;

public class Fisica extends Abonado {

	public Fisica(String nombre, int dni) {
		super(nombre, dni);
	}
	@Override
	public String toString() {
		return "tipoA: " + tipoAbonado() + super.toString();
	}
	public String tipoAbonado() {
		return "Fisica";
	}
	@Override
	public double valorTotal() {
		double suma = 0;
		for (int i = 0; i < this.Lista.size(); i++) {
			suma += this.Lista.get(i).getValorTotal();
		}
		return suma;
	}
	public Object clone() throws CloneNotSupportedException {
		Fisica clon = null;
		clon = (Fisica) super.clone();
		return clon;
	}
}
