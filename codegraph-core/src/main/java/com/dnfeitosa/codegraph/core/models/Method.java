package com.dnfeitosa.codegraph.core.models;

import java.util.ArrayList;
import java.util.List;

public class Method {

    private final String name;
    private final List<Parameter> parameters;
    private final List<Type> returnTypes;

    public Method(String name, List<Parameter> parameters, List<Type> returnTypes) {
        this.name = name;
        this.parameters = parameters;
        this.returnTypes = returnTypes;
    }

    public String getName() {
        return name;
    }

    public List<Parameter> getParameters() {
        return new ArrayList<>(parameters);
    }

    public List<Type> getReturnTypes() {
        return returnTypes;
    }
}
