package com.springrest.test.helloworld;

import io.undertow.Undertow.Builder;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.mangofactory.swagger.plugin.EnableSwagger;
import com.springrest.test.helloworld.interceptor.SecurityInterceptor;

@Configuration
@EnableAutoConfiguration(exclude = {ErrorMvcAutoConfiguration.class})
@EnableTransactionManagement
@EnableWebMvc
@EnableSwagger
@ComponentScan
public class Application extends WebMvcAutoConfigurationAdapter {
	
	@Autowired
	SecurityInterceptor securityInterceptor;
	
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(securityInterceptor);
		super.addInterceptors(registry);
		
	}

    public static void main(String[] args) {
       SpringApplication.run(Application.class, args);
    }
    
    @Bean
    public UndertowEmbeddedServletContainerFactory embeddedServletContainerFactory() {
        UndertowEmbeddedServletContainerFactory factory = new UndertowEmbeddedServletContainerFactory();
        
        factory.setContextPath("/rest");
        
        factory.addBuilderCustomizers(new UndertowBuilderCustomizer() {

            @Override
            public void customize(Builder builder) {
            	builder.setIoThreads(2);
            	builder.setWorkerThreads(10);
                builder.addHttpListener(8081, "0.0.0.0");
            }

        });
        return factory;
    }

}