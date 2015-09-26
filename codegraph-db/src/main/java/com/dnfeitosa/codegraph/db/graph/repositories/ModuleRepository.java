package com.dnfeitosa.codegraph.db.graph.repositories;

import com.dnfeitosa.codegraph.db.graph.nodes.ImpactResult;
import com.dnfeitosa.codegraph.db.graph.nodes.Module;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ModuleRepository extends GraphRepository<Module> {

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

    @Query("MATCH (module:Module { name: {0} }) return module")
    Module findByName(String module);
}

