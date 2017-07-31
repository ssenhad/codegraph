package com.dnfeitosa.codegraph.server.services;

import com.dnfeitosa.codegraph.core.models.AvailableVersion;
import com.dnfeitosa.codegraph.core.models.Version;
import com.dnfeitosa.codegraph.db.models.converters.ArtifactNodeConverter;
import com.dnfeitosa.codegraph.db.repositories.ArtifactRepository;
import com.dnfeitosa.codegraph.db.repositories.DependencyRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Set;

import static com.dnfeitosa.codegraph.core.models.AvailableVersion.Availability.ARTIFACT;
import static com.dnfeitosa.codegraph.core.models.AvailableVersion.Availability.DEPENDENCY;
import static com.dnfeitosa.codegraph.core.utils.Arrays.asSet;
import static java.util.Collections.emptySet;
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

    @Mock
    private DependencyRepository dependencyRepository;

    private ArtifactService service;

    @Before
    public void setUp() {
        service = new ArtifactService(artifactNodeConverter, artifactRepository, dependencyRepository);
    }

    @Test
    public void shouldReturnTheAvailableVersionsWithTheInformationWhereSuchVersionAppears() {
        when(artifactRepository.getVersions("com.dnfeitosa.codegraph", "codegraph-core")).thenReturn(asSet("1.0", "1.1"));
        when(dependencyRepository.getVersions("com.dnfeitosa.codegraph", "codegraph-core")).thenReturn(asSet("1.1", "1.2"));
        Set<AvailableVersion> versions = service.getVersions("com.dnfeitosa.codegraph", "codegraph-core");

        assertThat(versions.size(), is(3));
        assertThat(versions, hasItems(
            new AvailableVersion(new Version("1.0"), ARTIFACT),
            new AvailableVersion(new Version("1.1"), ARTIFACT, DEPENDENCY),
            new AvailableVersion(new Version("1.2"), DEPENDENCY)
        ));
    }

    @Test
    public void shouldNotReturnTheDynamicVersionsFromDependencyDeclarations() {
        when(artifactRepository.getVersions("com.dnfeitosa.codegraph", "codegraph-core")).thenReturn(emptySet());
        when(dependencyRepository.getVersions("com.dnfeitosa.codegraph", "codegraph-core")).thenReturn(asSet("1.1", "1.2", "1.+"));
        Set<AvailableVersion> versions = service.getVersions("com.dnfeitosa.codegraph", "codegraph-core");

        assertThat(versions.size(), is(2));
        assertThat(versions, hasItems(
            new AvailableVersion(new Version("1.1"), DEPENDENCY),
            new AvailableVersion(new Version("1.2"), DEPENDENCY)
        ));
    }
}
