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
package com.dnfeitosa.codegraph.server.acceptance;

import com.dnfeitosa.codegraph.db.models.ArtifactNode;
import com.dnfeitosa.codegraph.db.models.relationships.DeclaresRelationship;
import com.dnfeitosa.codegraph.db.repositories.ArtifactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

import static java.lang.String.format;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@Component
public class PersistenceAssertions {

    private ArtifactRepository artifactRepository;

    @Autowired
    public PersistenceAssertions(ArtifactRepository artifactRepository) {
        this.artifactRepository = artifactRepository;
    }

    public void hasArtifact(String organization, String name, String version) {
        new ArtifactVerification(artifactRepository, new ArtifactNode(organization, name, version)).exists();
    }

    public ArtifactVerification artifact(String organization, String name, String version) {
        return new ArtifactVerification(artifactRepository, new ArtifactNode(organization, name, version));
    }

    public static class ArtifactVerification {

        private final ArtifactRepository artifactRepository;
        private ArtifactNode artifactSpec;
        private ArtifactNode dbArtifact;

        public ArtifactVerification(ArtifactRepository artifactRepository, ArtifactNode artifactSpec) {
            this.artifactRepository = artifactRepository;
            this.artifactSpec = artifactSpec;
        }

        public ArtifactVerification exists() {
            ArtifactNode node = fetch();

            assertNotNull(format("Artifact '%s' not present in database", artifactSpec.getId()), node);
            assertThat(node.getOrganization(), is(artifactSpec.getOrganization()));
            assertThat(node.getName(), is(artifactSpec.getName()));
            assertThat(node.getVersion(), is(artifactSpec.getVersion()));
            return this;
        }

        public ArtifactVerification hasDependency(String organization, String name, String version, Set<String> configurations) {
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

        public void hasNoDependencies() {
            assertTrue(fetch().getDependencies().isEmpty());
        }
    }
}
