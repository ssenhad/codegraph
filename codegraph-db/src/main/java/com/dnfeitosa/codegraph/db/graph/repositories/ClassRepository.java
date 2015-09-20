package com.dnfeitosa.codegraph.db.graph.repositories;

import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassRepository extends GraphRepository<com.dnfeitosa.codegraph.db.graph.nodes.Class> {

}
