package com.dnfeitosa.codegraph.web.resources;

import java.util.Set;

import static java.lang.String.format;

public class GraphResource<T extends Resource> implements Resource {

    private final T root;
    private final Set<EdgeResource<Resource, Resource>> edges;
    private String name;

    public GraphResource(T root, Set<EdgeResource<Resource, Resource>> edges, String name) {
        this.root = root;
        this.edges = edges;
        this.name = name;
    }

    @Override
    public String getUri() {
        return format("%s/%s", root.getUri(), name);
    }

    public T getRoot() {
        return root;
    }

    public Set<EdgeResource<Resource, Resource>> getEdges() {
        return edges;
    }
}
