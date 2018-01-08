/**
 * Copyright (C) 2015-2017 Diego Feitosa [dnfeitosa@gmail.com]
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package co.degraph.server.main;

import co.degraph.core.models.Artifacts;
import org.neo4j.ogm.config.AutoIndexMode;
import org.neo4j.ogm.config.Configuration;
import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.session.SessionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

@Component
public class RequestScopedBeanProvider {

    @Bean
    @Lazy
    @Scope(WebApplicationContext.SCOPE_REQUEST)
    public Artifacts getArtifacts() {
        return new Artifacts();
    }

    @Bean
    @Lazy
    public SessionFactory getSessionFactory(@Value("${neo4j.database.uri}") String databaseURI) {
        Configuration configuration = new Configuration.Builder()
            .autoIndex(AutoIndexMode.ASSERT.getName())
            .uri(databaseURI)
            .build();

        return new SessionFactory(configuration, "co.degraph.db.models");
    }

    @Bean
    @Lazy
    @Scope(WebApplicationContext.SCOPE_REQUEST)
    public Session getSession(SessionFactory sessionFactory) {
        return sessionFactory.openSession();
    }
}
