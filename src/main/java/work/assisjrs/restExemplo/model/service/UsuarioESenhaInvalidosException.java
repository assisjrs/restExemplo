package work.assisjrs.restExemplo.model.service;

import work.assisjrs.restExemplo.model.RestExemploException;

public class UsuarioESenhaInvalidosException extends RestExemploException {
	private static final long serialVersionUID = 4550231617168419783L;

	public UsuarioESenhaInvalidosException(String email) {
		super(String.format("O Usuario e/ou senha com o email %s inválido.", email));
	}
}
