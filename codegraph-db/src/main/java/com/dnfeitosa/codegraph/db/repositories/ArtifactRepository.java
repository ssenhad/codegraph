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

import com.dnfeitosa.codegraph.db.models.ArtifactNode;
import org.neo4j.ogm.cypher.Filter;
import org.neo4j.ogm.model.Result;
import org.neo4j.ogm.session.Neo4jSession;
import org.neo4j.ogm.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static com.dnfeitosa.codegraph.db.Node.id;
import static java.util.stream.Collectors.toSet;
import static java.util.stream.StreamSupport.stream;
import static org.neo4j.ogm.cypher.ComparisonOperator.EQUALS;

@Repository
public class ArtifactRepository  {

    private final Neo4jSession session;

    @Autowired
    public ArtifactRepository(Session session) {
        this.session = (Neo4jSession) session;
    }

    public void save(ArtifactNode artifact) {
        session.save(artifact, 0);
        session.save(artifact.getDependencies(), 0);
        session.save(artifact);
    }

    public ArtifactNode load(String organization, String name, String version) {
        return session.load(ArtifactNode.class, id(organization, name, version));
    }

    public Set<String> getVersions(String organization, String name) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("organization", organization);
        parameters.put("name", name);
        Result result = session.query("MATCH (a:Artifact { organization: {organization},  name: {name}}) return a.version as version", parameters);
        return stream(result.spliterator(), false)
            .map(r -> r.get("version").toString())
            .collect(toSet());
    }

    public Set<ArtifactNode> getArtifactsFromOrganization(String organization) {
        Filter filter = new Filter("organization", EQUALS, organization);
        return new HashSet<>(session.loadAll(ArtifactNode.class, filter));
    }

    public void save(Set<ArtifactNode> artifacts) {
        session.save(artifacts, 0);
        session.save(collectDependencies(artifacts), 0);
        session.save(artifacts);
    }

    private Set<ArtifactNode> collectDependencies(Set<ArtifactNode> artifacts) {
        return artifacts.stream()
            .flatMap(a -> a.getDependencies().stream())
            .collect(toSet());
    }
}
