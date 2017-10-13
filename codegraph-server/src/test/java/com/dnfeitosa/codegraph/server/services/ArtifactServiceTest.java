package com.dnfeitosa.codegraph.server.services;

import com.dnfeitosa.codegraph.core.models.AvailableVersion;
import com.dnfeitosa.codegraph.core.models.Version;
import com.dnfeitosa.codegraph.db.models.converters.ArtifactNodeConverter;
import com.dnfeitosa.codegraph.db.repositories.ArtifactRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Set;

import static com.dnfeitosa.codegraph.core.utils.Arrays.asSet;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ArtifactServiceTest {

    @Mock
    private ArtifactNodeConverter artifactNodeConverter;

    @Mock
    private ArtifactRepository artifactRepository;

    private ArtifactService service;

    @Before
    public void setUp() {
        service = new ArtifactService(artifactNodeConverter, artifactRepository);
    }

    @Test
    public void shouldReturnTheAvailableVersionsOfAnArtifact() {
        when(artifactRepository.getVersions("com.dnfeitosa.codegraph", "codegraph-core")).thenReturn(asSet("1.0", "1.1"));
        Set<AvailableVersion> versions = service.getVersions("com.dnfeitosa.codegraph", "codegraph-core");

        assertThat(versions.size(), is(2));
        assertThat(versions, hasItems(
            new AvailableVersion(new Version("1.0")),
            new AvailableVersion(new Version("1.1"))
        ));
    }

    @Test
    public void shouldNotReturnTheDynamicVersionsFromDependencyDeclarations() {
        when(artifactRepository.getVersions("com.dnfeitosa.codegraph", "codegraph-core")).thenReturn(asSet("1.1", "1.2", "1.+"));
        Set<AvailableVersion> versions = service.getVersions("com.dnfeitosa.codegraph", "codegraph-core");

        assertThat(versions.size(), is(2));
        assertThat(versions, hasItems(
            new AvailableVersion(new Version("1.1")),
            new AvailableVersion(new Version("1.2"))
        ));
    }
}
