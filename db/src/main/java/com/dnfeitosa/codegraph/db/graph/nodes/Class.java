package com.dnfeitosa.codegraph.db.graph.nodes;

import static org.neo4j.graphdb.Direction.OUTGOING;

import java.util.Set;

import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

@NodeEntity
public class Class {

	@GraphId
	private Long id;

	@Indexed(unique = true)
	private String canonicalName;

	private String fullName;

	private String name;

	private String packageName;

	private String type;

	@Fetch
	@RelatedTo(direction = OUTGOING, type = "IMPORT")
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
