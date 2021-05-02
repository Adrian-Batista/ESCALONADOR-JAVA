package escalonamento;

public class Processos {
	private String algoritmo;
	private int id;
	private int nAlgoritmos;
	private int tempoChegada;
	private int bashTime;
	private int tempoConclusao;
	private int tempoResposta;
	private int tempoEspera;
	private int tempoExecucao;
	private int tempoRestante;
	
	public Processos() {
		
	}
	
	public Processos(int id, int tempoChegada, int bashTime, int nAlgoritmos, String algoritmo) {
		this.id = id;
		this.tempoChegada = tempoChegada;
		this.bashTime = bashTime;
		this.tempoRestante = bashTime;
		this.algoritmo = algoritmo;
		this.nAlgoritmos = nAlgoritmos;
		this.tempoExecucao = 0;
	}
	
	public void finish() {
		this.tempoResposta = this.tempoConclusao - this.tempoChegada;
		this.tempoEspera = this.tempoResposta - this.bashTime;
	}
	
	public void atualizarTempoRest(int tempoExecucao) {
		this.tempoRestante -= tempoExecucao;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAlgoritmo() {
		return algoritmo;
	}

	public void setAlgoritmo(String algoritmo) {
		this.algoritmo = algoritmo;
	}

	public int getnAlgoritmos() {
		return nAlgoritmos;
	}

	public void setnAlgoritmos(int nAlgoritmos) {
		this.nAlgoritmos = nAlgoritmos;
	}

	public int getTempoChegada() {
		return tempoChegada;
	}

	public void setTempoChegada(int tempoChegada) {
		this.tempoChegada = tempoChegada;
	}

	public int getBashTime() {
		return bashTime;
	}

	public void setBashTime(int bashTime) {
		this.bashTime = bashTime;
	}

	public int getTempoConclusao() {
		return tempoConclusao;
	}

	public void setTempoConclusao(int tempoConclusao) {
		this.tempoConclusao = tempoConclusao;
	}

	public int getTempoResposta() {
		return tempoResposta;
	}

	public void setTempoResposta(int tempoResposta) {
		this.tempoResposta = tempoResposta;
	}

	public int getTempoEspera() {
		return tempoEspera;
	}

	public void setTempoEspera(int tempoEspera) {
		this.tempoEspera = tempoEspera;
	}

	public int getTempoExecucao() {
		return tempoExecucao;
	}

	public void setTempoExecucao(int tempoExecucao) {
		this.tempoExecucao = tempoExecucao;
	}

	public int getTempoRestante() {
		return tempoRestante;
	}

	public void setTempoRestante(int tempoRestante) {
		this.tempoRestante = tempoRestante;
	}
	
}
