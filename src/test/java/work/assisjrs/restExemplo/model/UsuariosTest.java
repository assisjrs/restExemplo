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
	
	@PersistenceContext
	private EntityManager entityManager;

	@Test
	public void devePersistirOUsuario() throws RestExemploException {
		Usuario usuario = new Usuario();

		usuario.setEmail("email1@gmail.com");

		usuarios.salvar(usuario);

		Assert.assertThat(usuario.getId(), not(0));
	}
	
	@DatabaseSetup("/Datasets/UsuariosTest.xml")
	@Test
	public void deveAtualizarOUsuario() throws RestExemploException {
		Usuario usuario = new Usuario();

		usuario.setId(999999L);
		usuario.setName("nomeAlterado");
		usuario.setEmail("emailJaCadastrado@gmail.com");
		usuario.setPassword("666");
		usuario.setCreated(new Date("08/15/2016 00:00:00"));
		
		usuarios.salvar(usuario);

		Assert.assertThat(usuario.getName(), is("nomeAlterado"));
	}
	
	@DatabaseSetup("/Datasets/UsuariosTest.xml")
	@Test
	public void deveAtualizarOLastModified() throws RestExemploException {
		Usuario usuario = new Usuario();

		usuario.setId(999999L);
		usuario.setName("nomeAlterado");
		usuario.setEmail("emailJaCadastrado@gmail.com");
		usuario.setPassword("666");
		usuario.setCreated(new Date("08/15/2016 00:00:00"));
		
		Date modifiedInicial = new Date("08/15/2016 00:00:00");
		
		usuario.setModified(modifiedInicial);
		
		Usuario usuarioGerenciado = usuarios.salvar(usuario);

		Assert.assertThat(usuarioGerenciado.getModified().toInstant().getNano(), is(not(modifiedInicial.toInstant().getNano())));
	}
	
	@Test
	public void aoCriarOUsuarioInserirCreated() throws RestExemploException {
		Usuario usuario = new Usuario();

		usuario.setEmail("email2@gmail.com");

		usuarios.salvar(usuario);

		Date created = usuario.getCreated();

		Assert.assertThat(created.before(Date.from(Instant.now())), is(true));
	}

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
