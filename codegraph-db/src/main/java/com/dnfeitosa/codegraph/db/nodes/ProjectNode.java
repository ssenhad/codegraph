package com.dnfeitosa.codegraph.db.nodes;

import org.neo4j.graphdb.Direction;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

import java.util.HashSet;
import java.util.Set;

@NodeEntity
@TypeAlias("Project")
public class ProjectNode {

    @GraphId
    @Indexed
    private Long id;

    private String name;
    private String version;
    private String organization;

    @Fetch
    @RelatedTo(direction = Direction.OUTGOING, type = "EXPORTS")
    private Set<ArtifactNode> artifacts;

    public ProjectNode() {
    }

    public ProjectNode(Long id, String name, String organization, String version) {
        this.id = id;
        this.name = name;
        this.version = version;
        this.organization = organization;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getVersion() {
        return version;
    }

    public String getOrganization() {
        return organization;
    }

    public void addArtifact(ArtifactNode artifactNode) {
        getArtifacts().add(artifactNode);
    }

    public Set<ArtifactNode> getArtifacts() {
        if (artifacts == null) {
            artifacts = new HashSet<>();
        }
        return artifacts;
    }
}
