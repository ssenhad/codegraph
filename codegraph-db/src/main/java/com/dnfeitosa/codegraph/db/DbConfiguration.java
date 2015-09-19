package com.dnfeitosa.codegraph.db;

import org.neo4j.ogm.session.SessionFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.neo4j.config.Neo4jConfiguration;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.data.neo4j.server.Neo4jServer;
import org.springframework.data.neo4j.server.RemoteServer;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.Resource;

@Configuration
@EnableNeo4jRepositories(basePackages = { "com.dnfeitosa.codegraph.db" })
@EnableTransactionManagement
public class DbConfiguration extends Neo4jConfiguration {

    @Resource
    private Environment environment;

    @Override
    public Neo4jServer neo4jServer() {
        if (environment.acceptsProfiles("test")) {
            return new InProcessServer
        }
        return new RemoteServer("http://localhost:7474");
    }

    @Override
    public SessionFactory getSessionFactory() {
        return new SessionFactory("com.dnfeitosa.codegraph.db.graph.nodes");
    }
}
