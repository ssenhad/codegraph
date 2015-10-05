package com.dnfeitosa.codegraph.web.resources;

import com.dnfeitosa.codegraph.web.resources.Resource.Resource;

import static java.lang.String.format;

public class ModuleResource implements Resource {

    private final ApplicationResource application;
    private boolean fromOrg;
	private String name;

    public ModuleResource(ApplicationResource application, String name) {
        this.application = application;
        this.name = name;
    }

    public boolean isFromOrg() {
		return fromOrg;
	}

	public void setFromOrg(boolean fromOrg) {
		this.fromOrg = fromOrg;
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
}
