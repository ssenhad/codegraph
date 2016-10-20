package com.dnfeitosa.codegraph.db.nodes.converters;

import com.dnfeitosa.codegraph.core.models.Type;
import com.dnfeitosa.codegraph.db.nodes.TypeNode;
import org.springframework.stereotype.Component;

import java.util.Set;

import static java.util.stream.Collectors.toSet;

@Component
public class TypeNodeConverter {

    public TypeNode toNode(Type type) {
        TypeNode typeNode = new TypeNode(type.getName(), type.getPackageName());
        typeNode.setUsage(type.getUsage());
        typeNode.setType(type.getType());
        return typeNode;
    }

    public Type toModel(TypeNode node) {
        return new Type(node.getName(), node.getPackageName(), node.getUsage(), node.getType());
    }

    public Set<Type> toModel(Set<TypeNode> types) {
        return types.stream()
                .map(this::toModel).collect(toSet());
    }
}
