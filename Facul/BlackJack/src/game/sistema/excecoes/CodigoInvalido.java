package game.sistema.excecoes;

public class CodigoInvalido extends Exception{
	private static final long serialVersionUID = 1L;
	
	public CodigoInvalido() {
	    super("-> C�digo indispon�vel! Tente novamente.");
	}
}
