package work.assisjrs.restExemplo.model;

import org.junit.Assert;
import org.junit.Test;

import work.assisjrs.restExemplo.model.entity.Usuario;

public class UsuarioTest {
	@Test
	public void aListaPhonesSempreDeveRetornarUmaInstanciaMesmoQueSejaVazia() {
		Usuario u = new Usuario();

		u.setPhones(null);

		Assert.assertNotNull(u.getPhones());
	}
}
