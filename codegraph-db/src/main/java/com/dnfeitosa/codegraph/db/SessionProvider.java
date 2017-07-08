package com.dnfeitosa.codegraph.db;

import org.neo4j.ogm.autoindex.AutoIndexMode;
import org.neo4j.ogm.config.Configuration;
import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.session.SessionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class SessionProvider {

    @Bean
    public Session getSession(@Value("${neo4j.database.driver}") String neo4jDriver,
                              @Value("${neo4j.database.uri}") String databaseURI) {
        Configuration configuration = new Configuration();
        configuration.autoIndexConfiguration()
            .setAutoIndex(AutoIndexMode.ASSERT.getName());
        configuration.driverConfiguration()
            .setDriverClassName(neo4jDriver)
            .setURI(databaseURI);

        SessionFactory sessionFactory = new SessionFactory(configuration, "com.dnfeitosa.codegraph.db.models");
        return sessionFactory.openSession();
    }

}
