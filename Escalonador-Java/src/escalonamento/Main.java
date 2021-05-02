package escalonamento;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {

		int pId = 0, prioritario = 0, bashTime = 0, tempoChegada = 0, prox = -1;
		String linha[], algoritmo;
		String arquivo = "./data.txt";

		Execucao execucao = new Execucao();

		try {
			File file = new File(arquivo);
			if (file.exists()) {
				Scanner scanner = new Scanner(file);

// ---------------------------------- Lendo a primeira linha -----------------------------------------
				
				String pLinha[] = scanner.nextLine().split(" ");
				int nPilha = Integer.parseInt(pLinha[0]);
				int nProcesso = Integer.parseInt(pLinha[1]);

// ------------------------------------- Lendo as filas ----------------------------------------------
				
				for (int i = 0; i < nPilha; i++) {
					linha = scanner.nextLine().split(" ");
					if (linha.length > 1) {
						execucao.getLista().add(new ListaProcessos(linha[0], Integer.parseInt(linha[1])));
					} else {
						execucao.getLista().add(new ListaProcessos(linha[0], -1));
					}
				}

// ------------------------------------- Lendo os processos ------------------------------------------
				
				for (int i = 0; i < nProcesso; i++) {
					linha = scanner.nextLine().split(" ");

					pId = Integer.parseInt(linha[0]);
					prioritario = Integer.parseInt(linha[1]);
					tempoChegada = Integer.parseInt(linha[2]);
					bashTime = Integer.parseInt(linha[3]);
					algoritmo = execucao.getLista().get(prioritario).getAlgorithm();

					execucao.getLista().get(prioritario).getProcesses()
							.add(new Processos(pId, tempoChegada, bashTime, prioritario, algoritmo));

					if (tempoChegada > prox)
						prox = tempoChegada;
				}

				scanner.close();
				
// ----------------------------  Ordenando os processos da pilha por ID -------------------------------------
				
				for (ListaProcessos pList : execucao.getLista()) {
					pList.orderById();
				}
				
				execucao.setUltChegada(prox);

// -------------------------------------------- Executando -------------------------------------------------
				
				execucao.execute();
				execucao.finalizar();
				
// --------------------------------------- Formatar os dados e calcular as médias -------------------------------------
				
				float time1 = 0, time2 = 0;
				String grafGantt = "Gráfico de Gantt\n\n", tabela = "Tabela de Processos\n\n", tempoSistema = "Tempo médio no sistema\n",
						tempoEspera = "Tempo médio de espera\n";

				for (ExeGantt ganttEx : execucao.getListaGantt()) {
					grafGantt += "P" + ganttEx.getId() + " (" + ganttEx.getInicio() + "-" + ganttEx.getFim() + ") | ";
				}

				for (ListaProcessos pList : execucao.getLista()) {
					for (Processos p : pList.getProcesses()) {
						time1 += p.getTempoResposta();
						time2 += p.getTempoEspera();

						tabela += "| ID: " + p.getId() + ", PR: " + p.getnAlgoritmos() + ", AT: " + p.getTempoChegada()
								+ ", BT: " + p.getBashTime() + ", CT: " + p.getTempoConclusao() + ", TAT: "
								+ p.getTempoResposta() + ", WT: " + p.getTempoEspera() + " |\n";
					}
				}

				tempoSistema += time1 / nProcesso;
				tempoEspera += time2 / nProcesso;

				String finalString = grafGantt + "\n\n" + tabela + "\n" + tempoSistema + "\n\n" + tempoEspera;

// -------------------------------------- Criação e saída dos dados em um arquivo ---------------------------------------
				
				try {
					FileWriter output = new FileWriter("output.txt");
					output.write(finalString);
					output.close();
					System.out.println("Processo Realizado com Sucesso!! Verifique o arquivo! \"output.txt\".");
				} catch (IOException e) {
					System.out.println("ERRO!!!!!!.");
					e.printStackTrace();
				}
			} else {
				System.err.println("O arquivo \"" + arquivo + "\" não existe.");
			}
		} catch (FileNotFoundException e) {
			System.err.println("Erro durante a abertura do Arquivo! \"" + arquivo + "\".");
		}
	}
}
