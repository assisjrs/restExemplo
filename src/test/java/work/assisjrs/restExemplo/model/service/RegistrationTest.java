package work.assisjrs.restExemplo.model.service;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.IsNull.notNullValue;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.TestExecutionListeners.MergeMode;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;

import work.assisjrs.restExemplo.DBUnitConfig;
import work.assisjrs.restExemplo.model.RestExemploException;
import work.assisjrs.restExemplo.model.entity.Usuario;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { DBUnitConfig.class })
@Transactional
@TestExecutionListeners(value = { DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
		DbUnitTestExecutionListener.class }, mergeMode = MergeMode.MERGE_WITH_DEFAULTS)
public class RegistrationTest {
	@Autowired
	private Registration registration;

	@Test
	public void deveSalvarOUsuario() throws RestExemploException {
		Usuario usuario = new Usuario();

		usuario.setEmail("email1@gmail.com");
		usuario.setPassword("senha");

		registration.register(usuario);

		Assert.assertThat(usuario.getId(), not(0));
	}

	@Test
	public void aoCriarOUsuarioInserirModifiedIgualACreated() throws RestExemploException {
		Usuario usuario = new Usuario();

		usuario.setEmail("email3@gmail.com");
		usuario.setPassword("senha");

		registration.register(usuario);

		Date modified = usuario.getModified();

		Assert.assertThat(modified.before(Date.from(Instant.now())), is(true));
	}

	@Test
	public void aoCriarOUsuarioInserirLastLoginIgualACreated() throws RestExemploException {
		Usuario usuario = new Usuario();

		usuario.setEmail("email4@gmail.com");
		usuario.setPassword("senha");

		registration.register(usuario);

		SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd");
		
		Assert.assertThat(sdf.format(usuario.getLastLogin()), is(sdf.format(usuario.getCreated())));
	}
	
	@Test
	public void OTokenDeveraSerPersistidoJuntoComOUsuario() throws RestExemploException {
		Usuario usuario = new Usuario();

		usuario.setEmail("token@gmail.com");
		usuario.setPassword("senha");

		registration.register(usuario);
		
		Assert.assertThat(usuario.getToken(), notNullValue());
	}

	@DatabaseSetup("/Datasets/UsuariosTest.xml")
	@Test(expected = EmailJaCadastradoException.class)
	public void aoCriarOUsuarioCasoEmailJaExistaRetornarExcecao() throws RestExemploException {
		Usuario usuario = new Usuario();

		usuario.setEmail("emailJaCadastrado@gmail.com");
		usuario.setPassword("senha");

		registration.register(usuario);
	}
}
