/**
 * Copyright (C) 2015-2018 Diego Feitosa [dnfeitosa@gmail.com]
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
package co.degraph.db.repositories;

import co.degraph.db.models.ArtifactNode;
import co.degraph.db.models.relationships.DeclaresRelationship;
import org.neo4j.ogm.cypher.Filter;
import org.neo4j.ogm.model.Result;
import org.neo4j.ogm.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.inject.Provider;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static co.degraph.db.Node.id;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;
import static java.util.stream.StreamSupport.stream;
import static org.neo4j.ogm.cypher.ComparisonOperator.EQUALS;

@Repository
public class ArtifactRepository  {

    private final Provider<Session> session;

    @Autowired
    public ArtifactRepository(Provider<Session> session) {
        this.session = session;
    }

    public void save(ArtifactNode artifact) {
        getSession().save(artifact, 0);
        getSession().save(artifact.getDependencies(), 0);
        getSession().save(artifact);
    }

    private Session getSession() {
        return session.get();
    }

    public ArtifactNode load(String organization, String name, String version) {
        return getSession().load(ArtifactNode.class, id(organization, name, version));
    }

    public Set<String> getVersions(String organization, String name) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("organization", organization);
        parameters.put("name", name);
        Result result = getSession().query(
            " MATCH (a:Artifact { organization: {organization},  name: {name}}) " +
            " RETURN a.version as version", parameters);
        return stream(result.spliterator(), false)
            .map(r -> r.get("version").toString())
            .collect(toSet());
    }

    public Set<ArtifactNode> getArtifactsFromOrganization(String organization) {
        Filter filter = new Filter("organization", EQUALS, organization);
        return new HashSet<>(getSession().loadAll(ArtifactNode.class, filter));
    }

    private Set<ArtifactNode> collectDependencies(Set<ArtifactNode> artifacts) {
        return artifacts.stream()
            .flatMap(a -> a.getDependencies().stream())
            .collect(toSet());
    }

    public Set<DeclaresRelationship> loadDependencyGraph(String organization, String name, String version) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("id", id(organization, name, version));

        Result result = getSession().query(
            " MATCH p=(a:Artifact)-[r:DEPENDS_ON*]->(d:Artifact) " +
            " WHERE a.id = {id} with a, r, d " +
            " RETURN r, d ", parameters);

        return stream(result.queryResults().spliterator(), false)
            .flatMap(r -> {
                return ((Collection<DeclaresRelationship>) r.get("r")).stream();
            })
            .collect(toSet());
    }

    public void saveRelationshipsWithoutProperties(Set<ArtifactNode> artifacts) {
        List<Map<Object, Object>> rows = artifacts.stream().flatMap(n -> n.getDeclaredDependencies().stream()).map(rel -> {
            Map<Object, Object> params = new HashMap<>();
            params.put("startNodeId", rel.getArtifact().getId());
            params.put("endNodeId", rel.getDependency().getId());
            params.put("id", rel.getId());
            return params;
        }).collect(toList());

        getSession().save(artifacts, 0);

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("rows", rows);
        getSession().query(
            " UNWIND {rows} as row " +
            " MATCH (startNode:Artifact) WHERE startNode.id = row.startNodeId " +
            " MATCH (endNode:Artifact) WHERE endNode.id = row.endNodeId " +
            " MERGE (startNode)-[rel:`DEPENDS_ON` {`id`: row.`id`} ]->(endNode) " +
            " RETURN row.relRef as ref, ID(rel) as id, row.type as type ", parameters);
    }
}
