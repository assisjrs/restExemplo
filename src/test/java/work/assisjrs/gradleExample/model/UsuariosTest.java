package work.assisjrs.gradleExample.model;

import org.hamcrest.core.IsNot;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import work.assisjrs.gradleExample.config.HibernateConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { HibernateConfig.class })
@Transactional
public class UsuariosTest {
	@Autowired
	private Usuarios usuarios;
	
	@Test
	public void deveSalvarOUsuario() {
		Usuario usuario = new Usuario();

		usuario.setEmail("assisjrs@gmail.com");

		usuarios.salvar(usuario);

		Assert.assertThat(usuario.getId(), IsNot.not(0));
	}
}
