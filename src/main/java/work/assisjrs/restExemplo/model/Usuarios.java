package work.assisjrs.restExemplo.model;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import work.assisjrs.restExemplo.model.entity.Telefone;
import work.assisjrs.restExemplo.model.entity.Usuario;

@Transactional
@Repository
public class Usuarios {
	@PersistenceContext
	private EntityManager entityManager;

	@Transactional
	public Usuario salvar(Usuario usuario) {
		Usuario usuarioManaged = usuario;

		Date modified = new Date();
		
		if (usuario.getId() == null) {
			usuarioManaged.setCreated(modified);

			entityManager.persist(usuarioManaged);
		}

		if (!entityManager.contains(usuario))
			usuarioManaged = entityManager.merge(usuario);

		List<Telefone> telefones = usuarioManaged.getPhones();

		for (Telefone telefone : telefones) {
			telefone.setUsuario(usuarioManaged);

			entityManager.persist(telefone);
		}

		usuarioManaged.setModified(modified);
		
		entityManager.flush();

		return usuarioManaged;
	}

	public Usuario getUsuarioPorEmail(String email) {
		try {
			return (Usuario) entityManager.createQuery("FROM Usuario WHERE email = :email").setParameter("email", email)
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public Usuario byId(Long id) {
		return entityManager.find(Usuario.class, id);
	}
}
