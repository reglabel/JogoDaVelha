package game.sistema.excecoes;

public class CodigoInvalido extends Exception{
	private static final long serialVersionUID = 1L;
	
	public CodigoInvalido() {
	    super("-> Código indisponível! Tente novamente.");
	}
}
