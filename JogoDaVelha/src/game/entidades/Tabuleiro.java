package game.entidades;

import java.util.Arrays;
import java.util.List;

public class Tabuleiro {
	private List<Posicao> catalogo;
	Character[] linhas, colunas, diagonalPrincipal, diagonalSecundaria; 
	final private int[] valores = {0, 1, 2, 3, 4, 5, 6, 7, 8};
	final private Character[] vitoria1 = {'X','X','X'};
	final private Character[] vitoria2 = {'O','O','O'};
	private Character simboloVencedor;
	
	public Tabuleiro() {
		catalogo = Posicao.createCatalogo();
		this.linhas = new Character[3];
		this.colunas = new Character[3];
		this.diagonalPrincipal = new Character[3];
		this.diagonalSecundaria = new Character[3];
		this.simboloVencedor = '-';
	}
	
	public Character getSimboloVencedor() {
		if(simboloVencedor.equals('-')) {
			return null;
		} else {
			return simboloVencedor;
		}
	}
	
	public boolean ocupar(int[] posicao, Character simbolo) {
		boolean sucesso = false;
		for(Posicao p: catalogo) {
			if(Arrays.equals(posicao, p.getId())) {
				sucesso = p.marcar(simbolo);
			} 
		}
		return sucesso;
	}
	
	public int situacao() {
		//retornar 0 para conclusao e 1 para velha e -1 para nao acabou
		diagonalPrincipal[0] = catalogo.get(0).getSimbolo();
		diagonalPrincipal[1] = catalogo.get(4).getSimbolo();
		diagonalPrincipal[2] = catalogo.get(8).getSimbolo();
		if(Arrays.equals(diagonalPrincipal, vitoria1) || Arrays.equals(diagonalPrincipal, vitoria2)) {
			if(Arrays.equals(diagonalPrincipal, vitoria1)) {
				simboloVencedor = 'X';
			} else {
				simboloVencedor = 'O';
			}
			return 0;
		}
		
		diagonalSecundaria[0] = catalogo.get(2).getSimbolo();
		diagonalSecundaria[1] = catalogo.get(4).getSimbolo();
		diagonalSecundaria[2] = catalogo.get(6).getSimbolo();
		if(Arrays.equals(diagonalSecundaria, vitoria1) || Arrays.equals(diagonalSecundaria, vitoria2)) {
			if(Arrays.equals(diagonalSecundaria, vitoria1)) {
				simboloVencedor = 'X';
			} else {
				simboloVencedor = 'O';
			}
			return 0;
		}
		
		for(int j = 0; j < 3; j++) {
			int fator = 0, limite = 3;
			int passadas = 0;
			
			if(j == 1) {
				fator = 3;
				limite = 6;
			} else if (j == 2) {
				fator = 6;
				limite = 9;
			}
			
			for(int i = fator; i < limite; i++) {
				linhas[passadas] = catalogo.get(valores[i]).getSimbolo();
				passadas++;
			}
			
			if(Arrays.equals(linhas, vitoria1) || Arrays.equals(linhas, vitoria2)) {
				if(Arrays.equals(linhas, vitoria1)) {
					simboloVencedor = 'X';
				} else {
					simboloVencedor = 'O';
				}
				return 0;
			}
		}
		
		
		for(int j = 0; j < 3; j++) {
			int fator = 0, limite = 7;
			int passadas = 0;
			
			if(j == 1) {
				fator = 1;
				limite = 8;
			} else if (j == 2) {
				fator = 2;
				limite = 9;
			}
			
			for(int i = fator; i < limite; i += 3) {
				colunas[passadas] = catalogo.get(valores[i]).getSimbolo();
				passadas++;
			}
			
			if(Arrays.equals(colunas, vitoria1) || Arrays.equals(colunas, vitoria2)) {
				if(Arrays.equals(colunas, vitoria1)) {
					simboloVencedor = 'X';
				} else {
					simboloVencedor = 'O';
				}
				return 0;
			}
		}
		
		for(Posicao i: catalogo) {
			if(i.getSimbolo().equals('-')) {
				return -1;
			}
		}
		
		return 1;
	}

	public void visaoGrafica() {
		System.out.println("\r\n\t\t-- TABULEIRO --");
		System.out.println("\t\t0C\t1C\t2C");
		System.out.print("\t0L");
		int contador = -1;
		boolean passouUmaVez = false;
		for(Posicao j: catalogo) {
			contador++;
			if(contador == 3) {
				if(!passouUmaVez) {
					passouUmaVez = true;
					System.out.print("\r\n\t1L");
				} else {
					System.out.print("\r\n\t2L");
				}
				contador = 0;
			}
			System.out.print("\t"+j.toString());
		}
	}

}
