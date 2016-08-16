package work.assisjrs.restExemplo.model.service;

import org.hamcrest.core.Is;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import work.assisjrs.restExemplo.DBUnitConfig;
import work.assisjrs.restExemplo.model.Hash;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { DBUnitConfig.class })
public class HashTest {
	@Autowired
	private Hash hash;
	
	@Test
	public void deveGerarHash() {
		Assert.assertThat("89e98fa7ac946099dd2504e6c9487993", Is.is(hash.encode("stringViaHash")));
	}
}
