package game.sistema;

import java.util.Set;

import game.UI;

public class Validacao {
	public static boolean jaExisteUmCodigo(int codigoAVerificar, Set<Integer> codigos) {
		int valorPresente;
		boolean resposta = false;
		for(int i: codigos) {
			if(Integer.valueOf(codigoAVerificar).equals(Integer.valueOf(i))) {
				resposta = true;
				return resposta;
			}
		}
		return resposta;
	}
	
	public static boolean verificar(String msg){
		// Para 0 retorna falso e para 1 returna true
		boolean vamos = false, naoValido = true;
		int resposta;
		while(naoValido){
			System.out.print(msg);
			try {
				resposta = Integer.parseInt(UI.ler.nextLine());
			} catch(NumberFormatException e) {
				resposta = -1;
			}
			switch(resposta){
				case 0:
					naoValido = false;
					vamos = false;
					break;

				case 1:
					naoValido = false;
					vamos = true;
					break;

				default:
					System.out.println("\r\nPor favor, digite valores válidos ([0] ou [1]).");
					break;
			}
		}
		return vamos;
	}
	
	public static boolean verificarSimbolo(Character simbolo, Character[] SIMBOLOS_VALIDOS) {
		if(simbolo.equals(SIMBOLOS_VALIDOS[0]) || simbolo.equals(SIMBOLOS_VALIDOS[1])) {
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean jaExisteUmNome(String nomeAVerificar, Set<String> nomes) {
		int nomePresente;
		boolean resposta = false;
		
		for(String i: nomes) {
			if((nomeAVerificar).equalsIgnoreCase(i)) {
				resposta = true;
				return resposta;
			}
		}
		return resposta;
	}
	
	public static boolean eUmNumeroDoTabuleiro(String valor) {
		int numero;
		try {
			numero = Integer.parseInt(valor);
			if(numero < 0 || numero >= 3) {
				return false;
			} else {
				return true;
			}
		} catch(NumberFormatException e) {
			return false;
		}
	}

}
