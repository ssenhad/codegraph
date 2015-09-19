package com.dnfeitosa.codegraph.db.graph.repositories;

import java.util.List;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.stereotype.Repository;

import com.dnfeitosa.codegraph.db.graph.nodes.ImpactResult;
import com.dnfeitosa.codegraph.db.graph.nodes.Module;

@Repository
public interface GraphModuleRepository extends GraphRepository<Module> {

	static final String IMMEDIATE_IMPACT_QUERY = " MATCH (module:Module {name: {0}})<-[:DEPENDS_ON]-(m:Module) "
			+ " RETURN DISTINCT module as impactor, m as impacted ";

	@Query(IMMEDIATE_IMPACT_QUERY + " UNION "
			+ " MATCH (module:Module {name: {0}})<-[:DEPENDS_ON]-(m:Module)<-[:DEPENDS_ON*]-(mod:Module) "
			+ " RETURN DISTINCT m as impactor, mod as impacted")
	List<ImpactResult> fullImpactOf(String moduleName);

	@Query(IMMEDIATE_IMPACT_QUERY)
	List<ImpactResult> immediateImpactOf(String moduleName);

	@Query("MATCH p=shortestPath((module:Module {name: {0}})-[:DEPENDS_ON]->(mod:Module)) "
		 + "RETURN distinct mod")
	List<Module> dependenciesOf(String moduleName);
}

