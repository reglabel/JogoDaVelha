package game.entidades;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import game.UI;
import game.sistema.Validacao;

public class Jogador implements Comparable<Jogador>{
	private String nome;
	private int derrotas;
	private int vitorias;
	private Character simbolo;
	private static Set<String> nomes = new TreeSet<String>();
	private List<Integer> partidas = new ArrayList<Integer>();
	
	
	private Jogador(String nome, Character simbolo) {
		this.simbolo = simbolo;
		this.nome = nome;
		nomes.add(nome);
		derrotas = 0;
		vitorias = 0;
	}
	
	public static Jogador criarJogador(String nome, Character simbolo) {
		while(!(Validacao.verificarSimbolo(simbolo, Posicao.SIMBOLOS_VALIDOS))) {
			System.out.print("Insira um simbolo válido ('X' ou 'O'): ");
			simbolo = UI.ler.next().charAt(0);
		}
		
		while(Validacao.jaExisteUmNome(nome, nomes)) {
			System.out.print("Ops! Acabamos de olhar, o nome '" + nome + "' não está disponível.\r\nPor favor, escolha outro: ");
			nome = UI.ler.nextLine();
		}
		Jogador jogador = new Jogador(nome, simbolo);
		return jogador;
	}
	
	public Jogador getJogador() {
		return this;
	}
	
	public String acao(int[] posicao, Tabuleiro tabuleiro) {
		if(tabuleiro.ocupar(posicao, this.simbolo)) {
			//o jogo nao esta deixando a pessoa tentar de novo, consertar isso
			//anunciando vitoria duas vezes
			return "\r\nOperação de JOGADA realizada com sucesso!";
		} else {
			return "\r\nOperação de JOGADA fracassou! Tente novamente!";
		}
	}
	
	public String getNome() {
		return this.nome;
	}
	
	public Character getSimbolo() {
		return this.simbolo;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
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
		Jogador other = (Jogador) obj;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "\r\n-> Nome: " + nome +
				"\r\n-> Derrotas: " + derrotas +
				"\r\n-> Vitorias: " + vitorias + 
				"\r\n-> Simbolo: " + simbolo + toStringDePartidas();
	}

	@Override
	public int compareTo(Jogador o) {
		return this.getNome().compareToIgnoreCase(o.getNome());
	}

	public void adicionarPartida(int codigo) {
		this.partidas.add(codigo);
	}
	
	private String toStringDePartidas(){
		String texto = "\r\n-> Partidas: [";
		if(!partidas.isEmpty()) {
			for(Integer i: partidas) {
				texto = texto.concat(i.toString() + ", ");
			}
		}
		texto = texto.concat("]\r\n");
		return texto;
	}
	
	public Set<String> getNomes(){
		return nomes;
	}
}
