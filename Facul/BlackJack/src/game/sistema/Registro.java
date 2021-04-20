package game.sistema;

import java.util.Set;
import java.util.TreeSet;

import game.entidades.Jogador;

public class Registro {
	private Set<Jogador> jogadores;
	private Set<Partida> partidas;

	public Registro() {
		jogadores = new TreeSet<Jogador>();
		partidas = new TreeSet<Partida>();
	}
	
	public void registrarJogadores(Jogador player) {
		jogadores.add(player);
	}
	
	public void registrarPartidas(Partida partida) {
		partidas.add(partida);
	}

	@Override
	public String toString() {
		String composicao = "\r\n\t***** Registro de Jogadores *****";
		int contador = 0;
		for(Jogador p: jogadores) {
			composicao = composicao.concat("\r\n-= Jogador nº " + (contador+1) + " =- " + p.toString());
			contador++;
		}
		return composicao;
	}
	
	public String toStringPartidas() {
		String composicao = "\r\n\t***** Registro de Partidas *****";
		int contador = 0;
		for(Partida p: partidas) {
			composicao = composicao.concat("\r\n-= Partida nº " + (contador+1) + " =- " + p.toString());
			contador++;
		}
		return composicao;
	}
	
	public boolean estaVazioJogadores() {
		return jogadores.isEmpty();
	}
	
	public boolean estaVazioPartidas() {
		return partidas.isEmpty();
	}
	
	public int tamanhoJogadores() {
		return jogadores.size();
	}
	
	public int tamanhoPartidas() {
		return partidas.size();
	}
	
	public Jogador getJogador(String nome) {
		Jogador jogador = null;
		for(Jogador p: jogadores) {
			if(p.getNome().length() == nome.length()) {
				if(p.getNome().contains(nome)) {
					jogador = p.getJogador();
				}
			}
		}
		return jogador;
	}
	
	public Partida getPartida(Integer codigo) {
		Partida partida = null;
		for(Partida p: partidas) {
			if(p.getCodigo() == codigo) {
				partida = p;
			}
		}
		return partida;
	}
}
