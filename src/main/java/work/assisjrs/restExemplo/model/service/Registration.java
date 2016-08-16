package work.assisjrs.restExemplo.model.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import work.assisjrs.restExemplo.model.Usuarios;
import work.assisjrs.restExemplo.model.entity.Usuario;

@Transactional
@Service
public class Registration {
	@Autowired
	private Usuarios usuarios;
	
	@Autowired
	private Authenticator authenticator;
	
	public Usuario register(Usuario usuario) throws EmailJaCadastradoException {
		Usuario usuarioComEmailExistente = usuarios.getUsuarioPorEmail(usuario.getEmail());

		if (usuarioComEmailExistente != null)
			throw new EmailJaCadastradoException(usuario.getEmail());

		usuario.setLastLogin(new Date());
		
		usuarios.salvar(usuario);
		
		try {
			return authenticator.authenticate(usuario.getEmail(), usuario.getPassword());
		} catch (UsuarioInexistenteException | UsuarioESenhaInvalidosException e) {
			throw new RuntimeException(e);
		}
	}
}
