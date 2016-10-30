package com.dnfeitosa.codegraph.db.nodes;

import org.neo4j.graphdb.Direction;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

@NodeEntity
@TypeAlias("Field")
public class FieldNode {

    @GraphId
    @Indexed
    private Long id;

    private String name;

    @RelatedTo(direction = Direction.OUTGOING, type = "OF_TYPE")
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
