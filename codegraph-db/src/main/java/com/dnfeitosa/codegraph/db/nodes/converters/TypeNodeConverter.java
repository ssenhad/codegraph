package com.dnfeitosa.codegraph.db.nodes.converters;

import com.dnfeitosa.codegraph.core.models.Field;
import com.dnfeitosa.codegraph.core.models.Method;
import com.dnfeitosa.codegraph.core.models.Parameter;
import com.dnfeitosa.codegraph.core.models.Type;
import com.dnfeitosa.codegraph.db.nodes.FieldNode;
import com.dnfeitosa.codegraph.db.nodes.MethodNode;
import com.dnfeitosa.codegraph.db.nodes.ParameterNode;
import com.dnfeitosa.codegraph.db.nodes.TypeNode;
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
