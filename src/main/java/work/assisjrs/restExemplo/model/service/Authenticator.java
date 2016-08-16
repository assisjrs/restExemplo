package work.assisjrs.restExemplo.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.impl.DefaultClaims;
import work.assisjrs.restExemplo.model.Hash;
import work.assisjrs.restExemplo.model.Usuarios;
import work.assisjrs.restExemplo.model.entity.Usuario;

@Service
public class Authenticator {
	@Autowired
	private Usuarios usuarios;
	
	@Autowired
	private Token token;
	
	@Autowired
	private Hash hash;
	
	public Usuario authenticate(String email, String password)
			throws UsuarioInexistenteException, UsuarioESenhaInvalidosException {
		Usuario usuario = usuarios.getUsuarioPorEmail(email);

		if (usuario == null)
			throw new UsuarioInexistenteException(email);

		if (!usuario.getPassword().equals(hash.encode(password))) {
			throw new UsuarioESenhaInvalidosException(email);
		}
		
		String tokenForAutenticacao = token.tokenizer(usuario, "restExemploSubject");
		
		DefaultClaims body = token.getBody(tokenForAutenticacao);
		
		usuario.setLastLogin(body.getIssuedAt());
		
		usuario.setToken(tokenForAutenticacao);
		
		return usuarios.salvar(usuario);
	}
}
