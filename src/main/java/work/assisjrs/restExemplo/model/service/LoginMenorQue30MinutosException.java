package work.assisjrs.restExemplo.model.service;

import work.assisjrs.restExemplo.model.RestExemploException;

public class LoginMenorQue30MinutosException  extends RestExemploException{
	private static final long serialVersionUID = -8269006862195152842L;

	public LoginMenorQue30MinutosException(String email) {
		super(String.format("O email %s possui o login MENOS de 30 minutos.", email));
	}
}
