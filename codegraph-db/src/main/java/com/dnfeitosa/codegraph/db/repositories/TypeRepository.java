package com.dnfeitosa.codegraph.db.repositories;


import com.dnfeitosa.codegraph.db.nodes.TypeNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.conversion.Result;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Repository
public class TypeRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(TypeRepository.class);

    private static int find = 0;
    private static int hit = 0;
    private static int missCache = 0;
    private static int missMiss = 0;
    @Autowired
    private BaseTypeRepository baseRepository;

    private Map<String, TypeNode> cache = new HashMap<>();

    public TypeNode save(TypeNode type) {
        TypeNode existingType = findExisting(type);
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

    private TypeNode findExisting(TypeNode type) {
        String qualifiedName = type.getQualifiedName();
        if (cache.containsKey(qualifiedName)) {
            LOGGER.info("-> hit {}", hit++);
            return cache.get(qualifiedName);
        }
        LOGGER.info("-> find {}", find++);
        TypeNode existing = baseRepository.findBySchemaPropertyValue("qualifiedName", qualifiedName);
        if (existing != null) {
            LOGGER.info(" -> miss cache {}", missCache++);
            cache.put(qualifiedName, existing);
        }
        LOGGER.info("-> miss miss {}", missMiss++);
        return existing;
    }

    public Result<TypeNode> findAll() {
        return baseRepository.findAll();
    }

    public Set<TypeNode> loadTypesFromArtifact(Long artifactId) {
        return baseRepository.findTypesFromArtifact(artifactId);
    }
}
