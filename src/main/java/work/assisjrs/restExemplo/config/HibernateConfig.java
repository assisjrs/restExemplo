package work.assisjrs.restExemplo.config;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan({ "work.assisjrs.restExemplo.model" })
@EnableTransactionManagement
public class HibernateConfig {
	@Autowired
	@Bean
	public DataSource getDataSource() {
		DriverManagerDataSource ds = new DriverManagerDataSource();

		ds.setDriverClassName("org.hsqldb.jdbc.JDBCDriver");
		// ds.setUrl("jdbc:hsqldb:mem:RestExemplo;shutdown=true");
		ds.setUrl("jdbc:hsqldb:file:db/RestExemplo;shutdown=true");

		ds.setUsername("sa");
		ds.setPassword("");
		
		return ds;
	}
	
	@Bean
	public HibernateJpaVendorAdapter getHibernateJpaVendorAdapter(){
		return new HibernateJpaVendorAdapter();
	}
	
	@Bean
	public LocalContainerEntityManagerFactoryBean getEntityManagerFactory(HibernateJpaVendorAdapter hibernateJpaVendorAdapter, DataSource dataSource){
		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		
		factory.setDataSource(dataSource);
		factory.setJpaVendorAdapter(hibernateJpaVendorAdapter);
				
		return factory;
	}
		
	@Bean
	@Autowired
	public JpaTransactionManager getTransactionManager(EntityManagerFactory entityManagerFactory) {
		return new JpaTransactionManager(entityManagerFactory);
	}
}
