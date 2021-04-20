package game;

import java.util.Scanner;

import game.entidades.Jogador;
import game.sistema.Partida;
import game.sistema.Validacao;

public class UI {
	
	public static Scanner ler = new Scanner(System.in);
	
	public static void main(String[] args) {
		//trabalhar com loop e criar a ideia de usar jogadores ja usados ou usar novos
		do {
			Partida partida = Partida.iniciarPartida();
			Jogador[] jogadores = {partida.getJogadores()[0].getJogador(), partida.getJogadores()[1].getJogador()};
			while(!partida.isTerminada()) {
				for(Jogador i: jogadores) {
					System.out.println(partida.anunciarSituacao(i));
					if(partida.isTerminada()) {
						break;
					}
				}
			}
			partida.exibirHistorico();
		}while(!Validacao.verificar("Deseja CONTINUAR a JOGAR?\r\nEscolha [0] para SIM\r\n"
				+ "Escolha [1] para NÃO\r\nDigite AQUI sua resposta: "));
		
	}
	
}
