package com.dnfeitosa.codegraph.server.acceptance;

import com.dnfeitosa.codegraph.core.models.Artifacts;
import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.session.SessionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
@ComponentScan("com.dnfeitosa.codegraph.*")
public class AcceptanceTestConfiguration {

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public Artifacts getArtifacts() {
        return new Artifacts();
    }

    @Bean
    public Session getSession(@Value("${neo4j.database.uri:}") String databaseURI) {
        org.neo4j.ogm.config.Configuration.Builder builder = new org.neo4j.ogm.config.Configuration.Builder();
        if (databaseURI != null && !databaseURI.isEmpty()) {
            builder.uri(databaseURI);
        }
        org.neo4j.ogm.config.Configuration configuration = builder
            .build();

        SessionFactory sessionFactory = new SessionFactory(configuration, "com.dnfeitosa.codegraph.db.models");
        return sessionFactory.openSession();
    }
}
