package game.sistema;

import java.util.Set;
import java.util.TreeSet;

import game.UI;

import game.entidades.*;
//ver colocar nomes dos jogadores em upper case
public class Partida {
	private int codigo;
	private boolean terminada;
	private Tabuleiro tabuleiro;
	private Jogador player1;
	private Jogador player2;
	private static Registro historico = new Registro();
	private static Set<Integer> codigos = new TreeSet<Integer>();
	
	
	private Partida(int codigo, Jogador player1, String nome2) {
		this.terminada = false;
		
		nome2 = nome2.toUpperCase();
		
		Character simbolo_ocupado = player1.getSimbolo();
		Character simbolo_disponivel;
		
		if(simbolo_ocupado.equals('X')) {
			simbolo_disponivel = 'O';
		} else {
			simbolo_disponivel = 'X';
		}
		
		this.player1 = player1;
		this.player2 = Jogador.criarJogador(nome2, simbolo_disponivel);
		this.codigo = codigo;
		codigos.add(codigo);
		tabuleiro = new Tabuleiro();
		
		historico.registrar(player2);
		this.player1.adicionarPartida(this.codigo);
		this.player2.adicionarPartida(this.codigo);
	}
	
	
	private Partida(int codigo, Jogador player1, Jogador player2, boolean eNovo) {
		this.terminada = false;

		this.player1 = player1;
		this.player2 = player2;
		this.codigo = codigo;
		codigos.add(codigo);
		tabuleiro = new Tabuleiro();
		if(eNovo) {
			historico.registrar(player1);
			historico.registrar(player2);
		}
		this.player1.adicionarPartida(this.codigo);
		this.player2.adicionarPartida(this.codigo);
	}
	
	
	public String exibirHistorico() {
		return historico.toString();
	}
	
	// esse metodo deve tratar de anunciar a situacao e, a depender disso, chamar o jogar
	public String anunciarSituacao(Jogador jogador) {
		if(tabuleiro.situacao() == -1) {
			tabuleiro.visaoGrafica();
			return jogar(jogador);
		} else {
			return encerrarPartida();
		}
	}

	public static Partida iniciarPartida() {
		System.out.println("\r\n\t\t####### JOGO DA VELHA #######\r\n");
		if(!historico.estaVazio()) {
			System.out.print("Bem-vindos! Deseja inserir novos jogadores? \r\nOu jogar com jogador(es) já existente(s)? ");
			if(!Validacao.verificar("\r\nOpção [0]: usar [JOGADORES NOVOS]\r\n"
					+ "Opção [1]: usar 1 OU 2 [JOGADOR(RES) EXISTENTE(S)]\r\nDigite AQUI: ")) {
				//retorna falso para 0 e true pra 1
				return criarPartida(1);
			} else {
				historico.toString();
				if(historico.tamanho() == 1) {
					return criarPartida(3);	
				} else {
					if(Validacao.verificar("\r\nOpção [0]: usar 1 [JOGADOR NOVO] e 1 [JOGADOR EXISTENTE]\r\n"
							+ "Opção [1]: usar [SOMENTE JOGADORES EXISTENTES]\r\nDigite AQUI: ")) {
						return criarPartida(2);
					} else {
						return criarPartida(3);
					}
				}
			}
		} else {
			System.out.println("Bem-vindos! Configurando nova partida...");
			return criarPartida(1);
		}
	}
	
	private static Partida criarPartida(int valor) {
		Partida partida;
		int codigo = 000;
		String str;
		boolean valido = false;
		
		while(true) {
			do {
				System.out.print("\r\nInsira código numérico da partida: ");
				str = UI.ler.nextLine();
				try {
					codigo = Integer.parseInt(str);
					valido = true;
				} catch(NumberFormatException e) {
					valido = false;
					System.out.println("Código numérico inválido. Tente novamente.");
				}
			} while(!valido);
			
			if(Validacao.jaExisteUmCodigo(codigo, codigos)) {
				System.out.println("Já existe uma partida com tal código. Tente novamente.");
			} else {
				break;
			}
		}
		
		switch(valor) {
			case 1:
				//usar novos
				String nome1, nome2;
				
				System.out.print("Insira nome do 1º jogador (X): ");
				nome1 = UI.ler.nextLine();
				nome1 = nome1.toUpperCase();
				Jogador player1 = Jogador.criarJogador(nome1, 'X');
				
				System.out.print("Insira nome do 2º jogador (O): ");
				nome2 = UI.ler.nextLine();
				nome2 = nome2.toUpperCase();
				Jogador player2 = Jogador.criarJogador(nome2, 'O');
				
				partida = new Partida(codigo, player1, player2, true);
				break;
				
			case 2:
				System.out.println(historico.toString());
				String str2;
				Jogador[] jogadores = {null, null}; 
				Character simbolo_ocupado = '-';
				
				for(int i = 0; i<2; i++) {
					while(true) {
						System.out.print("Digite o nome do "+(i+1)+"º Jogador: ");
						str2 = UI.ler.nextLine();
						str2 = str2.toUpperCase();
						
						jogadores[i] = historico.getJogador(str2);
						if(jogadores[i] == null) {
							System.out.println("Aparentemente, você digitou o nome de alguem"
									+ " que não existe. Tente novamente.");
						} else {
							if(i == 0) {
								simbolo_ocupado = jogadores[0].getSimbolo();
								break;
							} else {
								if(jogadores[i].getSimbolo().equals(simbolo_ocupado)) {
									System.out.println("Você escolheu dois jogadores com o mesmo"
											+ " símbolo. Escolha outro segundo jogador.");
								} else {
									break;
								}
							}
						}
					}
				}
				
				partida = new Partida(codigo, jogadores[0], jogadores[1], false);
				break;
				
			default:
				System.out.println(historico.toString());
				String str3, nomeNovo;
				Jogador jogador = null;
				
				while(true) {
					System.out.print("Digite o nome do Jogador existente: ");
					str3 = UI.ler.nextLine();
					str3 = str3.toUpperCase();
					
					jogador = historico.getJogador(str3);
					if(jogador == null) {
						System.out.println("Aparentemente, você digitou o nome de alguem"
								+ " que não existe. Tente novamente.");
					} else {
						break;
					}
				}
				
				
				System.out.print("Digite o nome do novo jogador: ");
				nomeNovo = UI.ler.nextLine();
				partida = new Partida(codigo, jogador, nomeNovo);
				break;
		}
		return partida;
	}
	
	
	//esse metodo deve tratar de convidar o jogador a jogar
	private String jogar(Jogador player) {
		int[] posicao = Posicao.coletarPosicao(player);
		return player.acao(posicao, tabuleiro);
	}
	
	private Jogador quemVenceu() {
		Jogador vencedor = null;
		if(tabuleiro.getSimboloVencedor() != null) {
			if(player1.getSimbolo().equals(tabuleiro.getSimboloVencedor())) {
				vencedor = player1;
			} else {
				vencedor = player2;
			}
		}
		return vencedor;
	}
	
	private String encerrarPartida() {
		this.terminada = true;
		if(quemVenceu() == null) {
			return "DEU VELHA!";
		} else {
			Jogador vencedor, perdedor;
			if(quemVenceu().getJogador().equals(player1)){
				vencedor = player1;
				perdedor = player2;
			} else {
				vencedor = player2;
				perdedor = player1;
			}
			
			return "VENCEDOR: "+vencedor.getNome()+"\r\nPERDEDOR: "+perdedor.getNome()+"\r\n";
		}
	}
	
	public boolean isTerminada() {
		return this.terminada;
	}
	
	public Jogador[] getJogadores() {
		Jogador[] jogadores = {player1.getJogador(), player2.getJogador()};
		return jogadores;
	}
}
