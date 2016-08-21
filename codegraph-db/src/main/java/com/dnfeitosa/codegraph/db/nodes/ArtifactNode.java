package com.dnfeitosa.codegraph.db.nodes;

import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;

@NodeEntity
@TypeAlias("Artifact")
public class ArtifactNode {

    @GraphId
    @Indexed
    private Long id;

    private String name;
    private String version;
    private String organization;
    private String extension;
    private String type;

//    @Fetch
//    @RelatedTo(direction = Direction.OUTGOING, type = "DEPENDS_ON")
//    private Set<ArtifactNode> dependencies;

    public ArtifactNode() {
    }

    public ArtifactNode(Long id, String name, String organization, String version, String type, String extension) {
        this.id = id;
        this.name = name;
        this.version = version;
        this.organization = organization;
        this.type = type;
        this.extension = extension;
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

    public String getExtension() {
        return extension;
    }

    public String getType() {
        return type;
    }
}
