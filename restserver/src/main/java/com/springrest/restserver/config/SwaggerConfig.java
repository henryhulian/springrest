package com.springrest.restserver.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mangofactory.swagger.configuration.SpringSwaggerConfig;
import com.mangofactory.swagger.models.dto.ApiInfo;
import com.mangofactory.swagger.plugin.SwaggerSpringMvcPlugin;

@Configuration
public class SwaggerConfig {

	 	private SpringSwaggerConfig springSwaggerConfig;
		
	 	@Autowired
		public void setSpringSwaggerConfig(SpringSwaggerConfig springSwaggerConfig) {
			this.springSwaggerConfig = springSwaggerConfig;
		}
		
		@Bean
		public SwaggerSpringMvcPlugin customImplementation() {
			return new SwaggerSpringMvcPlugin(this.springSwaggerConfig).apiInfo(
					apiInfo());
		}
		private ApiInfo apiInfo() {
			ApiInfo apiInfo = new ApiInfo("SaurzCode API", "API for Saurzcode",
					"Saurzcode API terms of service", "mail2saurzcode@gmail.com",
					"Saurzcode API Licence Type", "Saurzcode API License URL");
			return apiInfo;
		}
	    
}
