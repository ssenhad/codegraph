package com.dnfeitosa.codegraph.db.repositories;


import com.dnfeitosa.codegraph.db.nodes.TypeNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.template.Neo4jOperations;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public class TypeRepository {

    @Autowired
    private BaseTypeRepository baseTypeRepository;

    @Autowired
    private Neo4jOperations template;

    public TypeNode save(TypeNode type) {
        type.getMethods().forEach(m -> {
            m.getParameters().forEach(p -> save(p.getType()));
            m.getReturnTypes().forEach(p -> save(p));
        });

        TypeNode existingType = getTypeNode(type);
//        TypeNode existingType = baseTypeRepository.findBySchemaPropertyValue("qualifiedName", type.getQualifiedName());
        if (existingType != null) {
            type.setId(existingType.getId());
        }
        type.getFields().forEach(f -> save(f.getType()));
        return baseTypeRepository.save(type);
    }

    private TypeNode getTypeNode(TypeNode type) {
        try {
            Collection<TypeNode> types = template.loadAllByProperty(TypeNode.class, "qualifiedName", type.getQualifiedName());
            System.out.println(type.getQualifiedName() + "   found " + types.size());
            if (types.isEmpty()) {
                return null;
            }
            return types.stream().findFirst().get();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    public Iterable<TypeNode> findAll() {
        return baseTypeRepository.findAll();
    }
}
