package com.dnfeitosa.codegraph.web.resources;

import java.util.List;

import static java.lang.String.format;

public class ModuleResource implements Resource {

    private final ApplicationResource application;
	private String name;

    public ModuleResource(ApplicationResource application, String name) {
        this.application = application;
        this.name = name;
    }

    public ApplicationResource getApplication() {
        return application;
    }

    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

    @Override
    public String getUri() {
        return format("/applications/%s/modules/%s", application.getName(), name);
    }

    public List<DependencyResource> getDependencies() {
        return null;
    }
}
