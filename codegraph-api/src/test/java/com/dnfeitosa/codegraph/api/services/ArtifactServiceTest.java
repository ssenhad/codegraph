package com.dnfeitosa.codegraph.api.services;

import com.dnfeitosa.codegraph.core.models.Artifact;
import com.dnfeitosa.codegraph.db.nodes.ArtifactNode;
import com.dnfeitosa.codegraph.db.nodes.converters.ArtifactNodeConverter;
import com.dnfeitosa.codegraph.db.repositories.ArtifactRepository;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ArtifactServiceTest {

    private final long newId = 10;
    private final ArtifactNode node = new ArtifactNode();

    private ArtifactService artifactService;
    private ArtifactRepository repository;
    private ArtifactNodeConverter converter;

    @Before
    public void setUp() {
        converter = mock(ArtifactNodeConverter.class);
        when(converter.toNode(any(Artifact.class))).thenReturn(node);

        repository = mock(ArtifactRepository.class);
        ArtifactNode newNode = new ArtifactNode(null, null, null, null, null, null);
        when(repository.save(node)).thenReturn(newNode);

        when(converter.toModel(newNode)).thenReturn(new Artifact(newId, null, null, null, null, null));

        artifactService = new ArtifactService(converter, repository);
    }

    @Test
    public void shouldConvertAnArtifactToNodeAndSaveIt() {
        Artifact savedArtifact = artifactService.addArtifact(new Artifact(null, null, null, null, null));

        assertThat(savedArtifact.getId(), is(newId));
    }

    @Test(expected = ItemDoesNotExistException.class)
    public void shouldBeEmptyWhenArtifactDoesNotExist() {
        artifactService.loadArtifact(999L);
    }
}

