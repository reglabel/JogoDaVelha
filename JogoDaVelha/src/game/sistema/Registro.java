package game.sistema;

import java.util.Set;
import java.util.TreeSet;

import game.entidades.Jogador;

public class Registro {
	public Set<Jogador> registro;
	
	public Registro() {
		registro = new TreeSet<Jogador>();
	}
	
	public void registrar(Jogador player) {
		registro.add(player);
	}

	@Override
	public String toString() {
		String composicao = "\r\n\t***** Registro *****";
		int contador = 0;
		for(Jogador p: registro) {
			composicao = composicao.concat("\r\n-= Jogador nº " + contador + " =- " + p.toString());
			contador++;
		}
		return composicao;
	}
	
	public boolean estaVazio() {
		return registro.isEmpty();
	}
	
	public int tamanho() {
		return registro.size();
	}
	
	public Jogador getJogador(String nome) {
		Jogador jogador = null;
		for(Jogador p: registro) {
			if(p.getNome().length() == nome.length()) {
				if(p.getNome().contains(nome)) {
					jogador = p.getJogador();
				}
			}
		}
		return jogador;
	}
}
