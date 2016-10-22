package com.dnfeitosa.codegraph.server.api.resources;

public class FieldResource implements Resource {

    private String name;
    private TypeResource type;

    FieldResource() {
    }

    public FieldResource(String name, TypeResource type) {
        this.name = name;
        this.type = type;
    }

    @Override
    public String getUri() {
        return null;
    }

    public String getName() {
        return name;
    }

    public TypeResource getType() {
        return type;
    }
}
