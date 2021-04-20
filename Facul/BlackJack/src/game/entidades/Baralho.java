package game.entidades;

import java.util.List;
import java.util.Random;

public class Baralho {
	private List<Carta> baralho;

	public Baralho() {
		baralho = Carta.criarBaralho();
	}
	
	public String darCarta(Jogador jogador) {
		Random sorte = new Random();
		int escolhida;
		String msg = "";
		escolhida = sorte.nextInt(baralho.size());
		
		if(jogador.getPontuacao() <= 21 && (baralho.get(escolhida).getValor() + jogador.getPontuacao()) <= 21) {
			jogador.setPontuacao(baralho.get(escolhida));
			
			msg = "\r\n=======================\r\n" + jogador.getNome() + " tirou " + baralho.get(escolhida).representacao + "!\r\n"
					+ "Foram ganhos " + baralho.get(escolhida).getValor() +" pontos!\r\n=======================\r\n";
			baralho.remove(escolhida);
		} else {
			jogador.setPontuacao(baralho.get(escolhida));
			msg = "\r\n=======================\r\n" + jogador.getNome() + " tirou " + baralho.get(escolhida).representacao + "!\r\n"
					+ "Ela vale " + baralho.get(escolhida).getValor() + " pontos!\r\n"
							+ "Isso ultrapassa seu limite de 21 pontos.\r\n=======================\r\n";
			baralho.remove(escolhida);
		}
		return msg;
	}
	
}
