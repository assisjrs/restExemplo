package work.assisjrs.gradleExample.config;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { HibernateConfig.class })
public class HibernateConfigTest {
	@Autowired
	private HibernateTemplate hibernateTemplate;

	@Test
	public void DeveIniciarAConfiguracaoDoHibernate() {
		Assert.assertNotNull(hibernateTemplate.getSessionFactory());
	}
}
