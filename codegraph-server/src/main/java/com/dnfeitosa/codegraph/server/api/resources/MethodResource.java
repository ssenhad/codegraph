package com.dnfeitosa.codegraph.server.api.resources;

import java.util.ArrayList;
import java.util.List;

public class MethodResource implements LinkableResource {

    private String name;
    private List<TypeResource> returnTypes = new ArrayList<>();
    private List<ParameterResource> parameters = new ArrayList<>();


    MethodResource() {
    }

    public MethodResource(String name) {
        this.name = name;
    }

    @Override
    public String getUri() {
        return null;
    }

    public String getName() {
        return name;
    }

    public void setReturnTypes(List<TypeResource> returnTypes) {
        this.returnTypes = returnTypes;
    }

    public void setParameters(List<ParameterResource> parameters) {
        this.parameters = parameters;
    }

    public List<ParameterResource> getParameters() {
        return parameters;
    }

    public List<TypeResource> getReturnTypes() {
        return returnTypes;
    }
}
