package com.dnfeitosa.codegraph.db.models;


public class ParameterNode {

    private Long id;

    private Integer order;

    private MethodNode method;

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

