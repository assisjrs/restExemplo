package work.assisjrs.restExemplo.model.service;

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
import work.assisjrs.restExemplo.model.service.Authentication;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { DBUnitConfig.class })
@Transactional
@TestExecutionListeners(value = { DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
		DbUnitTestExecutionListener.class }, mergeMode = MergeMode.MERGE_WITH_DEFAULTS)
public class AuthenticationTest {
	@Autowired
	private Authentication authentication;

	@Test
	@DatabaseSetup("/Datasets/UsuariosTest.xml")
	public void authenticate() throws RestExemploException {
		Usuario usuario = new Usuario();

		usuario.setEmail("emailJaCadastrado@gmail.com");

		Usuario usuarioEncontrado = authentication.authenticate("emailJaCadastrado@gmail.com", "666");
		
		Assert.assertNotNull(usuarioEncontrado);
	}
	
	@Test(expected = UsuarioInexistenteException.class)
	@DatabaseSetup("/Datasets/UsuariosTest.xml")
	public void casoOEmailNaoExistaLancarExcecao() throws RestExemploException {
		Usuario usuario = new Usuario();

		usuario.setEmail("emailJaCadastrado@gmail.com");

		Usuario usuarioEncontrado = authentication.authenticate("emailNaoExiste@gmail.com", "666");
		
		Assert.assertNotNull(usuarioEncontrado);
	}
	
	@Test(expected = UsuarioESenhaInvalidosException.class)
	@DatabaseSetup("/Datasets/UsuariosTest.xml")
	public void casoOEmailExistaMasASenhaNaoBataLancarExcecao() throws RestExemploException {
		Usuario usuario = new Usuario();

		usuario.setEmail("emailJaCadastrado@gmail.com");

		Usuario usuarioEncontrado = authentication.authenticate("emailJaCadastrado@gmail.com", "333");
		
		Assert.assertNotNull(usuarioEncontrado);
	}
}
