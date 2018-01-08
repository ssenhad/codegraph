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

import co.degraph.core.models.Artifact;
import co.degraph.core.models.Dependency;
import co.degraph.core.models.Version;

import java.util.Set;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

public class ArtifactCheck {

    private final Artifact artifact;

    public ArtifactCheck(Artifact artifact) {
        this.artifact = artifact;
    }

    public ArtifactCheck hasDependency(String organization, String name, String version, Set<String> configurations) {
        assertThat(artifact.getDependencies(), hasItem(
            new Dependency(artifact, new Artifact(organization, name, new Version(version)), configurations)
        ));
        return this;
    }

    public ArtifactCheck exists() {
        assertNotNull("Artifact is null", artifact);
        return this;
    }

    public ArtifactCheck hasDependencies(int count) {
        assertThat(artifact.getDependencies().size(), is(count));
        return this;
    }
}
