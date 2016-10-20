package com.dnfeitosa.codegraph.db.repositories;

import com.dnfeitosa.codegraph.db.nodes.TypeNode;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface BaseTypeRepository extends GraphRepository<TypeNode> {

    @Query("MATCH (a:Artifact)-[:CONTAINS]->(t:Type) where id(a) = 0 RETURN t")
    Set<TypeNode> findTypesFromArtifact(Long artifactId);
}
