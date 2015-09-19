package com.dnfeitosa.codegraph.db.graph.repositories;

import com.dnfeitosa.codegraph.db.graph.nodes.Application;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GraphApplicationRepository extends GraphRepository<Application> {

	@Query("MATCH (app:Application) RETURN app.name")
	List<String> getApplicationNames();

    @Query("MATCH (app:Application {name: {0}}) RETURN app")
    Application findByName(String name);

}