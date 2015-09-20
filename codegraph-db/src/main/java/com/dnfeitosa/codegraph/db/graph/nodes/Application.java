package com.dnfeitosa.codegraph.db.graph.nodes;

import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

import java.util.Set;

import static org.neo4j.graphdb.Direction.OUTGOING;

@NodeEntity
public class Application {

	@GraphId
	private Long id;

	@Indexed(unique = true)
	private String name;

	@Indexed
    @Fetch
	@RelatedTo(direction = OUTGOING, type = "EXPORTS")
	private Set<Module> modules;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Module> getModules() {
		return modules;
	}

	public void setModules(Set<Module> modules) {
		this.modules = modules;
	}
}
