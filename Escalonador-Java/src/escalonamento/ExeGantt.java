package escalonamento;

public class ExeGantt {
	private int id;
	private int inicio;
	private int fim;
	
	public ExeGantt(int id, int inicio) {
		this.id = id;
		this.inicio = inicio;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getInicio() {
		return inicio;
	}

	public void setInicio(int inicio) {
		this.inicio = inicio;
	}

	public int getFim() {
		return fim;
	}

	public void setFim(int fim) {
		this.fim = fim;
	}
	
	

}
