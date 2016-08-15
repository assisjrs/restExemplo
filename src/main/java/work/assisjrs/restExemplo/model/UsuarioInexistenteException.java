package work.assisjrs.restExemplo.model;

public class UsuarioInexistenteException extends RestExemploException {
	private static final long serialVersionUID = 4112459206862734376L;

	public UsuarioInexistenteException(String email) {
		super(String.format("O Usuario com o email %s não já está cadastrado no sistema.", email));
	}
}
