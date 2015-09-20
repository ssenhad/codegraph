package com.dnfeitosa.codegraph.db.graph.repositories;

import java.util.List;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.stereotype.Repository;

import com.dnfeitosa.codegraph.db.graph.nodes.Application;

@Repository
public interface ApplicationRepository extends GraphRepository<Application> {

	@Query("MATCH (app:Application) RETURN app.name")
	List<String> getApplicationNames();
}