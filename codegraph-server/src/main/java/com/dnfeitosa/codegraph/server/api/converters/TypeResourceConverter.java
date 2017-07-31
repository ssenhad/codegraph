/**
 * Copyright (C) 2015-2017 Diego Feitosa [dnfeitosa@gmail.com]
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
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
        if (type == null) {
            return null;
        }
        TypeResource resource = new TypeResource();
        resource.setType(type.getType());
        resource.setName(type.getName());
        resource.setPackageName(type.getPackageName());
        resource.setUsage(type.getUsage());

        resource.setSuperclass(toResource(type.getSuperclass()));
        resource.setInterfaces(type.getInterfaces().stream().map(this::toResource).collect(toList()));

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
        if (typeResource == null) {
            return null;
        }
        Type type = new Type(typeResource.getName(), typeResource.getPackageName(), typeResource.getUsage(), typeResource.getType());
        typeResource.getMethods().forEach(method -> type.addMethod(toModel(method)));
        typeResource.getFields().forEach(field -> type.addField(toModel(field)));
        type.setSuperclass(toModel(typeResource.getSuperclass()));
        typeResource.getInterfaces().forEach(interface_ -> type.addInterface(toModel(interface_)));
        return type;
    }

    private Field toModel(FieldResource field) {
        return new Field(field.getName(), toModel(field.getType()));
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
