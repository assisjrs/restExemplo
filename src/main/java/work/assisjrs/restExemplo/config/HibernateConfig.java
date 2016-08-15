package work.assisjrs.restExemplo.config;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan({ "work.assisjrs.restExemplo.model" })
@EnableTransactionManagement
public class HibernateConfig {
	@Bean
	public EntityManagerFactory getEntityManagerFactory(){
		EntityManagerFactory factory;

		try {
			factory = Persistence.createEntityManagerFactory("restExemploPU");
		} catch (Exception e) {
			//quando for teste
			factory = Persistence.createEntityManagerFactory("restExemploPUTest");
		}
		
		return factory;
	}
	
	@Bean
	@Autowired
	public JpaTransactionManager getTransactionManager(EntityManagerFactory entityManagerFactory) {
		return new JpaTransactionManager(entityManagerFactory);
	}
}
