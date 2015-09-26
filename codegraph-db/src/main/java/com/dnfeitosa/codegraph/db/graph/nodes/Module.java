package com.dnfeitosa.codegraph.db.graph.nodes;

import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

import java.util.Set;

import static org.neo4j.graphdb.Direction.INCOMING;
import static org.neo4j.graphdb.Direction.OUTGOING;

@NodeEntity
public class Module {

	@GraphId
	private Long id;

	@Indexed(unique = true)
	private String name;

	@RelatedTo(direction = OUTGOING, type = "DEPENDS_ON")
	private Set<Jar> dependencies;

	@RelatedTo(direction = OUTGOING, type = "EXPORTS")
	private Set<Artifact> artifacts;

	@RelatedTo(direction = OUTGOING, type = "HOLDS")
	private Set<Class> classes;

    @Fetch
    @RelatedTo(direction = INCOMING, type = "EXPORTS")
    private Application application;

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

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }
}
