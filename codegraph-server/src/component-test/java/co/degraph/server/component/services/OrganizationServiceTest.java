package co.degraph.server.component.services;

import co.degraph.server.ComponentTestBase;
import co.degraph.db.models.ArtifactNode;
import co.degraph.server.services.Organization;
import co.degraph.server.services.OrganizationService;
import org.junit.Test;
import org.neo4j.ogm.session.Session;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class OrganizationServiceTest extends ComponentTestBase {

    @Autowired
    private Session session;

    @Autowired
    private OrganizationService service;

    @Test
    public void shouldReturnAllRootOrganizationsWhenParentIsNull() {
        session.save(new ArtifactNode("co.degraph", "codegraph-core", "1.0"));
        session.save(new ArtifactNode("aopalliance", "aopalliance", "1.0"));
        session.save(new ArtifactNode("org.springframework", "spring-aop", "4.2.7.RELEASE"));
        session.save(new ArtifactNode("org.slf4j", "slf4j-api", "1.7.21"));

        Set<Organization> organizations = service.getOrganizations(null);

        assertThat(organizations.size(), is(3));
        assertThat(organizations, hasItems(
            new Organization(null, "aopalliance"),
            new Organization(null, "co"),
            new Organization(null, "org")
        ));
    }

    @Test
    public void shouldReturnAllTheOrganizationsForAGivenParent() {
        session.save(new ArtifactNode("co.degraph", "codegraph-core", "1.0"));
        session.save(new ArtifactNode("aopalliance", "aopalliance", "1.0"));
        session.save(new ArtifactNode("org.springframework", "spring-aop", "4.2.7.RELEASE"));
        session.save(new ArtifactNode("org.springframework.boot", "spring-boot", "1.3.6.RELEASE"));
        session.save(new ArtifactNode("org.slf4j", "slf4j-api", "1.7.21"));

        Set<Organization> organizations = service.getOrganizations("org");

        assertThat(organizations.size(), is(2));
        assertThat(organizations, hasItems(
            new Organization("org", "springframework"),
            new Organization("org", "slf4j")
        ));
    }

    @Test
    public void shouldReturnAllTheOrganizationsWithExactMatchForParentName() {
        session.save(new ArtifactNode("co.degraph", "codegraph-core", "1.0"));
        session.save(new ArtifactNode("com.fasterxml.jackson.core", "jackson-core", "2.6.7"));

        Set<Organization> organizations = service.getOrganizations("co");

        assertThat(organizations.size(), is(1));
        assertThat(organizations, hasItems(
            new Organization("co", "degraph")
        ));
    }
}
