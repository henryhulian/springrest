package com.springrest.test.helloworld.config;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.shell.ShellSettings;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.config.EnableNeo4jRepositories;
import org.springframework.data.neo4j.config.Neo4jConfiguration;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@EnableNeo4jRepositories(basePackages = "com.springrest.test.helloworld")
@EnableTransactionManagement(mode = AdviceMode.ASPECTJ)
public class Neo4jConfig extends Neo4jConfiguration {


	Neo4jConfig() {
        setBasePackage("com.springrest.test.helloworld");
    }
	
    @Bean(destroyMethod = "shutdown")
    public GraphDatabaseService graphDatabaseService() {
        GraphDatabaseService graphDb = new GraphDatabaseFactory().newEmbeddedDatabaseBuilder("var/graphdb")
        		.setConfig(ShellSettings.remote_shell_enabled,"true").newGraphDatabase();
        return graphDb;
    }

 

}