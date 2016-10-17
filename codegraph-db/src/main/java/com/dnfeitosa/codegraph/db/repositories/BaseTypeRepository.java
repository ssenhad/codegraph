package com.dnfeitosa.codegraph.db.repositories;

import com.dnfeitosa.codegraph.db.nodes.TypeNode;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BaseTypeRepository extends GraphRepository<TypeNode> {
}
