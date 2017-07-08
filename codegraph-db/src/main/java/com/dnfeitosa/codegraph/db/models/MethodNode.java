package com.dnfeitosa.codegraph.db.models;

import java.util.HashSet;
import java.util.Set;

public class MethodNode {

    private Long id;

    private String name;

    private Set<TypeNode> returnTypes;

    private Set<ParameterNode> parameters;

    MethodNode() {
    }

    public MethodNode(String name) {
        this.name = name;
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
