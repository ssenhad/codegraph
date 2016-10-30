package com.dnfeitosa.codegraph.server.api.resources;

import java.util.ArrayList;
import java.util.List;

import static com.dnfeitosa.codegraph.core.utils.PathUtils.join;

public class ArtifactResource implements Resource {

    private Long id;
    private String name;
    private String type;
    private String extension;
    private String version;
    private String organization;
    private List<ArtifactResource> dependencies = new ArrayList<>();
    private List<TypeResource> types = new ArrayList<>();;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public List<ArtifactResource> getDependencies() {
        return dependencies;
    }

    public void addDependency(ArtifactResource dependency) {
        dependencies.add(dependency);
    }

    public void addType(TypeResource type) {
        types.add(type);
    }

    @Override
    public String getUri() {
        if (id == null) {
            return null;
        }
        return join(BASE_URI, "artifacts", id);
    }

    public List<TypeResource> getTypes() {
        return types;
    }
}
