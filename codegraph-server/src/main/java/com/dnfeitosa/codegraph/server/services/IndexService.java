package com.dnfeitosa.codegraph.server.services;

import com.dnfeitosa.codegraph.core.models.Artifact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IndexService {

    private final ArtifactService artifactService;

    @Autowired
    public IndexService(ArtifactService artifactService) {

        this.artifactService = artifactService;
    }

    public void index(Artifact artifact) {
        artifactService.save(artifact);
    }
}
