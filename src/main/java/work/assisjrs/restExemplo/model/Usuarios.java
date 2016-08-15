package work.assisjrs.restExemplo.model;

import java.util.Date;

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
		usuario.setCreated(new Date());
		
		entityManager.persist(usuario);

		for (Telefone telefone : usuario.getPhones()) {
			telefone.setUsuario(usuario);

			entityManager.persist(telefone);
		}

		entityManager.flush();
		entityManager.refresh(usuario);

		return usuario;
	}

	public Usuario getUsuarioPorEmail(String email) {
		try {
			return (Usuario) entityManager.createQuery("FROM Usuario WHERE email = :email")
										  .setParameter("email", email)
										  .getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
}
