package com.springrest.restserver;

import io.undertow.Undertow.Builder;

import java.util.Properties;

import javax.sql.DataSource;

import org.h2.jdbcx.JdbcDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.web.ErrorMvcAutoConfiguration;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration.WebMvcAutoConfigurationAdapter;
import org.springframework.boot.context.embedded.undertow.UndertowBuilderCustomizer;
import org.springframework.boot.context.embedded.undertow.UndertowEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.xnio.Options;

import com.mangofactory.swagger.plugin.EnableSwagger;
import com.springrest.restserver.interceptor.SecurityInterceptor;

@Configuration
@EnableAutoConfiguration(exclude = { ErrorMvcAutoConfiguration.class })
@EnableTransactionManagement
@EnableWebMvc
@EnableSwagger
@ComponentScan
public class Application extends WebMvcAutoConfigurationAdapter {
	
	private static final String H2_JDBC_URL_TEMPLATE = "jdbc:h2:%s/db/applicaion;MULTI_THREADED=1;AUTO_SERVER=TRUE ";

	@Value("${context.path}")
	private String contextPath = "/rest";

	@Value("${io.core}")
	private Integer ioThread = 2;

	@Value("${io.worker.max}")
	private Integer ioMaxWorker = 10;

	@Value("${neo4j.ha}")
	private Boolean useHA = false;

	@Value("${neo4j.db.path}")
	private String dbPath = "db";

	@Value("${neo4j.config.path}")
	private String configPath = "config/neo4j.properties";

	@Value("${session.key}")
	private String sessionKey = "wXf;7-*!i)&d7TCM";

	@Autowired
	SecurityInterceptor securityInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(securityInterceptor);
		super.addInterceptors(registry);

	}

	@Bean
	public DataSource dataSource() {

		// EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2).build();
		String jdbcUrl = String.format(H2_JDBC_URL_TEMPLATE,
				System.getProperty("user.dir"));
		JdbcDataSource dataSource = new JdbcDataSource();
		dataSource.setURL(jdbcUrl);
		dataSource.setUser("sa");
		dataSource.setPassword("");

		return dataSource;
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
		entityManagerFactoryBean.setDataSource(dataSource());
		entityManagerFactoryBean
				.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		entityManagerFactoryBean
				.setPackagesToScan("com.springrest.restserver.domain");
		entityManagerFactoryBean.setJpaProperties(jpaProperties());
		return entityManagerFactoryBean;
	}

	private Properties jpaProperties() {
		Properties properties = new Properties();
		properties.setProperty("hibernate.hbm2ddl.auto", "update");
		properties.setProperty("hibernate.dialect",
				"org.hibernate.dialect.H2Dialect");
		//properties.setProperty("hibernate.show_sql", "true");
		//properties.setProperty("hibernate.format_sql", "true");
		return properties;
	}

	@Bean
	public JpaTransactionManager transactionManager() {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactory()
				.getObject());
		transactionManager.setDataSource(dataSource());
		return transactionManager;
	}

	@Bean
	public ConfigurationBean configurationBean() {
		ConfigurationBean configurationBean = new ConfigurationBean();
		configurationBean.setSessionKey(sessionKey);
		return configurationBean;
	}

	@Bean
	public UndertowEmbeddedServletContainerFactory embeddedServletContainerFactory() {
		UndertowEmbeddedServletContainerFactory factory = new UndertowEmbeddedServletContainerFactory();

		factory.setContextPath(contextPath);

		factory.addBuilderCustomizers(new UndertowBuilderCustomizer() {

			@Override
			public void customize(Builder build) {
				build.setWorkerOption(Options.WORKER_IO_THREADS, ioThread);
				build.setWorkerOption(Options.WORKER_TASK_CORE_THREADS,
						ioMaxWorker / 2);
				build.setWorkerOption(Options.WORKER_TASK_MAX_THREADS,
						ioMaxWorker);
			}

		});
		return factory;
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}