package com.dnfeitosa.codegraph.db.repositories;


import com.dnfeitosa.codegraph.db.nodes.TypeNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.conversion.Result;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public class TypeRepository {

    @Autowired
    private BaseTypeRepository baseRepository;

    public TypeNode save(TypeNode type) {
        TypeNode existingType = baseRepository.findBySchemaPropertyValue("qualifiedName", type.getQualifiedName());
        if (existingType != null) {
            type.setId(existingType.getId());
        }

        type.getMethods().forEach(m -> {
            m.getParameters().forEach(p -> save(p.getType()));
            m.getReturnTypes().forEach(p -> save(p));
        });
        type.getFields().forEach(f -> save(f.getType()));
        return baseRepository.save(type);
    }

    public Result<TypeNode> findAll() {
        return baseRepository.findAll();
    }

    public Set<TypeNode> loadTypesFromArtifact(Long artifactId) {
        return baseRepository.findTypesFromArtifact(artifactId);
    }
}
