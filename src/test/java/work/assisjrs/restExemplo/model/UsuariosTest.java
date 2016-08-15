package work.assisjrs.restExemplo.model;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;

import java.time.Instant;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
import work.assisjrs.restExemplo.model.entity.Telefone;
import work.assisjrs.restExemplo.model.entity.Usuario;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { DBUnitConfig.class })
@Transactional
@TestExecutionListeners(value = { DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
		DbUnitTestExecutionListener.class }, mergeMode = MergeMode.MERGE_WITH_DEFAULTS)
public class UsuariosTest {
	@Autowired
	private Usuarios usuarios;

	@Test
	public void deveSalvarOUsuario() throws RestExemploException {
		Usuario usuario = new Usuario();

		usuario.setEmail("email1@gmail.com");

		usuarios.salvar(usuario);

		Assert.assertThat(usuario.getId(), not(0));
	}
	
	@Test
	public void aoCriarOUsuarioInserirCreated() throws RestExemploException {
		Usuario usuario = new Usuario();

		usuario.setEmail("email2@gmail.com");

		usuarios.salvar(usuario);

		Date created = usuario.getCreated();

		Assert.assertThat(created.before(Date.from(Instant.now())), is(true));
	}

	@PersistenceContext
	private EntityManager entityManager;

	@Test
	public void aoCriarOUsuarioInserirTelefone() throws RestExemploException {
		Usuario usuario = new Usuario();

		usuario.setEmail("email5@gmail.com");

		Telefone telefone = new Telefone();
		telefone.setDdd("85");
		telefone.setNumber("9999999");

		usuario.getPhones().add(telefone);

		usuarios.salvar(usuario);

		List<?> telefonesEncontrados = entityManager.createQuery("FROM Telefone WHERE usuario = :usuario")
				.setParameter("usuario", usuario).getResultList();

		Assert.assertThat(telefonesEncontrados.size(), is(1));
	}

	@Test
	public void aoCriarOUsuarioPodeInserirMaisDeUmTelefone() throws RestExemploException {
		Usuario usuario = new Usuario();

		usuario.setEmail("email6@gmail.com");

		Telefone telefone1 = new Telefone();
		telefone1.setDdd("85");
		telefone1.setNumber("9999999");

		usuario.getPhones().add(telefone1);

		Telefone telefone2 = new Telefone();
		telefone2.setDdd("85");
		telefone2.setNumber("9999999");

		usuario.getPhones().add(telefone2);

		usuarios.salvar(usuario);

		List<?> telefonesEncontrados = entityManager.createQuery("FROM Telefone WHERE usuario = :usuario")
				.setParameter("usuario", usuario).getResultList();

		Assert.assertThat(telefonesEncontrados.size(), is(2));
	}

	@Test
	@DatabaseSetup("/Datasets/UsuariosTest.xml")
	public void recupararUsuarioPorEmail() throws RestExemploException {
		Usuario usuario = new Usuario();

		usuario.setEmail("emailJaCadastrado@gmail.com");

		Usuario usuarioEncontrado = usuarios.getUsuarioPorEmail("emailJaCadastrado@gmail.com");
		
		Assert.assertNotNull(usuarioEncontrado);
	}
}
