package com.dnfeitosa.codegraph.db.repositories;

import com.dnfeitosa.codegraph.db.models.DependencyNode;
import org.neo4j.ogm.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Set;

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
}
