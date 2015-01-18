package com.springrest.restserver;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.graphdb.factory.HighlyAvailableGraphDatabaseFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.web.ErrorMvcAutoConfiguration;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration.WebMvcAutoConfigurationAdapter;
import org.springframework.boot.context.embedded.undertow.UndertowEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
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
	
	@Autowired
	SecurityInterceptor securityInterceptor;
	
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(securityInterceptor);
		super.addInterceptors(registry);
		
	}

    
    @Bean
    public UndertowEmbeddedServletContainerFactory embeddedServletContainerFactory() {
        UndertowEmbeddedServletContainerFactory factory = new UndertowEmbeddedServletContainerFactory();
        
        factory.setContextPath(contextPath);
        factory.setIoThreads(ioThread);
        factory.setWorkerThreads(ioMaxWorker);
        
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