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
package co.degraph.server.acceptance.api.controllers;

import co.degraph.server.api.resources.ArtifactResource;
import co.degraph.server.api.resources.DependencyResource;
import org.apache.commons.collections4.Predicate;
import org.hamcrest.CoreMatchers;

import java.util.Set;

import static org.apache.commons.collections4.IterableUtils.find;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

public class ArtifactResourceChecks {

    private ArtifactResource artifactResource;

    public ArtifactResourceChecks(ArtifactResource artifactResource) {
        this.artifactResource = artifactResource;
    }

    public ArtifactResourceChecks is(String organization, String name, String version) {
        ArtifactResource resource = getValue();
        assertThat(resource.getOrganization(), CoreMatchers.is(organization));
        assertThat(resource.getName(), CoreMatchers.is(name));
        assertThat(resource.getVersion(), CoreMatchers.is(version));
        return this;
    }

    public ArtifactResourceChecks hasDependencies(int count) {
        assertThat(getValue().getDependencies().size(), CoreMatchers.is(count));
        return this;
    }

    public ArtifactResourceChecks hasDependency(String organization, String name, String version, Set<String> configurations) {
        Predicate<DependencyResource> byProperties = d
            -> organization.equals(d.getOrganization())
            && name.equals(d.getName())
            && version.equals(d.getVersion());

        DependencyResource dependency = find(getValue().getDependencies(), byProperties);
        assertNotNull(dependency);
        assertThat(dependency.getOrganization(), CoreMatchers.is(organization));
        assertThat(dependency.getName(), CoreMatchers.is(name));
        assertThat(dependency.getVersion(), CoreMatchers.is(version));
        assertThat(dependency.getConfigurations(), CoreMatchers.is(configurations));
        return this;
    }

    private ArtifactResource getValue() {
        assertNotNull(artifactResource);
        return artifactResource;
    }
}
