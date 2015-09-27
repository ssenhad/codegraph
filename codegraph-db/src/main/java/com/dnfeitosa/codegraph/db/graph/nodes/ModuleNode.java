package com.dnfeitosa.codegraph.db.graph.nodes;

import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

import java.util.Set;

import static org.neo4j.graphdb.Direction.INCOMING;
import static org.neo4j.graphdb.Direction.OUTGOING;

@TypeAlias("Module")
@NodeEntity
public class ModuleNode {

	@GraphId
	private Long id;

	@Indexed(unique = true)
	private String name;

	@RelatedTo(direction = OUTGOING, type = "DEPENDS_ON")
	private Set<JarNode> dependencies;

	@RelatedTo(direction = OUTGOING, type = "EXPORTS")
	private Set<ArtifactNode> artifactNodes;

	@RelatedTo(direction = OUTGOING, type = "HOLDS")
	private Set<ClassNode> classNodes;

    @RelatedTo(direction = INCOMING, type = "EXPORTS")
    private ApplicationNode applicationNode;

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

	public Set<JarNode> getDependencies() {
		return dependencies;
	}

	public void setDependencies(Set<JarNode> dependencies) {
		this.dependencies = dependencies;
	}

	public Set<ArtifactNode> getArtifacts() {
		return artifactNodes;
	}

	public void setArtifacts(Set<ArtifactNode> artifactNodes) {
		this.artifactNodes = artifactNodes;
	}

	public Set<ClassNode> getClassNodes() {
		return classNodes;
	}

	public void setClassNodes(Set<ClassNode> classNodes) {
		this.classNodes = classNodes;
	}

    public ApplicationNode getApplication() {
        return applicationNode;
    }

    public void setApplication(ApplicationNode applicationNode) {
        this.applicationNode = applicationNode;
    }
}
