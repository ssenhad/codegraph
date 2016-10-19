package com.dnfeitosa.codegraph.server.api.services;

import com.dnfeitosa.codegraph.core.models.Artifact;
import com.dnfeitosa.codegraph.db.nodes.ArtifactNode;
import com.dnfeitosa.codegraph.db.nodes.converters.ArtifactNodeConverter;
import com.dnfeitosa.codegraph.db.repositories.ArtifactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.dnfeitosa.codegraph.db.utils.ResultUtils.stream;
import static java.util.stream.Collectors.toList;

@Service
public class ArtifactService {

    private ArtifactNodeConverter nodeConverter;
    private ArtifactRepository artifactRepository;

    @Autowired
    public ArtifactService(ArtifactNodeConverter nodeConverter, ArtifactRepository artifactRepository) {
        this.nodeConverter = nodeConverter;
        this.artifactRepository = artifactRepository;
    }

    public Artifact addArtifact(Artifact artifact) {
        ArtifactNode node = nodeConverter.toNode(artifact);
        mergeIfExists(node);

        node.getDependencies().forEach(dependency -> mergeIfExists(dependency));
        ArtifactNode saved = artifactRepository.save(node);
        return nodeConverter.toModel(saved);
    }

    private void mergeIfExists(ArtifactNode node) {
        ArtifactNode existingNode = artifactRepository.find(node.getName(), node.getOrganization(), node.getVersion(), node.getType(), node.getExtension());
        if (existingNode != null) {
            node.setId(existingNode.getId());
            existingNode.getDependencies().forEach(node::addDependency);
        }
    }

    public Artifact loadArtifact(Long id) {
        ArtifactNode node = artifactRepository.findOne(id);
        if (node == null) {
            throw new ItemDoesNotExistException(String.format("Artifact '%d' does not exist.", id));
        }
        return nodeConverter.toModel(node);
    }

    public List<Artifact> loadAll() {
        Iterable<ArtifactNode> all = artifactRepository.findAll();
        return stream(all)
                .map(nodeConverter::toModel)
                .collect(toList());
    }
}
