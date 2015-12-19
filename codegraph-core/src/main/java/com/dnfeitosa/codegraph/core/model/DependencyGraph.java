package com.dnfeitosa.codegraph.core.model;

import java.util.Set;

public class DependencyGraph {

    private final Module root;
    private final Set<Dependency> relationships;

    public DependencyGraph(Module root, Set<Dependency> relationships) {
        this.root = root;
        this.relationships = relationships;
    }

    public Set<Dependency> getDependencies() {
        return relationships;
    }

    public Module getRoot() {
        return root;
    }
}
