package com.dnfeitosa.codegraph.web.resources;

import static java.lang.String.format;

public class ModuleResource implements Resource {

    private final ApplicationResource parent;
	private final String name;

    public ModuleResource(ApplicationResource application, String name) {
        this.parent = application;
        this.name = name;
    }

    public ApplicationResource getParent() {
        return parent;
    }

    public String getName() {
		return name;
	}

    @Override
    public String getUri() {
        return format("/applications/%s/modules/%s", parent.getName(), name);
    }
}
