package com.dnfeitosa.codegraph.db.graph.nodes.builders;

import com.dnfeitosa.codegraph.db.graph.nodes.ApplicationNode;
import com.dnfeitosa.codegraph.db.graph.nodes.ModuleNode;

import java.util.HashSet;
import java.util.Set;

import static com.dnfeitosa.coollections.Coollections.asSet;
import static java.util.stream.Collectors.toSet;

public class ApplicationNodeBuilder {

    private final ApplicationNode node;
    private final Set<ModuleNodeBuilder> moduleBuilders = new HashSet<>();

    public ApplicationNodeBuilder(String name) {
        this.node = new ApplicationNode();
        this.node.setName(name);
    }

    public ApplicationNodeBuilder exports(ModuleNodeBuilder... nodes) {
        moduleBuilders.addAll(asSet(nodes));
        return this;
    }

    public ApplicationNode node() {
        Set<ModuleNode> modules = moduleBuilders.stream()
                .map(m -> m.node())
                .collect(toSet());
        node.setModules(modules);
        return node;
    }

    public ApplicationNodeBuilder exports(ModuleNodeBuilders modules) {
        moduleBuilders.addAll(modules.builders());
        return this;
    }
}
