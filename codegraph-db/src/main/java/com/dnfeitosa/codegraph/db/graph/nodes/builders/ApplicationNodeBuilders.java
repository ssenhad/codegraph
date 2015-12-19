package com.dnfeitosa.codegraph.db.graph.nodes.builders;

import java.util.Set;
import java.util.function.Consumer;

public class ApplicationNodeBuilders {
    private Set<ApplicationNodeBuilder> applicationBuilders;

    public ApplicationNodeBuilders(Set<ApplicationNodeBuilder> applicationBuilders) {
        this.applicationBuilders = applicationBuilders;
    }

    public void each(Consumer<ApplicationNodeBuilder> action) {
        builders().stream().forEach(action);
    }

    public Set<ApplicationNodeBuilder> builders() {
        return applicationBuilders;
    }
}
