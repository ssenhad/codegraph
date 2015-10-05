package com.dnfeitosa.codegraph.web.resources;

public class ModuleResource {

	private boolean fromOrg;
	private String application;
	private String name;

	public boolean isFromOrg() {
		return fromOrg;
	}

	public void setFromOrg(boolean fromOrg) {
		this.fromOrg = fromOrg;
	}

	public String getApplication() {
		return application;
	}

	public void setApplication(String application) {
		this.application = application;
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
