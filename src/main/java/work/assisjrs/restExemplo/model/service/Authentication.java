package work.assisjrs.restExemplo.model.service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import work.assisjrs.restExemplo.model.UsuarioESenhaInvalidosException;
import work.assisjrs.restExemplo.model.UsuarioInexistenteException;
import work.assisjrs.restExemplo.model.Usuarios;
import work.assisjrs.restExemplo.model.entity.Usuario;

@Service
public class Authentication {
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private Usuarios usuarios;
	
	public Usuario authenticate(String email, String password)
			throws UsuarioInexistenteException, UsuarioESenhaInvalidosException {
		Usuario usuario = usuarios.getUsuarioPorEmail(email);

		if (usuario == null)
			throw new UsuarioInexistenteException(email);

		if (!usuario.getPassword().equals(password)) {
			throw new UsuarioESenhaInvalidosException(email);
		}

		return usuario;
	}
}
