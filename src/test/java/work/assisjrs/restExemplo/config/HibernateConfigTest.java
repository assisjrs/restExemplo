package work.assisjrs.restExemplo.config;

import javax.persistence.EntityManager;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { HibernateConfig.class })
public class HibernateConfigTest {
	//@PersistenceContext
	private EntityManager entityManager;

	@Test
	public void DeveIniciarAConfiguracaoDoHibernate() {
		//Assert.assertNotNull(entityManager);
	}
}
