package com.dnfeitosa.codegraph.analysis.dependencies;

import com.dnfeitosa.codegraph.core.model.Dependencies;
import com.dnfeitosa.codegraph.core.model.Dependency;
import com.dnfeitosa.codegraph.core.model.Jar;
import com.dnfeitosa.codegraph.core.model.Module;
import com.dnfeitosa.codegraph.web.TaintedEggs;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.Collections.emptyList;

public class DependencyCalculator {

    private TaintedEggs taintedEggs;

    public DependencyCalculator(TaintedEggs taintedEggs) {
        this.taintedEggs = taintedEggs;
    }

    public Dependencies dependenciesOf(Module module, boolean orgOnly) {
        List<Dependency> dependencies = dependenciesOf(module, new HashSet<String>(), orgOnly);
        return new Dependencies(dependencies, module);
    }

    public Dependencies dependenciesOf(Module module) {
        return dependenciesOf(module, false);
    }

    private List<Dependency> dependenciesOf(Module module, Set<String> known, boolean orgOnly) {
        if (known.contains(module.getName())) {
            return emptyList();
        }

        List<Dependency> dependencies = new ArrayList<Dependency>();
        for (Jar dependency : module.getDependencies()) {
            if (!orgOnly || orgOnly && dependency.fromorg()) {
                dependencies.add(new Dependency(module, dependency));
                dependencies.addAll(subDependenciesOf(dependency, known, orgOnly));
            }
            known.add(dependency.getName());
        }
        return dependencies;
    }

    private List<Dependency> subDependenciesOf(Jar dependency, Set<String> known, boolean orgOnly) {
        if (!dependency.fromorg()) {
            return emptyList();
        }

        return dependenciesOf(toModule(dependency), known, orgOnly);
    }

    private Module toModule(Jar dependency) {
        return taintedEggs.getModule(dependency.getName());
    }
}
