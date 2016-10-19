package com.dnfeitosa.codegraph.db;

import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.session.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.config.Neo4jConfiguration;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;

@Configuration
@ComponentScan("com.dnfeitosa.codegraph")
@EnableNeo4jRepositories(basePackages = "com.dnfeitosa.codegraph.db")
public class Config extends Neo4jConfiguration {

    @Bean
    public SessionFactory getSessionFactory() {
        return new SessionFactory(getConfiguration(), "com.dnfeitosa.codegraph.db");
    }

    @Bean
    public Session getSession() throws Exception {
        return super.getSession();
    }

    private org.neo4j.ogm.config.Configuration getConfiguration() {
        org.neo4j.ogm.config.Configuration config = new org.neo4j.ogm.config.Configuration();
        config.driverConfiguration()
                .setDriverClassName("org.neo4j.ogm.drivers.http.driver.HttpDriver")
                .setURI("http://localhost:7476");
        return config;
    }
}
