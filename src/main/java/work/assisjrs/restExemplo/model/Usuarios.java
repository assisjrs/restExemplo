package work.assisjrs.restExemplo.model;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public class Usuarios
{
	@PersistenceContext
	private EntityManager entityManager;
	
	@Transactional
	public Usuario salvar(Usuario usuario) throws EmailJaCadastradoException {
		List<?> usuariosComEmailExistente = entityManager.createQuery("FROM Usuario WHERE email = :email")
												   		 .setParameter("email", usuario.getEmail())
												   		 .getResultList();

		if (!usuariosComEmailExistente.isEmpty())
			throw new EmailJaCadastradoException(usuario.getEmail());

		usuario.setCreated(new Date());

		usuario.setModified(usuario.getCreated());
		usuario.setLastLogin(usuario.getCreated());

		entityManager.persist(usuario);
		
		for (Telefone telefone : usuario.getPhones()) {
			telefone.setUsuario(usuario);
			
			entityManager.persist(telefone);
		}
		
		entityManager.flush();
		entityManager.refresh(usuario);
		
		return usuario;
	}
}
