/**
 * Copyright (C) 2015-2018 Diego Feitosa [dnfeitosa@gmail.com]
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
package co.degraph.db.models.converters;

import co.degraph.core.models.Field;
import co.degraph.core.models.Method;
import co.degraph.core.models.Parameter;
import co.degraph.core.models.Type;
import co.degraph.db.models.FieldNode;
import co.degraph.db.models.MethodNode;
import co.degraph.db.models.ParameterNode;
import co.degraph.db.models.TypeNode;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

@Component
public class TypeNodeConverter {

    public TypeNode toNode(Type type) {
        if (type == null) {
            return null;
        }
        TypeNode typeNode = new TypeNode(type.getName(), type.getPackageName());
        typeNode.setSuperclass(toNode(type.getSuperclass()));
        type.getInterfaces().forEach(i -> typeNode.addInterface(toNode(i)));
        typeNode.setUsage(type.getUsage());
        typeNode.setType(type.getType());

        type.getMethods().forEach(m -> typeNode.addMethod(toNode(m)));
        type.getFields().forEach(f -> typeNode.addField(toNode(f)));
        return typeNode;
    }

    private FieldNode toNode(Field field) {
        FieldNode fieldNode = new FieldNode();
        fieldNode.setName(field.getName());
        fieldNode.setType(toNode(field.getType()));
        return fieldNode;
    }

    private MethodNode toNode(Method method) {
        MethodNode methodNode = new MethodNode(method.getName());
        method.getParameters().forEach(p -> methodNode.addParameter(toNode(p, methodNode)));
        method.getReturnTypes().forEach(r -> methodNode.addReturnType(toNode(r)));

        return methodNode;
    }

    private ParameterNode toNode(Parameter parameter, MethodNode methodNode) {
        ParameterNode parameterNode = new ParameterNode();
        parameterNode.setOrder(parameter.getOrder());
        parameterNode.setMethod(methodNode);
        parameterNode.setType(toNode(parameter.getType()));
        return parameterNode;
    }

    public Type toModel(TypeNode node) {
        if (node == null) {
            return null;
        }
        Type type = new Type(node.getName(), node.getPackageName(), node.getUsage(), node.getType());
        type.setSuperclass(toModel(node.getSuperclass()));
        node.getInterfaces().forEach(i -> type.addInterface(toModel(i)));
        node.getFields().forEach(f -> type.addField(toModel(f)));
        node.getMethods().forEach(m -> type.addMethod(toModel(m)));
        return type;
    }

    private Method toModel(MethodNode methodNode) {
        List<Parameter> parameters = methodNode.getParameters().stream().map(m -> new Parameter(m.getOrder(), toModel(m.getType()))).collect(toList());
        List<Type> returnTypes = methodNode.getReturnTypes().stream().map(r -> toModel(r)).collect(toList());
        return new Method(methodNode.getName(), parameters, returnTypes);
    }

    private Field toModel(FieldNode f) {
        return new Field(f.getName(), toModel(f.getType()));
    }

    public Set<Type> toModel(Set<TypeNode> types) {
        return types.stream()
                .map(this::toModel).collect(toSet());
    }
}
