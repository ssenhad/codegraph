package com.dnfeitosa.codegraph.server.api.services;

import com.dnfeitosa.codegraph.core.models.Artifact;
import com.dnfeitosa.codegraph.db.nodes.ArtifactNode;
import com.dnfeitosa.codegraph.db.nodes.converters.ArtifactNodeConverter;
import com.dnfeitosa.codegraph.db.repositories.ArtifactRepository;
import com.dnfeitosa.codegraph.db.repositories.TypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.conversion.Result;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.dnfeitosa.codegraph.db.utils.ResultUtils.stream;
import static java.util.stream.Collectors.toList;

@Service
public class ArtifactService {

    private ArtifactNodeConverter nodeConverter;
    private ArtifactRepository artifactRepository;
    private TypeRepository typeRepository;

    @Autowired
    public ArtifactService(ArtifactNodeConverter nodeConverter, ArtifactRepository artifactRepository, TypeRepository typeRepository) {
        this.nodeConverter = nodeConverter;
        this.artifactRepository = artifactRepository;
        this.typeRepository = typeRepository;
    }

    public Artifact addArtifact(Artifact artifact) {
        ArtifactNode node = nodeConverter.toNode(artifact);
        mergeIfExists(node);

        node.getDependencies().forEach(dependency -> mergeIfExists(dependency));
        node.getTypes().forEach(typeRepository::save);
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
            throw new ItemDoesNotExistException(String.format("ArtifactResource '%d' does not exist.", id));
        }
        return nodeConverter.toModel(node);
    }

    public List<Artifact> loadAll() {
        Result<ArtifactNode> all = artifactRepository.findAll();
        return stream(all)
                .map(nodeConverter::toModel)
                .collect(toList());
    }
}
