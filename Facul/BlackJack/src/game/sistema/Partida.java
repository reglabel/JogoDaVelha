package game.sistema;

import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

import game.entidades.Baralho;
import game.entidades.Jogador;
import game.sistema.excecoes.CodigoInvalido;

public class Partida implements Comparable<Partida>{
	final private String[] tipos = {"EMPATE", "VIT�RIA"};
	private String tipo;
	private int codigo;
	private boolean terminada;
	private Baralho baralho;
	private int[] pontuacoes;
	private List<Jogador> jogadores = new ArrayList<Jogador>();
	private List<Jogador> vencedores = new ArrayList<Jogador>();
	private static Registro historico = new Registro();
	private static Set<Integer> codigos = new TreeSet<Integer>();
	
	private Partida() {
	}
	
	private Partida(int codigo, List<Jogador> jogadores, boolean novos) {
		this.terminada = false;
		
		for(Jogador j: jogadores) {
			this.jogadores.add(j);
			j.adicionarPartida(codigo);
			if(novos) {
				historico.registrarJogadores(j);
			}
		}
		this.codigo = codigo;
		
		codigos.add(codigo);
		baralho = new Baralho();
		historico.registrarPartidas(this);
		pontuacoes = new int[jogadores.size()];
	}
	
	public String exibirHistoricoJogadores() {
		return historico.toString();
	}
	
	public String exibirHistoricoPartidas() {
		return historico.toStringPartidas();
	}
	
	private static int pegarNumeroDeJogadores() {
		int numeroDeJogadores = 0;
		String str;
		do {
			System.out.print("Para come�armos, escolha com quantos jogadores deseja jogar (entre 2 e 5)!"
					+ "\r\nDigite AQUI: ");
			str = Validacao.ler.nextLine();
			
			while(!Validacao.eUmNumero(str)) {
				System.out.print("Por favor, digite um n�mero v�lido: ");
				str = Validacao.ler.nextLine();
			}
			
			numeroDeJogadores = Integer.parseInt(str);
		}while(numeroDeJogadores < 2 || numeroDeJogadores > 5);
		
		numeroDeJogadores = Integer.parseInt(str);
		
		return numeroDeJogadores;
	}
	
	private static int classificarPartida(int numeroDeJogadores) {
		if(!historico.estaVazioJogadores()) {
			System.out.print("\r\nDeseja inserir novos jogadores? \r\nOu jogar com jogador(es) j� existente(s)? ");
			if(Validacao.perguntar("\r\nOp��o [0]: usar [JOGADORES NOVOS]\r\n"
					+ "Op��o [1]: usar [JOGADORRES EXISTENTES]\r\nDigite AQUI: ")) {
				if(historico.tamanhoJogadores() < numeroDeJogadores) {
					System.out.println("Infelizmente, n�o temos jogadores suficientes cadastrados para"
							+ " essa partida.\r\nPortanto, vamos cadastrar cada um do zero. :)");
					//gente nova
					return 0;
				} else {
					//gente velha
					return 1;
				}
			}  else {
				// gente nova
				return 0;
			}
		} else {
			System.out.println("\r\nConfigurando nova partida...");
			//gente nova
			return 0;
		}
	}
	
	public static Partida criarPartida() {
		Partida partida = new Partida();
		int numero, numeroDeJogadores;
		String str, valor;
		
		String nome;
		ArrayList<Jogador> jogadores = new ArrayList<Jogador>();
		
		boolean teste = true;
		System.out.println("\r\n\t\t####### BLACKJACK21 #######\r\n");
		System.out.println("\t\t  ~~ Sejam bem-vindos! ~~");
		
		while(teste) {
			try {
				while(true) {
					System.out.print("\r\nEscolha um valor NUM�RICO para o C�DIGO da partida!\r\n"
							+ "Nota, zeros � esquerda ser�o desconsiderados!\r\nInsira AQUI: ");
					valor = Validacao.ler.nextLine();
					if(Validacao.eUmNumero(valor)) {
						break;
					} else {
						System.out.println("Por favor, digite um VALOR V�LIDO. Tente novamente!");
					}
				}
				numero = Integer.parseInt(valor);
				Validacao.jaExisteUmCodigo(numero, codigos);
				numeroDeJogadores = pegarNumeroDeJogadores();
				switch(classificarPartida(numeroDeJogadores)) {
					case 0:
						for(int i = 0; i < numeroDeJogadores; i++) {
							System.out.print("Digite o NOME do "+(i+1)+"� jogador: ");
							nome = Validacao.ler.nextLine();
							nome = nome.toUpperCase();
							jogadores.add(Jogador.criarJogador(nome));
						}
						partida = new Partida(numero, jogadores, true);
						break;
						
					default:
						System.out.println(historico.toString());
						for(int i = 0; i< numeroDeJogadores; i++) {
							while(true) {
								System.out.print("Digite o NOME do "+(i+1)+"� Jogador: ");
								str = Validacao.ler.nextLine();
								str = str.toUpperCase();
								Jogador jogadorDaVez = historico.getJogador(str);
								
								if(jogadorDaVez == null) {
									System.out.println("Aparentemente, voc� digitou o nome de alguem"
											+ " que n�o existe. Tente novamente.");
								} else {
									jogadores.add(jogadorDaVez);
									break;
								}
							}
						}
						partida = new Partida(numero, jogadores, false);
						break;
				}
				teste = false;
			} catch(CodigoInvalido e) {
				System.out.print("EXCE��O CAPTURADA em " + e);
			}
		}
		return partida;
	}
	
	public void jogar() {
		while(!encerrarPartida()) {
			for(Jogador i: jogadores) {
				System.out.println("--------------------------------------------------------");
				System.out.println("\r\n-> Vez de: " +i.getNome() + "!\r\nVoc� automaticamente pegou uma carta!");
				System.out.println(baralho.darCarta(i));
				while(true) {
					System.out.println("Voc� tem " + i.getPontuacao() + " pontos!");
					if(Validacao.perguntar("Deseja pegar mais uma carta?\r\n-> Escolha [1] para SIM"
							+ "\r\n-> Escolha [0] para N�O\r\nDigite AQUI sua resposta: ")) {
						System.out.println(baralho.darCarta(i));
					} else {
						System.out.println("Entendido! Ent�o vamos para o pr�ximo jogador! ;)");
						i.setParouDeJogar(true);
						break;
					}
					
					if(!i.aindaPodeJogar()){
						System.out.println("Nesse momento voc� j� ultrapassou seu limite!\r\n"
								+ "Vamos passar pro pr�ximo!");
						i.setParouDeJogar(true);
						break;
					}
				}
				
			}
		}
		
		
	}
	
	private boolean encerrarPartida() {
		for(Jogador i: jogadores) {
			if(!i.getParouDeJogar()) {
				return false;
			} 
		}
		
		System.out.println("\r\n\t\t-=-=- RESULTADOS =-=-=");
		for(Jogador i: jogadores) {
			System.out.println("\r\n\t-> " + i.getNome() +", PONTOS: " + i.getPontuacao());
		}
		
		for(Jogador i: jogadores) {
			if(i.getPontuacao() <= 21) {
				this.tipo = tipos[1];
				System.out.println("\r\n\tEssa partida foi do tipo VIT�RIA!");
				getVencedor();
				break;
			}
		}
		
		if(tipo == null) {
			this.tipo = tipos[0];
			System.out.println("\r\n\tEssa partida foi do tipo EMPATE!");
			int contador = 0;
			for(Jogador i: jogadores) {
				pontuacoes[contador] = i.getPontuacao();
				i.setEmpate();
				contador++;
			}
		}
		
		this.terminada = true;
		return true;
	}
	
	public int getCodigo() {
		return this.codigo;
	}
	
	private void getVencedor() {
		if(tipo != null) {
			List<Jogador> jogadoresVencedores = new ArrayList<Jogador>();
			for(Jogador i: jogadores) {
				if(i.getPontuacao() <= 21) {
					jogadoresVencedores.add(i);
				}
			}
			
			
			System.out.println("\r\n\t\t@@@@@ VENCEDOR(ES) @@@@@");
			for(Jogador i: jogadoresVencedores) {
				vencedores.add(i);
				System.out.println("\r\n\t-> " + i.getNome() +", " + i.getPontuacao() +" PONTOS" );
			}
			
			int contador = 0;
			for(Jogador i: jogadores) {
				if(!jogadoresVencedores.contains(i)) {
					pontuacoes[contador] = i.getPontuacao();
					i.setDerrota();
					contador++;
				} else {
					pontuacoes[contador] = i.getPontuacao();
					i.setVitoria();
					contador++;
				}
			}
		}
	}
	
	public List<Jogador> getJogadores() {
		return jogadores;
	}

	
	@Override
	public int compareTo(Partida o) {
		if(this.getCodigo() > o.getCodigo()) {
			return 1;
		} else if (this.getCodigo() < o.getCodigo()) {
			return -1;
		} else {
			return 0;
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + codigo;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Partida other = (Partida) obj;
		if (codigo != other.codigo)
			return false;
		return true;
	}
	

	@Override
	public String toString() {
		String nomesJogadores = "", nomesVencedores = "";
		int contador = 0;
		for(Jogador i: jogadores) {
			nomesJogadores = nomesJogadores.concat("{"+i.getNome()+ ", " + pontuacoes[contador] + " PONTOS} ,");
			contador++;
		}
		
		if(!vencedores.isEmpty()) {
			for(Jogador i: vencedores) {
				nomesVencedores = nomesVencedores.concat(i.getNome()+ ", ");
			}
		} else {
			nomesVencedores = "N�o houveram vencedores! :P";
		}
		return "\r\n-> Codigo: " + codigo + 
				"\r\n-> Tipo: " + tipo +
				"\r\n-> Terminada: " + terminada +
				"\r\n-> Jogadores: [" + nomesJogadores +
				"]\r\n-> Vencedores: [" + nomesVencedores + "]\r\n";
	}
	
	
}
