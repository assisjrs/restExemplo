package work.assisjrs.restExemplo;

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
	/*
	@Autowired
	@Bean
	public DataSource getDataSource() {
		DriverManagerDataSource ds = new DriverManagerDataSource();

		ds.setDriverClassName("org.hsqldb.jdbc.JDBCDriver");
		// ds.setUrl("jdbc:hsqldb:mem:RestExemplo;shutdown=true");
		ds.setUrl("jdbc:hsqldb:file:db/RestExemploTest;shutdown=true");

		ds.setUsername("sa");
		ds.setPassword("");
		
		return ds;
	}
	*/
	
	/*
	@Bean
	public HibernateJpaVendorAdapter getHibernateJpaVendorAdapter(){
		return new HibernateJpaVendorAdapter();
	}
	*/
	
	@Bean
	public EntityManagerFactory getEntityManagerFactory(){
		EntityManagerFactory factory;

		try {
			factory = Persistence.createEntityManagerFactory("restExemploPU");
		} catch (Exception e) {
			factory = Persistence.createEntityManagerFactory("default");
		}
		
		return factory;
	}
	
	/*
	@Bean
	public LocalContainerEntityManagerFactoryBean getEntityManagerFactory(HibernateJpaVendorAdapter hibernateJpaVendorAdapter, DataSource dataSource){
		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		
		factory.setPersistenceProvider(new HibernatePersistenceProvider());
		factory.setEntityManagerFactoryInterface(EntityManagerFactory.class);
		
		factory.setDataSource(dataSource);
		factory.setJpaVendorAdapter(hibernateJpaVendorAdapter);
				
		return factory;
	}
	*/
	
	@Bean
	@Autowired
	public JpaTransactionManager getTransactionManager(EntityManagerFactory entityManagerFactory) {
		return new JpaTransactionManager(entityManagerFactory);
	}
}
