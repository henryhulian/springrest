package com.springrest.restserver;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.config.EnableNeo4jRepositories;
import org.springframework.data.neo4j.config.Neo4jConfiguration;


@Configuration
@EnableNeo4jRepositories(basePackages = "com.springrest.restserver.repository")
public class Neo4jConfig extends Neo4jConfiguration {
	
	Neo4jConfig() {
        setBasePackage("com.springrest.restserver.domain");
    }
	
}