package com.dnfeitosa.codegraph.db.graph.nodes.builders;

import java.util.Set;

public class ModuleNodeBuilders {

    private Set<ModuleNodeBuilder> moduleBuilders;

    public ModuleNodeBuilders(Set<ModuleNodeBuilder> moduleBuilders) {
        this.moduleBuilders = moduleBuilders;
    }

    public Set<ModuleNodeBuilder> builders() {
        return moduleBuilders;
    }
}
