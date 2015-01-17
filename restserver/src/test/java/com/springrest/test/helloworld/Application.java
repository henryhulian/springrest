package com.springrest.test.helloworld;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.mangofactory.swagger.plugin.EnableSwagger;

@Configuration
@EnableAutoConfiguration
@EnableTransactionManagement
@EnableWebMvc
@EnableSwagger
@ComponentScan
public class Application extends WebMvcAutoConfiguration {

    public static void main(String[] args) {
       SpringApplication.run(Application.class, args);
    }

}