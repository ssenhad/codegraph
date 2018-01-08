package co.degraph.server.services;

import co.degraph.core.models.AvailableVersion;
import co.degraph.core.models.Version;
import co.degraph.db.models.converters.ArtifactNodeConverter;
import co.degraph.db.repositories.ArtifactRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Set;

import static org.apache.shiro.util.CollectionUtils.asSet;
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
        when(artifactRepository.getVersions("co.degraph", "codegraph-core")).thenReturn(asSet("1.0", "1.1"));
        Set<AvailableVersion> versions = service.getVersions("co.degraph", "codegraph-core");

        assertThat(versions.size(), is(2));
        assertThat(versions, hasItems(
            new AvailableVersion(new Version("1.0")),
            new AvailableVersion(new Version("1.1"))
        ));
    }

    @Test
    public void shouldNotReturnTheDynamicVersionsFromDependencyDeclarations() {
        when(artifactRepository.getVersions("co.degraph", "codegraph-core")).thenReturn(asSet("1.1", "1.2", "1.+"));
        Set<AvailableVersion> versions = service.getVersions("co.degraph", "codegraph-core");

        assertThat(versions.size(), is(2));
        assertThat(versions, hasItems(
            new AvailableVersion(new Version("1.1")),
            new AvailableVersion(new Version("1.2"))
        ));
    }
}
