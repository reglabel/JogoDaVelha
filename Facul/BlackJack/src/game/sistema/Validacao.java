package game.sistema;
import java.util.Scanner;
import java.util.Set;

import game.sistema.excecoes.CodigoInvalido;
import game.sistema.excecoes.NomeInvalido;

public class Validacao {
	public static Scanner ler = new Scanner(System.in);
	
	public static boolean perguntar(String msg){
		// Para 0 retorna falso e para 1 returna true
		boolean vamos = false, naoValido = true;
		int resposta;
		while(naoValido){
			System.out.print(msg);
			try {
				resposta = Integer.parseInt(ler.nextLine());
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
	
	public static void jaExisteUmNome(String nomeAVerificar, Set<String> nomes) throws NomeInvalido{
		int nomePresente;
		
		for(String i: nomes) {
			if((nomeAVerificar).equalsIgnoreCase(i)) {
				throw new NomeInvalido();
			}
		}
	}
	
	public static boolean eUmNumero(String valor) {
		try {
			int numero = Integer.parseInt(valor);
			return true;
		} catch(NumberFormatException e) {
			return false;
		}
	}
	
	public static void jaExisteUmCodigo(int codigoAVerificar, Set<Integer> codigos) throws CodigoInvalido{
		for(int i: codigos) {
			if(Integer.valueOf(codigoAVerificar).equals(Integer.valueOf(i))) {
				throw new CodigoInvalido();
			}
		}
	}

}
