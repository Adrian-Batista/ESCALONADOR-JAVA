package escalonamento;

import java.util.ArrayList;
import java.util.List;

public class Execucao {
	private int horaAtual;
	private int ultChegada;

	private List<ListaProcessos> lista;
	private List<Processos> executado;
	private List<ExeGantt> listaGantt;

	public Execucao() {
		this.horaAtual = 0;
		this.lista = new ArrayList<ListaProcessos>();
		this.executado = new ArrayList<Processos>();
		this.listaGantt = new ArrayList<ExeGantt>();
	}

	public void execute() {
		List<Processos> fila = new ArrayList<Processos>();
		Boolean executado;

		while (true) {
			executado = false;
			// Adicionando os novos processos à fila
			fila = adicionaNaHoraAtual(fila);

			if (fila.size() > 0) {
				String alg = fila.get(0).getAlgoritmo();
				executado = true;

				if (alg.contentEquals("FCFS")) {
					fila = iniciarFCFS(fila);
				} else if (alg.contentEquals("SJF")) {
					int index = getindiceLista(fila.get(0));
					fila = iniciarSJF(lista.get(index).getProcesses(), fila);
				} else if (alg.contentEquals("RR")) {
					int index = getindiceLista(fila.get(0));
					fila = inciarRR(fila, lista.get(index).getQuantum());
				} else {
					System.out.println("O processo atual na fila tem um algoritmo desconhecido");
					executado = false;
				}
			}

			if (!executado)
				this.horaAtual++;

			if (horaAtual > ultChegada && fila.isEmpty())
				break;
		}
	}

	public List<Processos> adicionaNaHoraAtual(List<Processos> fila) {
		for (int i = 0; i < lista.size(); i++) {
			for (Processos p : lista.get(i).getProcesses()) {
				if (p.getTempoChegada() <= horaAtual && !fila.contains(p) && !this.executado.contains(p)) {
					fila = addOrdenado(p, fila);
				}
			}
		}

		return fila;
	}

	public List<Processos> adicionaNaHoraAtual(List<Processos> fila, Processos processos) {
		for (int i = 0; i < lista.size(); i++) {
			for (Processos p : lista.get(i).getProcesses()) {
				if (p.getTempoChegada() <= horaAtual && !fila.contains(p) && !this.executado.contains(p)
						&& p != processos) {
					fila = addOrdenado(p, fila);
				}
			}
		}

		return fila;
	}

	public List<Processos> addOrdenado(Processos p, List<Processos> fila) {
		if (fila.size() > 1) {
			// Verificando se precisa adicionar na última posição
			if (fila.get(fila.size() - 1).getnAlgoritmos() <= p.getnAlgoritmos()) {
				fila.add(p);
				return fila;
			}

			for (int i = fila.size() - 1; i >= 0; i--) {
				if (i - 1 >= 0) {
					if (p.getnAlgoritmos() >= fila.get(i - 1).getnAlgoritmos()) {
						fila.add(i, p);
						break;
					}
				} else {
					fila.add(i, p);
					break;
				}

			}
		} else if (fila.size() > 0) {
			if (fila.get(0).getnAlgoritmos() > p.getnAlgoritmos())
				fila.add(0, p);
			else
				fila.add(p);
		} else {
			fila.add(p);
		}

		return fila;
	}


	public int getindiceLista(Processos processos) {
		int index = 0;

		for (int i = 0; i < lista.size(); i++) {
			if (lista.get(i).getProcesses().contains(processos)) {
				index = i;
				break;
			}
		}

		return index;
	}

	public Processos getIdInferior(List<Processos> fila) {
		Processos p = fila.get(0);
		int index = 0;

		for (int i = 0; i < fila.size(); i++) {
			if (p.getnAlgoritmos() == fila.get(i).getnAlgoritmos() && p.getId() > fila.get(i).getId()
					&& p.getTempoChegada() == fila.get(i).getTempoChegada()) {
				index = i;
			}
		}

		return fila.get(index);
	}

	public List<Processos> iniciarFCFS(List<Processos> fila) {
		Processos processos = getIdInferior(fila);
		ExeGantt ganttEx = new ExeGantt(processos.getId(), this.horaAtual);

		processos.setTempoConclusao(this.horaAtual + processos.getBashTime());
		processos.atualizarTempoRest(processos.getBashTime());

		this.horaAtual += processos.getBashTime();
		fila.remove(processos);
		this.executado.add(processos);

		ganttEx.setFim(this.horaAtual);
		listaGantt.add(ganttEx);

		return fila;
	}

	public List<Processos> iniciarSJF(List<Processos> pLista, List<Processos> fila) {
		int minor = 999999999, index = 0;

		for (int i = 0; i < pLista.size(); i++) {
			if (pLista.get(i).getBashTime() < minor && pLista.get(i).getTempoChegada() <= this.horaAtual
					&& !this.executado.contains(pLista.get(i))) {
				minor = pLista.get(i).getBashTime();
				index = i;
			}
		}

		Processos processos = pLista.get(index);
		ExeGantt ganttEx = new ExeGantt(processos.getId(), this.horaAtual);

		processos.setTempoConclusao(this.horaAtual + processos.getBashTime());
		processos.atualizarTempoRest(processos.getBashTime());
		this.horaAtual += processos.getBashTime();

		fila.remove(processos);
		this.executado.add(processos);

		ganttEx.setFim(this.horaAtual);
		listaGantt.add(ganttEx);

		return fila;
	}

	public List<Processos> inciarRR(List<Processos> fila, int quantum) {
		Processos processos = fila.get(0);
		ExeGantt ganttEx = new ExeGantt(processos.getId(), this.horaAtual);

		if (quantum <= processos.getTempoRestante()) {
			processos.atualizarTempoRest(quantum);
			this.horaAtual += quantum;
		} else {
			int tempoRestante = processos.getTempoRestante();
			processos.atualizarTempoRest(tempoRestante);
			this.horaAtual += tempoRestante;
		}

		fila.remove(processos);

		if (processos.getTempoRestante() > 0) {
			fila = adicionaNaHoraAtual(fila, processos);
			fila = addOrdenado(processos, fila);
		} else {
			processos.setTempoConclusao(this.horaAtual);
			executado.add(processos);
		}

		ganttEx.setFim(this.horaAtual);
		listaGantt.add(ganttEx);

		return fila;
	}

	public void finalizar() {
		for (ListaProcessos pList : lista) {
			for (Processos p : pList.getProcesses()) {
				p.finish();
			}
		}
	}

	public int getHoraAtual() {
		return horaAtual;
	}

	public void setHoraAtual(int horaAtual) {
		this.horaAtual = horaAtual;
	}

	public int getUltChegada() {
		return ultChegada;
	}

	public void setUltChegada(int ultChegada) {
		this.ultChegada = ultChegada;
	}

	public List<ListaProcessos> getLista() {
		return lista;
	}

	public void setLista(List<ListaProcessos> lista) {
		this.lista = lista;
	}

	public List<Processos> getExecutado() {
		return executado;
	}

	public void setExecutado(List<Processos> executado) {
		this.executado = executado;
	}

	public List<ExeGantt> getListaGantt() {
		return listaGantt;
	}

	public void setListaGantt(List<ExeGantt> listaGantt) {
		this.listaGantt = listaGantt;
	}



}
