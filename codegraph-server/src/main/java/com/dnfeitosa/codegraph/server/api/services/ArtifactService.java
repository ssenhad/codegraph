package com.dnfeitosa.codegraph.server.api.services;

import com.dnfeitosa.codegraph.db.nodes.converters.ArtifactNodeConverter;
import com.dnfeitosa.codegraph.db.repositories.ArtifactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArtifactService {

    private ArtifactNodeConverter nodeConverter;
    private ArtifactRepository artifactRepository;


    @Autowired
    public ArtifactService(ArtifactNodeConverter nodeConverter, ArtifactRepository artifactRepository) {
        this.nodeConverter = nodeConverter;
        this.artifactRepository = artifactRepository;
    }


}
