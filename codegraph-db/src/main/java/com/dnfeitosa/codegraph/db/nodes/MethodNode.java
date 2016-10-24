package com.dnfeitosa.codegraph.db.nodes;

import org.neo4j.graphdb.Direction;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;
import org.springframework.data.neo4j.annotation.RelatedToVia;

import java.util.HashSet;
import java.util.Set;

@NodeEntity
@TypeAlias("Method")
public class MethodNode {

    @GraphId
    private Long id;

    private String name;

    @RelatedTo(direction = Direction.OUTGOING, type = "RETURNS")
    private Set<TypeNode> returnTypes;

    @RelatedToVia(direction = Direction.OUTGOING, type = "TAKES")
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
