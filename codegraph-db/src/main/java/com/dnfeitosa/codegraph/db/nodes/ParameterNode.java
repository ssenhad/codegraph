package com.dnfeitosa.codegraph.db.nodes;


import org.springframework.data.neo4j.annotation.EndNode;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.RelationshipEntity;
import org.springframework.data.neo4j.annotation.StartNode;

@RelationshipEntity(type = "TAKES")
public class ParameterNode {

    @GraphId
    private Long id;

    private Integer order;

    @StartNode
    private MethodNode method;

    @EndNode
    private TypeNode type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public MethodNode getMethod() {
        return method;
    }

    public void setMethod(MethodNode method) {
        this.method = method;
    }

    public TypeNode getType() {
        return type;
    }

    public void setType(TypeNode type) {
        this.type = type;
    }
}

