package com.dnfeitosa.codegraph.db.repositories;

import com.dnfeitosa.codegraph.db.nodes.ArtifactNode;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArtifactRepository extends GraphRepository<ArtifactNode> {

    @Query("match (a:Artifact {name: {0}, organization: {1}, version: {2}, type: {3}, extension: {4}} ) return a")
    ArtifactNode find(String name, String organization, String version, String type, String extension);
}
