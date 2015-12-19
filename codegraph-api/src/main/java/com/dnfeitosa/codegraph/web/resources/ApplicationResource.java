package com.dnfeitosa.codegraph.web.resources;

import java.util.List;

import static java.lang.String.format;

public class ApplicationResource implements Resource {

	private String name;
	private List<ModuleResource> modules;

    public ApplicationResource(String name) {
        this.name = name;
    }

    public String getName() {
		return name;
	}

    public List<ModuleResource> getModules() {
        return modules;
    }

    public void setModules(List<ModuleResource> modules) {
        this.modules = modules;
    }

    @Override
    public String getUri() {
        return format("/applications/%s", name);
    }
}