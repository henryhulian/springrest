package com.springrest.restserver;

import io.undertow.Undertow.Builder;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.graphdb.factory.HighlyAvailableGraphDatabaseFactory;
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
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.xnio.Options;

import com.mangofactory.swagger.plugin.EnableSwagger;
import com.springrest.restserver.interceptor.SecurityInterceptor;

@Configuration
@EnableAutoConfiguration(exclude = {ErrorMvcAutoConfiguration.class})
@EnableTransactionManagement
@EnableWebMvc
@EnableSwagger
@ComponentScan
public class Application extends WebMvcAutoConfigurationAdapter {
	
	@Value("${context.path}")
	private String contextPath="/rest";
	
	@Value("${io.core}")
	private Integer ioThread=2;
	
	@Value("${io.worker.max}")
	private Integer ioMaxWorker=10;
	
	@Value("${neo4j.ha}")
	private Boolean useHA=false;
	
	@Value("${neo4j.db.path}")
	private String dbPath="db";
	
	@Value("${neo4j.config.path}")
	private String configPath="config/neo4j.properties";
	
	@Value("${session.key}")
	private String sessionKey="wXf;7-*!i)&d7TCM";
	
	@Autowired
	SecurityInterceptor securityInterceptor;
	
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(securityInterceptor);
		super.addInterceptors(registry);
		
	}

	@Bean
	public ConfigurationBean configurationBean(){
		ConfigurationBean configurationBean = new ConfigurationBean();
		configurationBean.setSessionKey(sessionKey);
		return configurationBean;
	}
    
    @Bean
    public UndertowEmbeddedServletContainerFactory embeddedServletContainerFactory() {
        UndertowEmbeddedServletContainerFactory factory = new UndertowEmbeddedServletContainerFactory();
        
        factory.setContextPath(contextPath);

        factory.addBuilderCustomizers(new UndertowBuilderCustomizer(){

			@Override
			public void customize(Builder build) {
				build.setWorkerOption(Options.WORKER_IO_THREADS, ioThread);
			    build.setWorkerOption(Options.WORKER_TASK_CORE_THREADS, ioMaxWorker/2);
			    build.setWorkerOption(Options.WORKER_TASK_MAX_THREADS, ioMaxWorker);
			}
        	
        });        
        return factory;
    }
    
    @Bean(destroyMethod = "shutdown")
    public GraphDatabaseService graphDatabaseService() {
    	GraphDatabaseService graphDb=null;
    	if( useHA==true){
    		graphDb= new HighlyAvailableGraphDatabaseFactory().newHighlyAvailableDatabaseBuilder(dbPath).loadPropertiesFromFile(configPath)
            		.newGraphDatabase();
    	}else{
    		graphDb= new GraphDatabaseFactory().newEmbeddedDatabaseBuilder(dbPath).loadPropertiesFromFile(configPath)
            		.newGraphDatabase();
        
    	}
        return graphDb;
    }
   
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
     }

}