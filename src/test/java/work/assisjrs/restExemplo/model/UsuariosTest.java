package work.assisjrs.restExemplo.model;

import java.time.Instant;
import java.util.Date;

import org.hamcrest.core.Is;
import org.hamcrest.core.IsNot;
import org.hibernate.StatelessSession;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import work.assisjrs.restExemplo.config.HibernateConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { HibernateConfig.class })
@Transactional
public class UsuariosTest {
	@Autowired
	private Usuarios usuarios;

	@Test
	public void deveSalvarOUsuario() throws RestExemploException {
		Usuario usuario = new Usuario();

		usuario.setEmail("email@gmail.com");

		usuarios.salvar(usuario);

		Assert.assertThat(usuario.getId(), IsNot.not(0));
	}

	@Test
	public void aoCriarOUsuarioInserirCreated() throws RestExemploException {
		Usuario usuario = new Usuario();

		usuario.setEmail("email@gmail.com");

		usuarios.salvar(usuario);

		Date created = usuario.getCreated();

		Assert.assertThat(created.before(Date.from(Instant.now())), Is.is(true));
	}

	@Test
	public void aoCriarOUsuarioInserirModified() throws RestExemploException {
		Usuario usuario = new Usuario();

		usuario.setEmail("email@gmail.com");

		usuarios.salvar(usuario);

		Date modified = usuario.getModified();

		Assert.assertThat(modified.before(Date.from(Instant.now())), Is.is(true));
	}

	@Test
	public void aoCriarOUsuarioInserirLastLoginIgualACreated() throws RestExemploException {
		Usuario usuario = new Usuario();

		usuario.setEmail("email@gmail.com");

		usuarios.salvar(usuario);

		Assert.assertThat(usuario.getLastLogin(), Is.is(usuario.getCreated()));
	}

	@Autowired
	private StatelessSession statelessSession;

	@Test(expected = EmailJaCadastradoException.class)
	public void aoCriarOUsuarioCasoEmailJaExistaRetornarExcecao() throws RestExemploException {
		int sequence = (int) statelessSession.createSQLQuery("call next value for hibernate_sequence").uniqueResult();
		statelessSession.createSQLQuery("insert into Usuario (created, email, id) values (?, ?, ?)")
				.setParameter(0, new Date()).setParameter(1, "emailJaCadastrado@gmail.com").setParameter(2, sequence)
				.executeUpdate();

		Usuario usuario = new Usuario();

		usuario.setEmail("emailJaCadastrado@gmail.com");

		usuarios.salvar(usuario);
	}
}
