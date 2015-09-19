package com.dnfeitosa.codegraph.db.graph.nodes;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.Index;
import org.neo4j.ogm.annotation.NodeEntity;

import static java.lang.String.format;

@NodeEntity
public class Jar {

	@GraphId
	private Long id;

	private String organization;
	private String name;
	private String version;

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}

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

    @Index(unique = true)
    public String uniqueName() {
        return format("%s-%s-%s", organization, name, version);
    }
}