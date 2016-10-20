package com.dnfeitosa.codegraph.server.api.services;

import com.dnfeitosa.codegraph.core.models.Type;
import com.dnfeitosa.codegraph.db.nodes.TypeNode;
import com.dnfeitosa.codegraph.db.nodes.converters.TypeNodeConverter;
import com.dnfeitosa.codegraph.db.repositories.TypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class TypeService {

    @Autowired
    private TypeRepository typeRepository;

    @Autowired
    private TypeNodeConverter typeConverter;

    public Set<Type> getTypesFromArtifact(Long artifactId) {
        Set<TypeNode> types = typeRepository.loadTypesFromArtifact(artifactId);
        return typeConverter.toModel(types);
    }
}
