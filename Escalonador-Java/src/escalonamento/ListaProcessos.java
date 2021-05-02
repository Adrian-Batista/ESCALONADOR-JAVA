package escalonamento;

import java.util.ArrayList;
import java.util.List;

public class ListaProcessos {
	private String algoritmo;
	private List<Processos> processo;
	private int quantum;
	
	public ListaProcessos() {

	}

	public ListaProcessos(String algoritmo, int quantum) {
		super();
		this.algoritmo = algoritmo;
		this.quantum = quantum;
		this.processo = new ArrayList<Processos>();
	}

	public ListaProcessos(String algoritmo, int quantum, ArrayList<Processos> processo) {
		super();
		this.algoritmo = algoritmo;
		this.quantum = quantum;
		this.processo = processo;
	}

	private static void quickSort(List<Processos> lista, int inicio, int fim, int tipo) {
		if (inicio < fim) {
			int pivot = 0;
			switch (tipo) {
				case 0:
					pivot = separaId(lista, inicio, fim);
					break;
				case 1:
					pivot = separarChegada(lista, inicio, fim);
					break;
				default:
					System.out.println("Invalid type");
					break;
			}
				
			quickSort(lista, inicio, pivot - 1, tipo);
			quickSort(lista, pivot + 1, fim, tipo);
		}
	}

	private static int separaId(List<Processos> list, int start, int end) {
		Processos pivot = list.get(start);
		int i = start + 1, f = end;
		
		while (i <= f) {
			if (list.get(i).getId() <= pivot.getId())
				i++;
			else if (pivot.getId() < list.get(f).getId())
				f--;
			else {
				Processos change = list.get(i);
				list.set(i, list.get(f));
				list.set(f, change);
				i++;
				f--;
			}
		}
		
		list.set(start, list.get(f));
		list.set(f, pivot);
		return f;
	}
	
	private static int separarChegada(List<Processos> lista, int inicio, int fim) {
		Processos pivot = lista.get(inicio);
		int i = inicio + 1, f = fim;
		
		while (i <= f) {
			if (lista.get(i).getTempoChegada() <= pivot.getTempoChegada())
				i++;
			else if (pivot.getTempoChegada() < lista.get(f).getTempoChegada())
				f--;
			else {
				Processos change = lista.get(i);
				lista.set(i, lista.get(f));
				lista.set(f, change);
				i++;
				f--;
			}
		}
		
		lista.set(inicio, lista.get(f));
		lista.set(f, pivot);
		return f;
	}
	
	public String getAlgorithm() {
		return algoritmo;
	}

	public void setAlgorithm(String algorithm) {
		this.algoritmo = algorithm;
	}

	public int getQuantum() {
		return quantum;
	}

	public void setQuantum(int quantum) {
		this.quantum = quantum;
	}

	public List<Processos> getProcesses() {
		return processo;
	}

	public void setProcesses(List<Processos> processes) {
		this.processo = processes;
	}
	
	public void orderById() {
		quickSort(processo, 0, processo.size() - 1, 0);
	}

	public void orderByArrival() {
		quickSort(processo, 0, processo.size() - 1, 1);
	}


}
