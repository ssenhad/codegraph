package com.dnfeitosa.codegraph.db.graph.nodes;

import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.neo4j.annotation.Fetch;
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
	private Set<ModuleNode> dependencies;

	@RelatedTo(direction = OUTGOING, type = "EXPORTS")
	private Set<ArtifactNode> artifactNodes;

	@RelatedTo(direction = OUTGOING, type = "HOLDS")
	private Set<ClassNode> classNodes;

    @Fetch
    @RelatedTo(direction = INCOMING, type = "EXPORTS")
    private ApplicationNode applicationNode;

    private String organization;
    private String version;

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

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

	public Set<ModuleNode> getDependencies() {
		return dependencies;
	}

	public void setDependencies(Set<ModuleNode> dependencies) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ModuleNode that = (ModuleNode) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        return !(name != null ? !name.equals(that.name) : that.name != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
