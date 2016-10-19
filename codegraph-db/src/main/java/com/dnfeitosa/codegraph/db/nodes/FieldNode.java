package com.dnfeitosa.codegraph.db.nodes;


import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.Relationship;
import org.springframework.data.annotation.TypeAlias;

@TypeAlias("FieldNode")
public class FieldNode {

    @GraphId
    private Long id;

    private String name;

    @Relationship(direction = Relationship.OUTGOING, type = "OF")
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
