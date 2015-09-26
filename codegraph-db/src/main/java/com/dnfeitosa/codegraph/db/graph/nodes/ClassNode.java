package com.dnfeitosa.codegraph.db.graph.nodes;

import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

import java.util.Set;

import static org.neo4j.graphdb.Direction.OUTGOING;

@TypeAlias("Class")
@NodeEntity
public class ClassNode {

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
	private Set<ClassNode> imports;

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

	public Set<ClassNode> getImports() {
		return imports;
	}

	public void setImports(Set<ClassNode> imports) {
		this.imports = imports;
	}
}
