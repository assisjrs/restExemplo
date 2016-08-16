package work.assisjrs.restExemplo.model.service;

import work.assisjrs.restExemplo.model.RestExemploException;

public class TokenInvalidoException extends RestExemploException {
	private static final long serialVersionUID = -6959129930635401536L;

	public TokenInvalidoException(Long id) {
		super(String.format("O token enviado pelo Usuario %s é inválido.", id));
	}
}
