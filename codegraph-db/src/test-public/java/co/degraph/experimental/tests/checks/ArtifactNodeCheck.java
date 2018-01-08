/**
 * Copyright (C) 2015-2017 Diego Feitosa [dnfeitosa@gmail.com]
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package co.degraph.experimental.tests.checks;

import co.degraph.db.models.ArtifactNode;
import co.degraph.db.models.relationships.DeclaresRelationship;
import co.degraph.db.repositories.ArtifactRepository;

import java.util.Set;

import static java.lang.String.format;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class ArtifactNodeCheck {

    private final ArtifactRepository artifactRepository;
    private final ArtifactNode artifactSpec;
    private ArtifactNode dbArtifact;

    public ArtifactNodeCheck(ArtifactRepository artifactRepository, ArtifactNode artifactSpec) {
        this.artifactRepository = artifactRepository;
        this.artifactSpec = artifactSpec;
    }

    public ArtifactNodeCheck exists() {
        ArtifactNode node = fetch();

        assertNotNull(format("Artifact '%s' not present in database", artifactSpec.getId()), node);
        assertThat(node.getOrganization(), is(artifactSpec.getOrganization()));
        assertThat(node.getName(), is(artifactSpec.getName()));
        assertThat(node.getVersion(), is(artifactSpec.getVersion()));
        return this;
    }

    public ArtifactNodeCheck hasDependency(String organization, String name, String version, Set<String> configurations) {
        ArtifactNode node = fetch();

        assertThat(node.getDeclaredDependencies(), hasItem(
            new DeclaresRelationship(node, new ArtifactNode(organization, name, version), configurations)
        ));

        return this;
    }

    private ArtifactNode fetch() {
        if (dbArtifact == null) {
            dbArtifact = artifactRepository.load(artifactSpec.getOrganization(), artifactSpec.getName(), artifactSpec.getVersion());
        }
        return dbArtifact;
    }

    public ArtifactNodeCheck hasNoDependencies() {
        assertTrue(fetch().getDeclaredDependencies().isEmpty());
        return this;
    }

    public ArtifactNodeCheck hasDependencies(int count) {
        Set<DeclaresRelationship> dependencies = fetch().getDeclaredDependencies();
        assertThat(dependencies.toString(), dependencies.size(), is(count));
        return this;
    }
}
