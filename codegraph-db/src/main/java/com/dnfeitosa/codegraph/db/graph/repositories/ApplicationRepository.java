package com.dnfeitosa.codegraph.db.graph.repositories;

import com.dnfeitosa.codegraph.db.graph.nodes.ApplicationNode;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationRepository extends GraphRepository<ApplicationNode> {

	@Query("MATCH (app:Application) RETURN app.name")
	List<String> getApplicationNames();
}