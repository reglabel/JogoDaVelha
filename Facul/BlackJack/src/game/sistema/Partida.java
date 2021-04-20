package game.sistema;

import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

import game.entidades.Baralho;
import game.entidades.Jogador;
import game.sistema.excecoes.CodigoInvalido;

public class Partida implements Comparable<Partida>{
	final private String[] tipos = {"EMPATE", "VITÓRIA"};
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
			System.out.print("Para começarmos, escolha com quantos jogadores deseja jogar (entre 2 e 5)!"
					+ "\r\nDigite AQUI: ");
			str = Validacao.ler.nextLine();
			
			while(!Validacao.eUmNumero(str)) {
				System.out.print("Por favor, digite um número válido: ");
				str = Validacao.ler.nextLine();
			}
			
			numeroDeJogadores = Integer.parseInt(str);
		}while(numeroDeJogadores < 2 || numeroDeJogadores > 5);
		
		numeroDeJogadores = Integer.parseInt(str);
		
		return numeroDeJogadores;
	}
	
	private static int classificarPartida(int numeroDeJogadores) {
		if(!historico.estaVazioJogadores()) {
			System.out.print("\r\nDeseja inserir novos jogadores? \r\nOu jogar com jogador(es) já existente(s)? ");
			if(Validacao.perguntar("\r\nOpção [0]: usar [JOGADORES NOVOS]\r\n"
					+ "Opção [1]: usar [JOGADORRES EXISTENTES]\r\nDigite AQUI: ")) {
				if(historico.tamanhoJogadores() < numeroDeJogadores) {
					System.out.println("Infelizmente, não temos jogadores suficientes cadastrados para"
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
					System.out.print("\r\nEscolha um valor NUMÉRICO para o CÓDIGO da partida!\r\n"
							+ "Nota, zeros à esquerda serão desconsiderados!\r\nInsira AQUI: ");
					valor = Validacao.ler.nextLine();
					if(Validacao.eUmNumero(valor)) {
						break;
					} else {
						System.out.println("Por favor, digite um VALOR VÁLIDO. Tente novamente!");
					}
				}
				numero = Integer.parseInt(valor);
				Validacao.jaExisteUmCodigo(numero, codigos);
				numeroDeJogadores = pegarNumeroDeJogadores();
				switch(classificarPartida(numeroDeJogadores)) {
					case 0:
						for(int i = 0; i < numeroDeJogadores; i++) {
							System.out.print("Digite o NOME do "+(i+1)+"º jogador: ");
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
								System.out.print("Digite o NOME do "+(i+1)+"º Jogador: ");
								str = Validacao.ler.nextLine();
								str = str.toUpperCase();
								Jogador jogadorDaVez = historico.getJogador(str);
								
								if(jogadorDaVez == null) {
									System.out.println("Aparentemente, você digitou o nome de alguem"
											+ " que não existe. Tente novamente.");
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
				System.out.print("EXCEÇÃO CAPTURADA em " + e);
			}
		}
		return partida;
	}
	
	public void jogar() {
		while(!encerrarPartida()) {
			for(Jogador i: jogadores) {
				System.out.println("--------------------------------------------------------");
				System.out.println("\r\n-> Vez de: " +i.getNome() + "!\r\nVocê automaticamente pegou uma carta!");
				System.out.println(baralho.darCarta(i));
				while(true) {
					System.out.println("Você tem " + i.getPontuacao() + " pontos!");
					if(Validacao.perguntar("Deseja pegar mais uma carta?\r\n-> Escolha [1] para SIM"
							+ "\r\n-> Escolha [0] para NÃO\r\nDigite AQUI sua resposta: ")) {
						System.out.println(baralho.darCarta(i));
					} else {
						System.out.println("Entendido! Então vamos para o próximo jogador! ;)");
						i.setParouDeJogar(true);
						break;
					}
					
					if(!i.aindaPodeJogar()){
						System.out.println("Nesse momento você já ultrapassou seu limite!\r\n"
								+ "Vamos passar pro próximo!");
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
				System.out.println("\r\n\tEssa partida foi do tipo VITÓRIA!");
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
			nomesVencedores = "Não houveram vencedores! :P";
		}
		return "\r\n-> Codigo: " + codigo + 
				"\r\n-> Tipo: " + tipo +
				"\r\n-> Terminada: " + terminada +
				"\r\n-> Jogadores: [" + nomesJogadores +
				"]\r\n-> Vencedores: [" + nomesVencedores + "]\r\n";
	}
	
	
}
