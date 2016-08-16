package work.assisjrs.restExemplo.model.service;

import static org.hamcrest.core.Is.is;

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

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.impl.DefaultClaims;
import work.assisjrs.restExemplo.DBUnitConfig;
import work.assisjrs.restExemplo.model.RestExemploException;
import work.assisjrs.restExemplo.model.entity.Usuario;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { DBUnitConfig.class })
@Transactional
@TestExecutionListeners(value = { DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
		DbUnitTestExecutionListener.class }, mergeMode = MergeMode.MERGE_WITH_DEFAULTS)
public class AuthenticationTest {
	@Autowired
	private Authenticator authenticator;
	
	@PersistenceContext
	private EntityManager entityManager;

	@Test
	@DatabaseSetup("/Datasets/UsuariosTest.xml")
	public void authenticate() throws RestExemploException {
		Usuario usuario = new Usuario();

		usuario.setEmail("emailJaCadastrado@gmail.com");

		Usuario usuarioEncontrado = authenticator.authenticate("emailJaCadastrado@gmail.com", "666");
		
		Assert.assertNotNull(usuarioEncontrado);
	}
	
	@Test
	@DatabaseSetup("/Datasets/UsuariosTest.xml")
	public void aoAutenticarModificarOLastLogin() throws RestExemploException {
		Usuario usuarioEncontrado = authenticator.authenticate("emailJaCadastrado@gmail.com", "666");
		
		Assert.assertTrue(usuarioEncontrado.getLastLogin().after(usuarioEncontrado.getCreated()));
	}
	
	@Test(expected = UsuarioInexistenteException.class)
	@DatabaseSetup("/Datasets/UsuariosTest.xml")
	public void casoOEmailNaoExistaLancarExcecao() throws RestExemploException {
		Usuario usuario = new Usuario();

		usuario.setEmail("emailJaCadastrado@gmail.com");

		Usuario usuarioEncontrado = authenticator.authenticate("emailNaoExiste@gmail.com", "666");
		
		Assert.assertNotNull(usuarioEncontrado);
	}
	
	@Test(expected = UsuarioESenhaInvalidosException.class)
	@DatabaseSetup("/Datasets/UsuariosTest.xml")
	public void casoOEmailExistaMasASenhaNaoBataLancarExcecao() throws RestExemploException {
		Usuario usuario = new Usuario();

		usuario.setEmail("emailJaCadastrado@gmail.com");

		Usuario usuarioEncontrado = authenticator.authenticate("emailJaCadastrado@gmail.com", "333");
		
		Assert.assertNotNull(usuarioEncontrado);
	}
	
	@Test
	public void aCadaAutenticacaoGerarUmNovoToken() throws Exception {
		Usuario usuario = new Usuario();

		usuario.setId(999L);
		usuario.setEmail("emailJaCadastrado@gmail.com");
		usuario.setPassword("666");

		Usuario usuarioAutenticado = authenticator.authenticate(usuario.getEmail(), usuario.getPassword());
		
		DefaultClaims body = (DefaultClaims) Jwts.parser()
				 								 .setSigningKey(Token.SECRET_KEY.getBytes("UTF-8"))
				 								 .parse(usuarioAutenticado.getToken())
				 								 .getBody();
		
		Assert.assertThat(usuarioAutenticado.getLastLogin(), is(body.getIssuedAt()));
	}
}
