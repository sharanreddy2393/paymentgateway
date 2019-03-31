package com.paymentgateway.config;

import java.util.Map;
import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@EnableJpaRepositories(basePackages="com.paymentgateway")
@EnableTransactionManagement
public class DBConfig {

//	@Value("${spring.datasource.driver-class.name}")
//	private String DB_DRIVER;
//	
//	@Value("${spring.datasource.url}")
//	private String DB_URL;
//	
//	@Value("${spring.datasource.username}")
//	private String DB_USERNAME;
//	
//	@Value("${spring.datasource.password}")
//	private String DB_PASSWORD;
	
	
	@Value("${hibernate.dialect}")
	private String HIBERNATE_DIALECT;
	
	@Value("${hibernate.show-sql}")
	private String HIBERNATE_SHOW_SQL;
	
	@Value("${entitymanager.packagesToScan}")
	private String ENTITYMANAGER_PACKAGE_TO_SCAN;
	
	@Value("${hibernate.hbm2ddl.auto}")
	private String HIBERNATE_HBM2DDL_AUTO;
	
	@Bean
	public DataSource DataSource(Environment env) {
		DriverManagerDataSource dataSourceConfig = new DriverManagerDataSource();
		//dataSourceConfig.setDriverClassName("com.mysql.jdbc.Driver");
		dataSourceConfig.setUrl(env.getRequiredProperty("spring.datasource.url"));
		dataSourceConfig.setUsername(env.getRequiredProperty("spring.datasource.username"));
		dataSourceConfig.setPassword(env.getRequiredProperty("spring.datasource.password"));
		return dataSourceConfig;
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean EntityManagerFactoryBean(DataSource dataSource,Environment env) {
		LocalContainerEntityManagerFactoryBean entityManagerBean = new LocalContainerEntityManagerFactoryBean();
		entityManagerBean.setDataSource(dataSource);
		entityManagerBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		entityManagerBean.setPackagesToScan(ENTITYMANAGER_PACKAGE_TO_SCAN);
		
		//Properties jpaProperties = new Properties();
		
		Properties jpaProperties = new Properties();
		jpaProperties.put("hibernate.dialect", env.getRequiredProperty("hibernate.dialect"));
		jpaProperties.put("hibernate.hbm2ddl.auto", env.getRequiredProperty("hibernate.hbm2ddl.auto"));
		jpaProperties.put("hibernate.show-sql", env.getRequiredProperty("hibernate.show-sql"));

//		jpaProperties.put("org.hibernate.dialect.MySQL5Dialect", "org.hibernate.dialect.MySQL5Dialect");
//		jpaProperties.put("hibernate.hbm2ddl.auto", HIBERNATE_HBM2DDL_AUTO);
//		jpaProperties.put("hibernate.show-sql", HIBERNATE_SHOW_SQL);
		
		entityManagerBean.setJpaProperties(jpaProperties);
		return entityManagerBean;
	}
	
	
	@Bean
	public JpaTransactionManager JpaTransactionManager(EntityManagerFactory entityManagerFactory) {
		JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
		jpaTransactionManager.setEntityManagerFactory(entityManagerFactory);
		return jpaTransactionManager;
	}
}
