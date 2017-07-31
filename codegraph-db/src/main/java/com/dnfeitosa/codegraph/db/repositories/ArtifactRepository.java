package com.dnfeitosa.codegraph.db.repositories;

import com.dnfeitosa.codegraph.db.models.ArtifactNode;
import org.neo4j.ogm.model.Result;
import org.neo4j.ogm.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Set;

import static com.dnfeitosa.codegraph.db.Node.id;
import static java.util.stream.Collectors.toSet;
import static java.util.stream.StreamSupport.stream;

@Repository
public class ArtifactRepository  {

    private final Session session;

    @Autowired
    public ArtifactRepository(Session session) {
        this.session = session;
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
        HashMap<String, Object> parameters = new HashMap<>();
        parameters.put("organization", organization);
        parameters.put("name", name);
        Result result = session.query("MATCH (a:Artifact { organization: {organization},  name: {name}}) return a.version as version", parameters);
        return stream(result.spliterator(), false)
            .map(r -> r.get("version").toString())
            .collect(toSet());
    }
}
