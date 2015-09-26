package com.dnfeitosa.codegraph.db.graph.repositories;

import com.dnfeitosa.codegraph.db.graph.nodes.ClassNode;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassRepository extends GraphRepository<ClassNode> {

}
