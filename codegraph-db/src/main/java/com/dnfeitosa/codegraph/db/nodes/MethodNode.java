package com.dnfeitosa.codegraph.db.nodes;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.HashSet;
import java.util.Set;

@NodeEntity(label = "Method")
public class MethodNode {

    @GraphId
    private Long id;

    private String name;

    private String qualifiedName;

    @Relationship(direction = Relationship.OUTGOING, type = "RETURNS")
    private Set<TypeNode> returnTypes;

    @Relationship(direction = Relationship.OUTGOING, type = "TAKES")
    private Set<ParameterNode> parameters;

    MethodNode() {
    }

    public MethodNode(String className, String name) {
        this.name = name;
        this.qualifiedName = className + "." + name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addParameter(ParameterNode parameter) {
        getParameters().add(parameter);
    }

    public Set<ParameterNode> getParameters() {
        if (parameters == null) {
            parameters = new HashSet<>();
        }
        return parameters;
    }

    public void addReturnType(TypeNode returnType) {
        getReturnTypes().add(returnType);
    }

    public Set<TypeNode> getReturnTypes() {
        if (returnTypes == null) {
            returnTypes = new HashSet<>();
        }
        return returnTypes;
    }
}
