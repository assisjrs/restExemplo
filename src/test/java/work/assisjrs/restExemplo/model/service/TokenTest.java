package work.assisjrs.restExemplo.model.service;

import static org.hamcrest.core.Is.is;

import org.junit.Assert;
import org.junit.Test;

import work.assisjrs.restExemplo.model.entity.Usuario;

public class TokenTest {
	@Test
	public void deveGerarTokenPelosDadosDoUsuario() {
		Usuario usuario = new Usuario();

		usuario.setId(666L);
		usuario.setEmail("emailJaCadastrado@gmail.com");

		String token = new Token().tokenizer(usuario, "TESTE");
		
		Assert.assertThat(token, is("eyJhbGciOiJIUzUxMiJ9.eyJqdGkiOiI2NjYiLCJzdWIiOiJURVNURSIsImVtYWlsIjoiZW1haWxKYUNhZGFzdHJhZG9AZ21haWwuY29tIn0.wo-Ba9tPGMiT-Y5JtMJ_8pelJbBqelDkF4zz5Mllo1rJbYL1QQ1S1Fj1GavQm6T0sc5BcLDwdcBK4EjcCork8A"));
	}
	
	@Test
	public void deveValidarOToken() {
		boolean valido = new Token().isValid("eyJhbGciOiJIUzUxMiJ9.eyJqdGkiOiI2NjYiLCJzdWIiOiJURVNURSIsImVtYWlsIjoiZW1haWxKYUNhZGFzdHJhZG9AZ21haWwuY29tIn0.wo-Ba9tPGMiT-Y5JtMJ_8pelJbBqelDkF4zz5Mllo1rJbYL1QQ1S1Fj1GavQm6T0sc5BcLDwdcBK4EjcCork8A");
		
		Assert.assertThat(valido, is(true));
	}
}
