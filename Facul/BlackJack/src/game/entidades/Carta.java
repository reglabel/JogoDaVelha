package game.entidades;

import java.util.ArrayList;
import java.util.List;

public class Carta {
	String representacao;
	int valor;
	
	final private static String[] NAIPES = {"O", "E", "P", "C"};
	final private static String[] VALORES = {"A", "J", "Q", "K", "2", "3", 
			"4", "5", "6", "7", "8", "9", "10"};
	
	private Carta(String naipe, String valor) {
		String str = "";
		str = str.concat(valor);
		str = str.concat(naipe);
		this.representacao = str;
		this.valor = determinarValor();
	}
	
	public int determinarValor() {
		int pontuacao = 0;
		for(int i = 0; i < 13; i++) {
			if(i == 0) {
				if(this.representacao.contains(VALORES[i])) {
					pontuacao = 1;
					break;
				}
			} else if (i < 4){
				if(this.representacao.contains(VALORES[i])) {
					pontuacao = 10;
					break;
				}
			} else {
				if(this.representacao.contains(VALORES[i])) {
					pontuacao = Integer.parseInt(VALORES[i]);
				}
			}
		}
		return pontuacao;
	}
	
	public static List<Carta> criarBaralho(){
		List<Carta> baralho = new ArrayList<Carta>();
		
		for(String i: NAIPES) {
			for(String j: VALORES) {
				Carta carta = new Carta(j, i);
				baralho.add(carta);
			}
		}
		return baralho;
	}
	
	public int concederPontos(Jogador jogador) {
		return this.valor;
	}
	
	public int getValor() {
		return valor;
	}
	
	public static String[] getNaipes() {
		return NAIPES;
	}
	
	public static String[] getValores() {
		return VALORES;
	}
}
