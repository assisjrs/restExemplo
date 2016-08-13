package work.assisjrs.restExemplo.model;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Component
public class Usuarios {
	@Autowired
	private HibernateTemplate hibernateTemplate;

	public Usuario salvar(Usuario usuario) throws EmailJaCadastradoException {
		List<?> usuariosComEmailExistente = hibernateTemplate.find("FROM Usuario WHERE email = ?", usuario.getEmail());

		if (!usuariosComEmailExistente.isEmpty())
			throw new EmailJaCadastradoException(usuario.getEmail());

		usuario.setCreated(new Date());

		usuario.setModified(usuario.getCreated());
		usuario.setLastLogin(usuario.getCreated());

		hibernateTemplate.saveOrUpdate(usuario);
		
		for (Telefone telefone : usuario.getPhones()) {
			telefone.setUsuario(usuario);
			
			hibernateTemplate.saveOrUpdate(telefone);
		}
		
		hibernateTemplate.flush();

		return usuario;
	}
}
