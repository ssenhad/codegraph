package com.dnfeitosa.codegraph.web.resources;

public class EdgeResource<S extends Resource, E extends Resource> {

    private final S startNode;
    private final E endNode;

    public EdgeResource(S startNode, E endNode) {
        this.startNode = startNode;
        this.endNode = endNode;
    }

    public S getStartNode() {
        return startNode;
    }

    public E getEndNode() {
        return endNode;
    }
}
