package work.assisjrs.restExemplo.model.service;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
import work.assisjrs.restExemplo.model.entity.Usuario;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { DBUnitConfig.class })
@Transactional
@TestExecutionListeners(value = { DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
		DbUnitTestExecutionListener.class }, mergeMode = MergeMode.MERGE_WITH_DEFAULTS)
public class TokenTest {
	private static final String TOKEN = "eyJhbGciOiJIUzUxMiJ9.eyJqdGkiOiI2NjYiLCJzdWIiOiJURVNURSIsImVtYWlsIjoiZW1haWxKYUNhZGFzdHJhZG9AZ21haWwuY29tIiwiaWF0Ijo2MTQyOTM3NDAwMH0.sp7dNesWHcmNqMdeSja7w7UK9z1m129WPu7rkIm0VMkqAzE_zSPrV-db1fOU7IvNphrBBXV5NuVRCQkK9KjrZQ";
	
	@Autowired
	private Token token;
	
	@Test
	public void deveGerarTokenPelosDadosDoUsuarioId() throws UnsupportedEncodingException {
		Usuario usuario = new Usuario();

		usuario.setId(666L);
		usuario.setEmail("emailJaCadastrado@gmail.com");

		DefaultClaims body = (DefaultClaims) Jwts.parser()
												 .setSigningKey(Token.SECRET_KEY.getBytes("UTF-8"))
												 .parse(token.tokenizer(usuario, "TESTE"))
												 .getBody();
		
		Assert.assertThat(body.getId(), is("666"));
	}
	
	@Test
	public void deveGerarTokenPelosDadosDoUsuarioEmailComHash() throws UnsupportedEncodingException {
		Usuario usuario = new Usuario();

		usuario.setId(666L);
		usuario.setEmail("emailJaCadastrado@gmail.com");

		DefaultClaims body = (DefaultClaims) Jwts.parser()
												 .setSigningKey(Token.SECRET_KEY.getBytes("UTF-8"))
												 .parse(token.tokenizer(usuario, "TESTE"))
												 .getBody();
		
		Assert.assertThat(body.get("email", String.class), is("445cba00f1a7c1824f7c5629d1e54539"));
	}
	
	@Test
	public void deveGerarTokenUsandoADataQueEstaLogando() throws UnsupportedEncodingException {
		Usuario usuario = new Usuario();

		usuario.setId(666L);
		usuario.setEmail("emailJaCadastrado@gmail.com");

		DefaultClaims body = (DefaultClaims) Jwts.parser()
												 .setSigningKey(Token.SECRET_KEY.getBytes("UTF-8"))
												 .parse(token.tokenizer(usuario, "TESTE"))
												 .getBody();
		SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd");
		
		String dataToken = sdf.format(body.getIssuedAt());
		
		Assert.assertThat(dataToken, is(sdf.format(new Date())));
	}
	
	@DatabaseSetup("/Datasets/PerfilControllerTest.xml")
	@Test
	public void deveBuscarOUsuarioPeloIdECompararSeOTokenNoModeloEIgualAoTokenPassado() throws Exception{
		Usuario usuarioLogado = token.getSigned(1L, TOKEN);
		
		Assert.assertThat(usuarioLogado, notNullValue());
	}
	
	@DatabaseSetup("/Datasets/PerfilControllerTest.xml")
	@Test(expected = TokenInvalidoException.class)
	public void deveBuscarOUsuarioPeloIdECompararSeOTokenNoModeloEIgualAoTokenPassadoCasoNaoSejaLancarExcecao() throws Exception{
		token.getSigned(1L, "NaoEOToken");
	}
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@DatabaseSetup("/Datasets/PerfilControllerTest.xml")
	@Test(expected = LoginMenorQue30MinutosException.class)
	public void CasoOUltimoLoginFoiMenosQue30minRetornarExcecao() throws Exception {
		Usuario usuarioAntesDe30Minutos = entityManager.find(Usuario.class, 2L);
		
		usuarioAntesDe30Minutos.setLastLogin(new Date());
		
		token.getSigned(2L, "zzzzz");
	}
}
