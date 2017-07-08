package com.dnfeitosa.codegraph.db.repositories;

import com.dnfeitosa.codegraph.db.models.ArtifactNode;
import org.neo4j.ogm.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import static com.dnfeitosa.codegraph.db.Node.id;

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
}
