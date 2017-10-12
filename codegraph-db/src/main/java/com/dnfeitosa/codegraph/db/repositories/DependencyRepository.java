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
package com.dnfeitosa.codegraph.db.repositories;

import com.dnfeitosa.codegraph.db.models.DependencyNode;
import org.neo4j.ogm.cypher.Filter;
import org.neo4j.ogm.model.Result;
import org.neo4j.ogm.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import static java.util.stream.Collectors.toSet;
import static java.util.stream.StreamSupport.stream;

@Repository
public class DependencyRepository {

    private final Session session;

    @Autowired
    public DependencyRepository(Session session) {
        this.session = session;
    }

    public void save(Set<DependencyNode> dependencies) {
        session.save(dependencies);
    }

    public DependencyNode load(String organization, String name, String version) {
        return session.load(DependencyNode.class, String.format("%s:%s:%s", organization, name, version));
    }

    public Set<String> getVersions(String organization, String name) {
        HashMap<String, Object> parameters = new HashMap<>();
        parameters.put("organization", organization);
        parameters.put("name", name);
        Result result = session.query("MATCH (d:Dependency { organization: {organization},  name: {name}}) return d.version as version", parameters);
        return stream(result.spliterator(), false)
            .map(r -> r.get("version").toString())
            .collect(toSet());
    }

    public Set<DependencyNode> getArtifactsFromOrganization(String organization) {
        Collection<DependencyNode> dependencies = session.loadAll(DependencyNode.class, new Filter("organization", organization));
        return new HashSet<>(dependencies);
    }
}
