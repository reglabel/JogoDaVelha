package game.entidades;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import game.sistema.Validacao;
import game.sistema.excecoes.NomeInvalido;

public class Jogador implements Comparable<Jogador>{
	private String nome;
	private int derrotas;
	private int vitorias;
	private int empates;
	private int pontuacao;
	private boolean podeJogar, parouDeJogar;
	private static Set<String> nomes = new TreeSet<String>();
	private List<Integer> partidas = new ArrayList<Integer>();
	
	private Jogador(String nome) {
		this.podeJogar = true;
		this.parouDeJogar = false;
		this.nome = nome;
		this.derrotas = 0;
		this.vitorias = 0;
		this.empates = 0;
		this.pontuacao = 0;
		nomes.add(nome);
	}
	
	public void setParouDeJogar(boolean resposta) {
		this.parouDeJogar = resposta;
	}
	
	public boolean getParouDeJogar() {
		return this.parouDeJogar;
	}
	
	
	public boolean aindaPodeJogar() {
		if(pontuacao > 21) {
			podeJogar = false;
		} else {
			podeJogar = true;
		}
		return podeJogar;
	}
	
	public static Jogador criarJogador(String nome) {
		Jogador jogador;
		
		while(true) {
			try {
				Validacao.jaExisteUmNome(nome, nomes);
				jogador = new Jogador(nome);
				break;
			} catch(NomeInvalido e) {
				System.out.print("EXCEÇÃO CAPTURADA em " + e + "\r\nDigite novo NOME aqui: ");
				nome = Validacao.ler.nextLine();
				nome = nome.toUpperCase();
			}
		}
		
		return jogador;
	}
	
	public Jogador getJogador() {
		return this;
	}
	
	public String getNome() {
		return this.nome;
	}
	
	public int getPontuacao() {
		return this.pontuacao;
	}
	
	@Override
	public String toString() {
		return "\r\n-> Nome: " + nome +
				"\r\n-> Vitorias: " + vitorias +
				"\r\n-> Empates: " + empates + 
				"\r\n-> Derrotas: " + derrotas +
				"\r\n-> Pontuacao: " + pontuacao + toStringDePartidas();
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
	public int compareTo(Jogador o) {
		return this.getNome().compareToIgnoreCase(o.getNome());
	}
	
	public void setPontuacao(Carta carta) {
		this.pontuacao += carta.concederPontos(this);
	}
	
	public void setVitoria() {
		if(parouDeJogar) {
			if(pontuacao <= 21) {
				vitorias+=1;
				this.pontuacao = 0;
				this.podeJogar = true;
				this.parouDeJogar = false;
			}
		}
	}
	
	public void setDerrota() {
		if(parouDeJogar) {
			if(pontuacao > 21) {
				derrotas+=1;
				this.pontuacao = 0;
				this.podeJogar = true;
				this.parouDeJogar = false;
			}
		}
	}
	
	public void setEmpate() {
		if(parouDeJogar) {
			if(pontuacao > 21) {
				empates+=1;
				this.pontuacao = 0;
				this.podeJogar = true;
				this.parouDeJogar = false;
			}
		}
	}
}