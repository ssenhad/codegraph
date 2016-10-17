package com.dnfeitosa.codegraph.db.repositories;


import com.dnfeitosa.codegraph.db.nodes.TypeNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.conversion.Result;
import org.springframework.stereotype.Repository;

@Repository
public class TypeRepository {

    @Autowired
    private BaseTypeRepository baseTypeRepository;

    public TypeNode save(TypeNode type) {
        TypeNode existingType = baseTypeRepository.findBySchemaPropertyValue("qualifiedName", type.getQualifiedName());
        if (existingType != null) {
            type.setId(existingType.getId());
        }

        type.getMethods().forEach(m -> {
            m.getParameters().forEach(p -> save(p.getType()));
            m.getReturnTypes().forEach(p -> save(p));
        });
        type.getFields().forEach(f -> save(f.getType()));
        return baseTypeRepository.save(type);
    }

    public Result<TypeNode> findAll() {
        return baseTypeRepository.findAll();
    }
}
