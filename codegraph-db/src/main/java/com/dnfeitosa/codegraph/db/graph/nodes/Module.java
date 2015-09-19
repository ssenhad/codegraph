package com.dnfeitosa.codegraph.db.graph.nodes;

import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

import java.util.Set;

import static org.neo4j.graphdb.Direction.OUTGOING;

@NodeEntity
public class Module {

	@GraphId
	private Long id;

	@Indexed(unique = true)
	private String name;

	@Indexed
	@RelatedTo(direction = OUTGOING, type = "DEPENDS_ON", enforceTargetType = true)
	private Set<Jar> dependencies;

	@Indexed
	@RelatedTo(direction = OUTGOING, type = "EXPORTS")
	private Set<Artifact> artifacts;

	@Indexed
	@RelatedTo(direction = OUTGOING, type = "HOLDS")
	private Set<Class> classes;

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

	public Set<Jar> getDependencies() {
		return dependencies;
	}

	public void setDependencies(Set<Jar> dependencies) {
		this.dependencies = dependencies;
	}

	public Set<Artifact> getArtifacts() {
		return artifacts;
	}

	public void setArtifacts(Set<Artifact> artifacts) {
		this.artifacts = artifacts;
	}

	public Set<Class> getClasses() {
		return classes;
	}

	public void setClasses(Set<Class> classes) {
		this.classes = classes;
	}
}
