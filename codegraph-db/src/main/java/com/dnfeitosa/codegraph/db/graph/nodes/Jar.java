package com.dnfeitosa.codegraph.db.graph.nodes;

import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;

import static java.lang.String.format;

@NodeEntity
public class Jar {

	@GraphId
	private Long id;

	private String organization;
	private String name;
	private String version;

    @Indexed(unique = true)
    private String canonicalName;

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

    /**
     * This method to be called before saving any Jar entity. Neo4J currently does not
     * provide the support for unique indexes on composite fields, so this method
     * prepares the node to be saved by concatenating its values to form the canonical
     * name.
     */
    public void prepare() {
        canonicalName = format("%s-%s-%s", organization, name, version);
    }
}