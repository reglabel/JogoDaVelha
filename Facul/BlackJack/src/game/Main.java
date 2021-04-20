package game;

import game.sistema.Partida;
import game.sistema.Validacao;

public class Main {
	public static void main(String[] args) {		
		while(true) {
			Partida partida = Partida.criarPartida();
			partida.jogar();
			if(Validacao.perguntar("\r\nDeseja CONTINUAR a JOGAR ou ENCERRAR o programa?"
				+ "\r\nEscolha [0] para CONTINUAR\r\n"
				+ "Escolha [1] para ENCERRAR\r\nDigite AQUI sua resposta: ")) {
				System.out.println("\r\n"+ partida.exibirHistoricoPartidas());
				System.out.println("\r\n\t-= MUITO OBRIGADA POR JOGAR! ATÉ A PRÓXIMA! :D =-");
				break;
			}
		}
	}
}
