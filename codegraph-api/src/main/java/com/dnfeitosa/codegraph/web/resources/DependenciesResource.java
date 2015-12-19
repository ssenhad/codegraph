package com.dnfeitosa.codegraph.web.resources;

import java.util.Set;

import static java.lang.String.format;

public class DependenciesResource implements Resource {

	private ModuleResource module;
    private Set<Resource> dependencies;

    public DependenciesResource(ModuleResource module, Set<Resource> dependencies) {
        this.module = module;
        this.dependencies = dependencies;
    }

    public ModuleResource getModule() {
        return module;
    }

    public Set<Resource> getDependencies() {
        return dependencies;
    }

    @Override
    public String getUri() {
        return format("/applications/%s/modules/%s/dependencies",
                module.getApplication().getName(),
                module.getName());
    }
}