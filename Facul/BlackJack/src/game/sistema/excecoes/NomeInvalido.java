package game.sistema.excecoes;

public class NomeInvalido extends Exception{
	private static final long serialVersionUID = 1L;
	
	public NomeInvalido() {
	    super("-> Nome indisponível! Tente novamente.");
	}
}
