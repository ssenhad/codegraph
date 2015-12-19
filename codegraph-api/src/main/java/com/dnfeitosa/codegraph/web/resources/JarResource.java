package com.dnfeitosa.codegraph.web.resources;


public class JarResource implements Resource {

	private String name;
	private String version;
    private String organization;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    @Override
    public String getUri() {
        return null;
    }
}