package com.dnfeitosa.codegraph.web.resources;

public class JarResource {

	private String name;
	private String version;
	private boolean fromOrg;

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

	public boolean isFromOrg() {
		return fromOrg;
	}

	public void setFromOrg(boolean fromOrg) {
		this.fromOrg = fromOrg;
	}
}