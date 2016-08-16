package work.assisjrs.restExemplo.model.service;

import static org.hamcrest.core.Is.is;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.impl.DefaultClaims;
import work.assisjrs.restExemplo.model.entity.Usuario;

public class TokenTest {
	@Test
	public void deveGerarTokenPelosDadosDoUsuarioId() throws UnsupportedEncodingException {
		Usuario usuario = new Usuario();

		usuario.setId(666L);
		usuario.setEmail("emailJaCadastrado@gmail.com");

		String token = new Token().tokenizer(usuario, "TESTE");
		
		DefaultClaims body = (DefaultClaims) Jwts.parser()
												 .setSigningKey(Token.SECRET_KEY.getBytes("UTF-8"))
												 .parse(token)
												 .getBody();
		
		Assert.assertThat(body.getId(), is("666"));
	}
	
	@Test
	public void deveGerarTokenPelosDadosDoUsuarioEmail() throws UnsupportedEncodingException {
		Usuario usuario = new Usuario();

		usuario.setId(666L);
		usuario.setEmail("emailJaCadastrado@gmail.com");

		String token = new Token().tokenizer(usuario, "TESTE");
		
		DefaultClaims body = (DefaultClaims) Jwts.parser()
												 .setSigningKey(Token.SECRET_KEY.getBytes("UTF-8"))
												 .parse(token)
												 .getBody();
		
		Assert.assertThat(body.get("email", String.class), is("emailJaCadastrado@gmail.com"));
	}
	
	@Test
	public void deveGerarTokenUsandoADataQueEstaLogando() throws UnsupportedEncodingException {
		Usuario usuario = new Usuario();

		usuario.setId(666L);
		usuario.setEmail("emailJaCadastrado@gmail.com");

		String token = new Token().tokenizer(usuario, "TESTE");
		
		DefaultClaims body = (DefaultClaims) Jwts.parser()
												 .setSigningKey(Token.SECRET_KEY.getBytes("UTF-8"))
												 .parse(token)
												 .getBody();
		SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd");
		
		String dataToken = sdf.format(body.getIssuedAt());
		
		Assert.assertThat(dataToken, is(sdf.format(new Date())));
	}
	
	@Test
	public void deveValidarOToken() {
		boolean valido = new Token().isValid("eyJhbGciOiJIUzUxMiJ9.eyJqdGkiOiI2NjYiLCJzdWIiOiJURVNURSIsImVtYWlsIjoiZW1haWxKYUNhZGFzdHJhZG9AZ21haWwuY29tIn0.wo-Ba9tPGMiT-Y5JtMJ_8pelJbBqelDkF4zz5Mllo1rJbYL1QQ1S1Fj1GavQm6T0sc5BcLDwdcBK4EjcCork8A");
		
		Assert.assertThat(valido, is(true));
	}
}
