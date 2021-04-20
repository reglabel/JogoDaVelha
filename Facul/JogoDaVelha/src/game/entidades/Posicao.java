package game.entidades;

import java.util.Arrays;
import java.util.List;

import game.UI;
import game.sistema.Validacao;

import java.util.ArrayList;

public class Posicao {
	//implements Comparable<Posicao>
	final static Character[] SIMBOLOS_VALIDOS = {'X', 'O'};
	private int[] id = new int[2];
	private Character simbolo; 
	private boolean ocupada;
	
	public Posicao() {
		this.ocupada = false;
		this.simbolo = '-';
	}
	
	public static int[] coletarPosicao(Jogador jogador) {
		int linha, coluna;
		String str;
		
		System.out.println("\r\n\t-= VEZ de :" +jogador.getNome() + "!");
		System.out.print("-> Digite AQUI o número da LINHA escolhida: ");
		str = UI.ler.nextLine();
		while(!Validacao.eUmNumeroDoTabuleiro(str)) {
			System.out.print("Por favor, digite um NÚMERO VÁLIDO ([0], [1] ou [2]): ");
			str = UI.ler.nextLine();
		}
		linha = Integer.parseInt(str);
		
		System.out.print("-> Digite AQUI o número da COLUNA escolhida: ");
		str = UI.ler.nextLine();
		while(!Validacao.eUmNumeroDoTabuleiro(str)) {
			System.out.print("Por favor, digite um NÚMERO VÁLIDO ([0], [1] ou [2]): ");
			str = UI.ler.nextLine();
		}
		coluna = Integer.parseInt(str);
		
		int[] idValido = {linha, coluna};
		return idValido;
	}
	
	public boolean marcar(Character simbolo) {
		boolean sucesso = false;
		if(!ocupada) {
			if(Validacao.verificarSimbolo(simbolo, SIMBOLOS_VALIDOS)) {
				this.ocupada = true;
				this.simbolo = simbolo;
				sucesso = true;
			}
		}
		return sucesso;
	}

	public static List<Posicao> createCatalogo(){
		List<Posicao> catalogo = new ArrayList<Posicao>();
		
		for(int i=0; i<3; i++) {
			for(int j=0; j<3; j++) {
				Posicao posicao = new Posicao();
				int[] id = {i, j};
				posicao.setId(id);
				catalogo.add(posicao);
			}
		}
		return catalogo;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(id);
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
		Posicao other = (Posicao) obj;
		if (!Arrays.equals(id, other.id))
			return false;
		return true;
	}
	
	public Character getSimbolo() {
		return simbolo;
	}
	
	public int[] getId() {
		return id;
	}

	private void setId(int[] id) {
		this.id = id;
	}

	/*
	@Override
	public int compareTo(Posicao o) {
		int valor = 0;
		for(int i = 0; i<2; i++) {
			if(!(this.getId()[i] < o.getId()[i])) {
				valor = 1;
			} else if((this.getId()[i] < o.getId()[i])) {
				valor = -1;
			}
		}
		return valor;
	}
	*/
	@Override
	public String toString() {
		return "["+this.simbolo+"]";
	}
}
