package com.dnfeitosa.codegraph.db.graph.nodes;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.Index;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.Set;

@NodeEntity
public class Module {

	@GraphId
	private Long id;

	@Index(unique = true)
	private String name;

	@Index
	@Relationship(type = "DEPENDS_ON")
	private Set<Jar> dependencies;

	@Index
	@Relationship(type = "EXPORTS")
	private Set<Artifact> artifacts;

	@Index
	@Relationship(type = "HOLDS")
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
