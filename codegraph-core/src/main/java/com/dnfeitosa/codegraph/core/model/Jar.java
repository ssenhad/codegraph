package com.dnfeitosa.codegraph.core.model;

import static org.apache.commons.lang.builder.EqualsBuilder.reflectionEquals;
import static org.apache.commons.lang.builder.HashCodeBuilder.reflectionHashCode;

public class Jar {

	private final String organization;
	private final String name;
	private final String version;

	public Jar(String organization, String name, String version) {
		this.organization = organization;
		this.name = name;
		this.version = version;
	}

	public boolean fromOrg() {
		return "org".equals(organization);
	}

	public String getName() {
		return name;
	}

	public String getVersion() {
		return version;
	}

    public String getOrganization() {
        return organization;
    }

    @Override
    public String toString() {
        return String.format("Jar{org: %s, name: %s, version: %s}", organization, name, version);
    }


    @Override
	public boolean equals(Object o) {
		return reflectionEquals(this, o);
	}

	@Override
	public int hashCode() {
		return reflectionHashCode(this);
	}
}
