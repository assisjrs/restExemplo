package work.assisjrs.restExemplo.model;

public class RestExemploException extends Exception {
	private static final long serialVersionUID = -6429328579869902574L;

	public RestExemploException() {
	}

	public RestExemploException(String mensagem) {
		super(mensagem);
	}

	public RestExemploException(String mensagem, Throwable throwable) {
		super(mensagem, throwable);
	}

	public RestExemploException(Throwable throwable) {
		super(throwable);
	}
}
