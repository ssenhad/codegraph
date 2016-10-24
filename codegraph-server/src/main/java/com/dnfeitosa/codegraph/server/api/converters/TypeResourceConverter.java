package com.dnfeitosa.codegraph.server.api.converters;

import com.dnfeitosa.codegraph.core.models.Field;
import com.dnfeitosa.codegraph.core.models.Method;
import com.dnfeitosa.codegraph.core.models.Parameter;
import com.dnfeitosa.codegraph.core.models.Type;
import com.dnfeitosa.codegraph.server.api.resources.ArtifactResource;
import com.dnfeitosa.codegraph.server.api.resources.FieldResource;
import com.dnfeitosa.codegraph.server.api.resources.MethodResource;
import com.dnfeitosa.codegraph.server.api.resources.ParameterResource;
import com.dnfeitosa.codegraph.server.api.resources.TypeResource;
import com.dnfeitosa.codegraph.server.api.resources.TypesResource;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;

@Component
public class TypeResourceConverter {

    public TypesResource toResource(ArtifactResource artifact, Set<Type> types) {
        TypesResource typesResource = new TypesResource(artifact);
        types.forEach(t -> typesResource.add(toResource(t)));
        return typesResource;
    }

    public TypeResource toResource(Type type) {
        TypeResource resource = new TypeResource();
        resource.setType(type.getType());
        resource.setName(type.getName());
        resource.setPackageName(type.getPackageName());
        resource.setUsage(type.getUsage());

        resource.setMethods(type.getMethods().stream().map(this::toResource).collect(toList()));
        resource.setFields(type.getFields().stream().map(this::toResource).collect(toList()));
        return resource;
    }

    private FieldResource toResource(Field field) {
        return new FieldResource(field.getName(), toResource(field.getType()));
    }

    private MethodResource toResource(Method method) {
        MethodResource methodResource = new MethodResource(method.getName());
        methodResource.setReturnTypes(method.getReturnTypes().stream().map(this::toResource).collect(toList()));
        methodResource.setParameters(method.getParameters().stream().map(this::toResource).collect(toList()));
        return methodResource;
    }

    private ParameterResource toResource(Parameter parameter) {
        return new ParameterResource(toResource(parameter.getType()), parameter.getOrder());
    }

    public Type toModel(TypeResource typeResource) {
        Type type = new Type(typeResource.getName(), typeResource.getPackageName(), typeResource.getUsage(), typeResource.getType());
        typeResource.getMethods().forEach(method -> type.addMethod(toModel(method)));
        return type;
    }

    private Method toModel(MethodResource method) {
        List<Parameter> parameters = toParameters(method.getParameters());
        List<Type> returnTypes = toReturnTypes(method);
        return new Method(method.getName(), parameters, returnTypes);
    }

    private List<Type> toReturnTypes(MethodResource method) {
        return method.getReturnTypes().stream()
                .map(this::toModel)
                .collect(toList());
    }

    private List<Parameter> toParameters(List<ParameterResource> parameters) {
        return parameters.stream()
                .map(this::toModel)
                .collect(toList());
    }

    private Parameter toModel(ParameterResource p) {
        return new Parameter(p.getOrder(), toModel(p.getType()));
    }
}