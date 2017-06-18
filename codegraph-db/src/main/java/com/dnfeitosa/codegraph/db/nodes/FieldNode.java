package com.dnfeitosa.codegraph.db.nodes;

public class FieldNode {

    private Long id;

    private String name;

    private TypeNode type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TypeNode getType() {
        return type;
    }

    public void setType(TypeNode type) {
        this.type = type;
    }
}
