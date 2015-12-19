package com.dnfeitosa.codegraph.web.resources.builders;

import com.dnfeitosa.codegraph.core.model.Dependency;
import com.dnfeitosa.codegraph.core.model.DependencyGraph;
import com.dnfeitosa.codegraph.core.model.Jar;
import com.dnfeitosa.codegraph.core.model.Module;
import com.dnfeitosa.codegraph.web.components.ResourceBuilders;
import com.dnfeitosa.codegraph.web.resources.EdgeResource;
import com.dnfeitosa.codegraph.web.resources.GraphResource;
import com.dnfeitosa.codegraph.web.resources.JarResource;
import com.dnfeitosa.codegraph.web.resources.ModuleResource;
import com.dnfeitosa.codegraph.web.resources.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

import static java.util.stream.Collectors.toSet;

@Component
public class DependencyGraphResourceBuilder {

    private ResourceBuilders resourceBuilders;

    @Autowired
    public DependencyGraphResourceBuilder(ResourceBuilders resourceBuilders) {
        this.resourceBuilders = resourceBuilders;
    }

    public GraphResource<ModuleResource> build(String applicationName, DependencyGraph dependencyGraph) {
        ModuleResource root = resourceBuilders.toResource(dependencyGraph.getRoot(), applicationName);
        Set<EdgeResource<Resource, Resource>> edges = dependencyGraph.getDependencies().stream()
                .map(dependency -> toEdgeResource(dependency))
                .collect(toSet());

        return new GraphResource<>(root, edges, "dependency-graph");
    }

    private EdgeResource<Resource, Resource> toEdgeResource(Dependency dependency) {
        Module dependent = dependency.getDependent();
        // To be fixed as part of issue #12
        String name = "buggy-relationship";
        if (dependent.getApplication() != null) {
            name = dependent.getApplication().getName();
        }
        ModuleResource dependentResource = resourceBuilders.toResource(dependent, name);
        Resource dependencyResource = getModuleResource(dependency.getJar());
        return new EdgeResource<>(dependentResource, dependencyResource);
    }

    private Resource getModuleResource(Jar dependency) {
        Module module = dependency.getModule();
        if (module == null) {
            JarResource jarResource = new JarResource();
            jarResource.setName(dependency.getName());
            jarResource.setVersion(dependency.getVersion());
            jarResource.setOrganization(dependency.getOrganization());
            return jarResource;
        }
        String appName = null;
        if (module.getApplication() != null) {
            appName = module.getApplication().getName();
        }
        return  resourceBuilders.toResource(module, appName);
    }
}
