package com.dnfeitosa.codegraph.db.graph.nodes.builders;

import com.dnfeitosa.codegraph.db.graph.nodes.ModuleNode;

import java.util.HashSet;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

public class ModuleNodeBuilder {

    private final ModuleNode node;
    private final Set<ModuleNodeBuilder> dependencyBuilders = new HashSet<>();

    public ModuleNodeBuilder(String name) {
        this.node = new ModuleNode();
        this.node.setName(name);
    }

    public ModuleNode node() {
        Set<ModuleNode> dependencies = dependencyBuilders.stream().map(b -> b.node()).collect(toSet());
        node.setDependencies(dependencies);
        return node;
    }

    public ModuleNodeBuilder exportedBy(ApplicationNodeBuilder applicationBuilder) {
        applicationBuilder.exports(this);
        return this;
    }

    public ModuleNodeBuilder dependsOn(ModuleNodeBuilders moduleBuilders) {
        return dependsOn(moduleBuilders.builders());
    }

    public ModuleNodeBuilder dependsOn(Set<ModuleNodeBuilder> modules) {
        this.dependencyBuilders.addAll(modules);
        return this;
    }

    public ModuleNodeBuilder dependsOn(ModuleNodeBuilder module) {
        this.dependencyBuilders.add(module);
        return this;
    }
}
