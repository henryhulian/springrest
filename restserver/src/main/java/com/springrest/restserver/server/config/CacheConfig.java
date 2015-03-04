package com.springrest.restserver.server.config;

import java.io.IOException;

import org.infinispan.manager.DefaultCacheManager;
import org.infinispan.spring.provider.SpringEmbeddedCacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
@EnableCaching
public class CacheConfig {
	
	@Autowired
	private Environment env;

	@Bean
    public CacheManager cacheManager() {
			
			CacheManager cacheManager;
			if(env.getProperty("cache.infinispan.open",Boolean.class,true)==true){
				try {
					DefaultCacheManager defaultCacheManager = new DefaultCacheManager("config/infinispan.xml");
					defaultCacheManager.getCache();
					cacheManager = new SpringEmbeddedCacheManager(defaultCacheManager);
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}else{
				cacheManager=new ConcurrentMapCacheManager("session","roles","checkCode");
			}
			
         return  cacheManager;
    }

}
