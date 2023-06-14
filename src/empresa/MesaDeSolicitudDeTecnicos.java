package empresa;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Queue;

import abonado.Abonado;

public class MesaDeSolicitudDeTecnicos extends Observable implements Serializable{

	private Queue<Abonado> abonadoEsperando = new LinkedList<Abonado>();
	
	
	public synchronized void  solicitarReparacion (Abonado abonado){
		
	
		this.setChanged();
		notifyObservers("El abonado " + abonado.getNombre() +" ha solicitado reparación, se agrega a lista de espera");		
		this.abonadoEsperando.add(abonado);	
		notifyAll();
	}

	public synchronized void getAbonado (Tecnico tecnico) {
		
		this.setChanged();
		notifyObservers("El tecnico " +tecnico.getNombre()+ " está esperando para reparar");
		
		while(this.abonadoEsperando.isEmpty()) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
		tecnico.setAbonado(this.abonadoEsperando.poll());
		notifyAll();
		this.setChanged();
		notifyObservers("El tecnico " + tecnico.getNombre() +" ha comenzado a trabajar en la reparación solicitada por " + tecnico.getAbonado().getNombre());		
		
	}

	public void informarFinDeTrabajo (Tecnico tecnico) {
		tecnico.getAbonado().setNecesitaReparacion(false);
		this.setChanged();
		notifyObservers("El tecnico " + tecnico.getNombre() +" ha terminado a trabajar en la reparación solicitada por " + tecnico.getAbonado().getNombre());
		
	}
}
