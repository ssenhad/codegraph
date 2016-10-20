package com.dnfeitosa.codegraph.server.api.resources;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TypesResource implements Resource, Iterable<TypeResource> {

    private List<TypeResource> types = new ArrayList<>();;
    private ArtifactResource artifactResource;

    public TypesResource() {
    }

    public TypesResource(ArtifactResource artifactResource) {
        this.artifactResource = artifactResource;
    }

    public void add(TypeResource type) {
        getTypes().add(type);
    }

    public List<TypeResource> getTypes() {
        return types;
    }

    @Override
    public String getUri() {
        String parentUri = artifactResource.getUri();
        if (parentUri == null) {
            return null;
        }
        return String.format("%s/types", parentUri);
    }

    @Override
    public Iterator<TypeResource> iterator() {
        return types.iterator();
    }
}
