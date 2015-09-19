package com.dnfeitosa.codegraph.db.graph.nodes;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.Index;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.Set;


@NodeEntity
public class Class {

	@GraphId
	private Long id;

	@Index(unique = true)
	private String canonicalName;

	private String fullName;

	private String name;

	private String packageName;

	private String type;

	@Relationship(type = "IMPORT")
	private Set<Class> imports;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCanonicalName() {
		return canonicalName;
	}

	public void setCanonicalName(String canonicalName) {
		this.canonicalName = canonicalName;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Set<Class> getImports() {
		return imports;
	}

	public void setImports(Set<Class> imports) {
		this.imports = imports;
	}
}
