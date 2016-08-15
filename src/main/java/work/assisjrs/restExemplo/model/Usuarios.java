package work.assisjrs.restExemplo.model;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.springframework.stereotype.Repository;

//@Transactional
@Repository
public class Usuarios {
	// @PersistenceContext
	// private EntityManager entityManager;

	// @Transactional
	public Usuario salvar(Usuario usuario) throws EmailJaCadastradoException {
		EntityManagerFactory factory;

		try {
			factory = Persistence.createEntityManagerFactory("restExemploPU");
		} catch (Exception e) {
			factory = Persistence.createEntityManagerFactory("default");
		}

		EntityManager entityManager = factory.createEntityManager();

		entityManager.getTransaction().begin();

		List<?> usuariosComEmailExistente = entityManager.createQuery("FROM Usuario WHERE email = :email")
				.setParameter("email", usuario.getEmail()).getResultList();

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

		entityManager.getTransaction().commit();
		entityManager.close();

		return usuario;
	}
}
