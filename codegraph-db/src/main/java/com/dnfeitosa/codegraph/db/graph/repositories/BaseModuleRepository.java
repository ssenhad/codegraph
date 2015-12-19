package com.dnfeitosa.codegraph.db.graph.repositories;

import com.dnfeitosa.codegraph.db.graph.nodes.ImpactResult;
import com.dnfeitosa.codegraph.db.graph.nodes.ModuleNode;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.neo4j.repository.TraversalRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface BaseModuleRepository extends GraphRepository<ModuleNode>, TraversalRepository<ModuleNode> {

    String IMMEDIATE_IMPACT_QUERY = " MATCH (module:Module {name: {0}})<-[:DEPENDS_ON]-(m:Module) "
            + " RETURN DISTINCT module as impactor, m as impacted ";

	@Query(IMMEDIATE_IMPACT_QUERY + " UNION "
			+ " MATCH (module:Module {name: {0}})<-[:DEPENDS_ON]-(m:Module)<-[:DEPENDS_ON*]-(mod:Module) "
			+ " RETURN DISTINCT m as impactor, mod as impacted")
	List<ImpactResult> fullImpactOf(String moduleName);

	@Query(IMMEDIATE_IMPACT_QUERY)
	List<ImpactResult> immediateImpactOf(String moduleName);

    @Query(" MATCH (module:Module {name: {0}}) RETURN module as dependency " +
            " UNION " +
            "MATCH p=shortestPath((module:Module {name: {0}})-[DEPENDS_ON*]->(dependency:Module)) " +
            "RETURN distinct dependency ")
    Set<ModuleNode> dependenciesOf(String name);

    @Query("MATCH (module:Module {name: {0}}) RETURN module")
    ModuleNode findByName(String module);
}
