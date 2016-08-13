package work.assisjrs.restExemplo.model;

public class EmailJaCadastradoException extends RestExemploException {
	private static final long serialVersionUID = 8080043096518948733L;
	
	public EmailJaCadastradoException(String email) {
		super(String.format("O email %s já está cadastrado no sistema.", email));
	}
}
