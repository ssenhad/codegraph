package com.dnfeitosa.codegraph.db.repositories;

import com.dnfeitosa.codegraph.db.models.DependencyNode;
import org.neo4j.ogm.model.Result;
import org.neo4j.ogm.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
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
}
