package com.dnfeitosa.codegraph.db.repositories;

import com.dnfeitosa.codegraph.db.nodes.ArtifactNode;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArtifactRepository extends GraphRepository<ArtifactNode> {
}
