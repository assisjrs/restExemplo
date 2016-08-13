package work.assisjrs.restExemplo.model;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;

import java.time.Instant;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;
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

		Assert.assertThat(usuario.getId(), not(0));
	}

	@Test
	public void aoCriarOUsuarioInserirCreated() throws RestExemploException {
		Usuario usuario = new Usuario();

		usuario.setEmail("email@gmail.com");

		usuarios.salvar(usuario);

		Date created = usuario.getCreated();

		Assert.assertThat(created.before(Date.from(Instant.now())), is(true));
	}

	@Test
	public void aoCriarOUsuarioInserirModified() throws RestExemploException {
		Usuario usuario = new Usuario();

		usuario.setEmail("email@gmail.com");

		usuarios.salvar(usuario);

		Date modified = usuario.getModified();

		Assert.assertThat(modified.before(Date.from(Instant.now())), is(true));
	}

	@Test
	public void aoCriarOUsuarioInserirLastLoginIgualACreated() throws RestExemploException {
		Usuario usuario = new Usuario();

		usuario.setEmail("email@gmail.com");

		usuarios.salvar(usuario);

		Assert.assertThat(usuario.getLastLogin(), is(usuario.getCreated()));
	}

	@Autowired
	private HibernateTemplate hibernateTemplate;
	
	@Test
	public void aoCriarOUsuarioInserirTelefone() throws RestExemploException {
		Usuario usuario = new Usuario();

		usuario.setEmail("email@gmail.com");
		
		Telefone telefone = new Telefone();
		telefone.setDdd("85");
		telefone.setNumber("9999999");
		
		usuario.getPhones().add(telefone);

		usuarios.salvar(usuario);

		List<?> telefonesEncontrados = hibernateTemplate.find("FROM Telefone WHERE usuario = ?", usuario);
		
		Assert.assertThat(telefonesEncontrados.size(), is(1));
	}
	
	@Test
	public void aoCriarOUsuarioPodeInserirMaisDeUmTelefone() throws RestExemploException {
		Usuario usuario = new Usuario();

		usuario.setEmail("email@gmail.com");
		
		Telefone telefone1 = new Telefone();
		telefone1.setDdd("85");
		telefone1.setNumber("9999999");
		
		usuario.getPhones().add(telefone1);

		Telefone telefone2 = new Telefone();
		telefone2.setDdd("85");
		telefone2.setNumber("9999999");
		
		usuario.getPhones().add(telefone2);
		
		usuarios.salvar(usuario);

		List<?> telefonesEncontrados = hibernateTemplate.find("FROM Telefone WHERE usuario = ?", usuario);
		
		Assert.assertThat(telefonesEncontrados.size(), is(2));
	}

	@Test(expected = EmailJaCadastradoException.class)
	public void aoCriarOUsuarioCasoEmailJaExistaRetornarExcecao() throws RestExemploException {
		Usuario usuario = new Usuario();

		usuario.setEmail("emailJaCadastrado@gmail.com");

		usuarios.salvar(usuario);
	}
}
