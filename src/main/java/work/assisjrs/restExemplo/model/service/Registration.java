package work.assisjrs.restExemplo.model.service;

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
	
	public Usuario register(Usuario usuario) throws EmailJaCadastradoException {
		Usuario usuarioComEmailExistente = usuarios.getUsuarioPorEmail(usuario.getEmail());

		if (usuarioComEmailExistente != null)
			throw new EmailJaCadastradoException(usuario.getEmail());

		usuarios.salvar(usuario);
		
		usuario.setModified(usuario.getCreated());
		usuario.setLastLogin(usuario.getCreated());

		return usuario;
	}
}
