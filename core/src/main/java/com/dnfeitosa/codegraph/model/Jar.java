package com.dnfeitosa.codegraph.model;

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

	@Override
	public boolean equals(Object o) {
		return reflectionEquals(this, o);
	}

	@Override
	public int hashCode() {
		return reflectionHashCode(this);
	}

	public String getOrganization() {
		return organization;
	}
}
