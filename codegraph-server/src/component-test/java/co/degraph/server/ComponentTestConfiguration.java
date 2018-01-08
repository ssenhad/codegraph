package co.degraph.server;

import co.degraph.core.models.Artifacts;
import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.session.SessionFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
@ComponentScan("co.degraph.*")
public class ComponentTestConfiguration {

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public Artifacts getArtifacts() {
        return new Artifacts();
    }

    @Bean
    public Session getSession() {
        org.neo4j.ogm.config.Configuration configuration = new org.neo4j.ogm.config.Configuration.Builder()
            .build();

        SessionFactory sessionFactory = new SessionFactory(configuration, "co.degraph.db.models");
        return sessionFactory.openSession();
    }
}
