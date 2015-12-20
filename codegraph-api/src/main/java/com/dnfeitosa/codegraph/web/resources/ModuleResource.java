package com.dnfeitosa.codegraph.web.resources;

import static java.lang.String.format;

public class ModuleResource implements Resource {

    private ApplicationResource parent;
    private String applicationName;
    private String organization;
    private String version;
    private final String name;

    public ModuleResource(String applicationName, String name) {
        this.applicationName = applicationName;
        this.name = name;
    }

    @Deprecated
    public ModuleResource(ApplicationResource application, String name) {
        this(application.getName(), name);
        this.parent = application;
    }

    public void setParent(ApplicationResource parent) {
        this.parent = parent;
    }

    public ApplicationResource getParent() {
        return parent;
    }

    public String getName() {
		return name;
	}

    @Override
    public String getUri() {
        return format("/applications/%s/modules/%s", applicationName, name);
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
