package com.dnfeitosa.codegraph.server.api.resources;

public class ParameterResource implements Resource {

    private TypeResource type;
    private Integer order;

    ParameterResource() {
    }

    public ParameterResource(TypeResource type, Integer order) {
        this.type = type;
        this.order = order;
    }

    public TypeResource getType() {
        return type;
    }

    public Integer getOrder() {
        return order;
    }

    @Override
    public String getUri() {
        return null;
    }
}
