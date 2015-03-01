package com.springrest.restserver;

import io.undertow.Undertow.Builder;

import java.io.IOException;

import org.infinispan.manager.DefaultCacheManager;
import org.infinispan.spring.provider.SpringEmbeddedCacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.web.ErrorMvcAutoConfiguration;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration.WebMvcAutoConfigurationAdapter;
import org.springframework.boot.context.embedded.undertow.UndertowBuilderCustomizer;
import org.springframework.boot.context.embedded.undertow.UndertowEmbeddedServletContainerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.xnio.Options;

import com.mangofactory.swagger.plugin.EnableSwagger;
import com.springrest.restserver.interceptor.SecurityInterceptor;

@Configuration
@EnableAutoConfiguration(exclude = { ErrorMvcAutoConfiguration.class ,DataSourceAutoConfiguration.class/*, JpaRepositoriesAutoConfiguration.class , HibernateJpaAutoConfiguration.class*/})
@EnableWebMvc
@EnableSwagger
@EnableCaching
@ComponentScan
@EnableTransactionManagement
public class Application extends WebMvcAutoConfigurationAdapter {
	
	@Autowired
	Environment env;

	@Autowired
	SecurityInterceptor securityInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(securityInterceptor);
		super.addInterceptors(registry);

	}
	
	@Bean
    public CacheManager cacheManager() {
			CacheManager cacheManager;
			try {
				DefaultCacheManager defaultCacheManager = new DefaultCacheManager("config/infinispan.xml");
				cacheManager = new SpringEmbeddedCacheManager(defaultCacheManager);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
         return  cacheManager;
    }


	@Bean
	public UndertowEmbeddedServletContainerFactory embeddedServletContainerFactory() {
		UndertowEmbeddedServletContainerFactory factory = new UndertowEmbeddedServletContainerFactory();

		factory.setContextPath(env.getProperty("context.path"));

		factory.addBuilderCustomizers(new UndertowBuilderCustomizer() {

			@Override
			public void customize(Builder build) {
				
				Integer maxWorker =  env.getProperty("io.worker.max",Integer.class,Runtime.getRuntime().availableProcessors() + 1);
				
				build.setWorkerOption(Options.WORKER_IO_THREADS, env.getProperty("io.core",Integer.class,Runtime.getRuntime().availableProcessors() + 1));
				build.setWorkerOption(Options.WORKER_TASK_CORE_THREADS,
						maxWorker / 2);
				build.setWorkerOption(Options.WORKER_TASK_MAX_THREADS,
						maxWorker);
			}

		});
		return factory;
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}