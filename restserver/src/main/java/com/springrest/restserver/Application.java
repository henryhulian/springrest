package com.springrest.restserver;

import io.undertow.Undertow.Builder;

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
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.cassandra.config.CassandraClusterFactoryBean;
import org.springframework.data.cassandra.config.CassandraSessionFactoryBean;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.convert.CassandraConverter;
import org.springframework.data.cassandra.convert.MappingCassandraConverter;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.data.cassandra.mapping.BasicCassandraMappingContext;
import org.springframework.data.cassandra.mapping.CassandraMappingContext;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;
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
@PropertySource(value = { "classpath:cassandra.properties" })
@EnableCassandraRepositories(basePackages = { "com.springrest.restserver.repository" })
public class Application extends WebMvcAutoConfigurationAdapter {

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

	@Autowired
	private Environment env;

	

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
	
	@Bean
	public CassandraClusterFactoryBean cluster() {

		CassandraClusterFactoryBean cluster = new CassandraClusterFactoryBean();
		//System.out.print(env.getProperty("cassandra.contactpoints"));
		//cluster.setContactPoints(env.getProperty("cassandra.contactpoints"));
		//cluster.setPort(Integer.parseInt(env.getProperty("cassandra.port")));

		return cluster;
	}

	@Bean
	public CassandraMappingContext mappingContext() {
		return new BasicCassandraMappingContext();
	}

	@Bean
	public CassandraConverter converter() {
		return new MappingCassandraConverter(mappingContext());
	}

	@Bean
	public CassandraSessionFactoryBean session() throws Exception {

		CassandraSessionFactoryBean session = new CassandraSessionFactoryBean();
		session.setCluster(cluster().getObject());
		session.setKeyspaceName(env.getProperty("cassandra.keyspace"));
		session.setConverter(converter());
		session.setSchemaAction(SchemaAction.NONE);

		return session;
	}

	@Bean
	public CassandraOperations cassandraTemplate() throws Exception {
		return new CassandraTemplate(session().getObject());
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}