package com.dnfeitosa.codegraph.web.resources.builders;

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
import java.util.stream.Collectors;

@Component
public class DependenciesResourceBuilder {

    private ResourceBuilders resourceBuilders;

    @Autowired
    public DependenciesResourceBuilder(ResourceBuilders resourceBuilders) {
        this.resourceBuilders = resourceBuilders;
    }

    public GraphResource<ModuleResource> build(String applicationName, String moduleName, DependencyGraph dependencyGraph) {
        ModuleResource root = resourceBuilders.toResource(dependencyGraph.getRoot(), applicationName);
        Set<EdgeResource<Resource, Resource>> edges = dependencyGraph.getDependencies().stream().map(dependency -> {
            Module dependent = dependency.getDependent();
            String name = "buggy-relationship";
            if (dependent.getApplication() != null) {
                name = dependent.getApplication().getName();
            }
            ModuleResource dependentResource = resourceBuilders.toResource(dependent, name);
            Resource dependencyResource = getModuleResource(dependency.getJar());
            return new EdgeResource<Resource, Resource>(dependentResource, dependencyResource);
        }).collect(Collectors.toSet());

        return new GraphResource<>(root, edges, "dependency-graph");
    }

    private Resource getModuleResource(Jar dep) {
        Module module = dep.getModule();
        if (module == null) {
            JarResource jarResource = new JarResource();
            jarResource.setName(dep.getName());
            jarResource.setVersion(dep.getVersion());
            jarResource.setOrganization(dep.getOrganization());
            return jarResource;
        }
        String appName = null;
        if (module.getApplication() != null) {
            appName = module.getApplication().getName();
        }
        return  resourceBuilders.toResource(module, appName);
    }
}
