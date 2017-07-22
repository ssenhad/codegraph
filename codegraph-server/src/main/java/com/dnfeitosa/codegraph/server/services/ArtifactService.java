package com.dnfeitosa.codegraph.server.services;

import com.dnfeitosa.codegraph.core.models.Artifact;
import com.dnfeitosa.codegraph.db.models.ArtifactNode;
import com.dnfeitosa.codegraph.db.models.converters.ArtifactNodeConverter;
import com.dnfeitosa.codegraph.db.repositories.ArtifactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArtifactService {

    private final ArtifactNodeConverter nodeConverter;
    private final ArtifactRepository repository;

    @Autowired
    public ArtifactService(ArtifactNodeConverter nodeConverter, ArtifactRepository repository) {
        this.nodeConverter = nodeConverter;
        this.repository = repository;
    }

    public Artifact load(String organization, String name, String version) {
        ArtifactNode artifactNode = repository.load(organization, name, version);
        if (artifactNode == null) {
            return null;
        }
        return nodeConverter.toModel(artifactNode);
    }

    public void save(Artifact artifact) {
        ArtifactNode artifactNode = nodeConverter.toNode(artifact);
        repository.save(artifactNode);
    }
}
