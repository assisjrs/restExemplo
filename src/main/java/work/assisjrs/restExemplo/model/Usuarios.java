package work.assisjrs.restExemplo.model;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public class Usuarios {
	@PersistenceContext
	private EntityManager entityManager;

	@Transactional
	public Usuario salvar(Usuario usuario) throws EmailJaCadastradoException {
		Usuario usuarioComEmailExistente = getUsuarioPorEmail(usuario.getEmail());

		if (usuarioComEmailExistente != null)
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

	public Usuario usuarioPorEmailESenha(String email, String password)
			throws UsuarioInexistenteException, UsuarioESenhaInvalidosException {
		Usuario usuario = getUsuarioPorEmail(email);

		if (usuario == null)
			throw new UsuarioInexistenteException(email);

		if (!usuario.getPassword().equals(password)) {
			throw new UsuarioESenhaInvalidosException(email);
		}

		return usuario;
	}

	private Usuario getUsuarioPorEmail(String email) {
		try {
			return (Usuario) entityManager.createQuery("FROM Usuario WHERE email = :email")
										  .setParameter("email", email)
										  .getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
}
