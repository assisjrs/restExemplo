package work.assisjrs.gradleExample.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;

@Configuration
@ComponentScan({ "work.assisjrs.gradleExample.model" })
public class HibernateConfig {
	@Bean()
	public DataSource getDataSource() {
		DriverManagerDataSource ds = new DriverManagerDataSource();

		ds.setDriverClassName("org.hsqldb.jdbc.JDBCDriver");
		ds.setUrl("jdbc:hsqldb:file:db/GradleExample;shutdown=true");
		ds.setUsername("sa");
		ds.setPassword("");

		return ds;
	}

	@Bean
	@Autowired
	public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
		return new HibernateTransactionManager(sessionFactory);
	}

	@Bean
	@Autowired
	public HibernateTemplate getHibernateTemplate(SessionFactory sessionFactory) {
		return new HibernateTemplate(sessionFactory);
	}

	@Bean
	public LocalSessionFactoryBean getSessionFactory() {
		LocalSessionFactoryBean asfb = new LocalSessionFactoryBean();

		asfb.setDataSource(getDataSource());
		asfb.setHibernateProperties(getHibernateProperties());
		asfb.setPackagesToScan(new String[] { "work.assisjrs.gradleExample.model" });

		return asfb;
	}

	@Bean
	public Properties getHibernateProperties() {
		Properties properties = new Properties();
		properties.put("hibernate.dialect", "org.hibernate.dialect.HSQLDialect");
		properties.put("hibernate.show_sql", true);
		properties.put("hibernate.hbm2ddl.auto", "update");
		properties.put("org.hibernate.flushMode", "COMMIT");

		return properties;
	}
}
