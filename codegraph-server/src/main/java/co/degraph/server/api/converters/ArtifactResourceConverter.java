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
package co.degraph.server.api.converters;

import co.degraph.core.models.Artifact;
import co.degraph.core.models.Artifacts;
import co.degraph.core.models.AvailableVersion;
import co.degraph.core.models.Version;
import co.degraph.server.api.resources.ArtifactResource;
import co.degraph.server.api.resources.ArtifactVersions;
import co.degraph.server.api.resources.AvailableVersionResource;
import co.degraph.server.api.resources.DependencyResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.inject.Provider;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

@Component
public class ArtifactResourceConverter {

    private Provider<Artifacts> artifacts;

    @Autowired
    public ArtifactResourceConverter(Provider<Artifacts> artifacts) {
        this.artifacts = artifacts;
    }

    public Artifact toModel(ArtifactResource resource) {
        String name = resource.getName();
        String organization = resource.getOrganization();
        Version version = new Version(resource.getVersion());

        Artifact artifact = artifacts.get().artifact(organization, name, version);
        resource.getDependencies().forEach(dependency -> artifact.addDependency(toModel(dependency), dependency.getConfigurations()));
        return artifact;
    }

    private Artifact toModel(DependencyResource dependency) {
        Version version = new Version(dependency.getVersion());
        return artifacts.get().artifact(dependency.getOrganization(), dependency.getName(), version);
    }

    public ArtifactResource toResource(Artifact artifact) {
        List<DependencyResource> dependencies = artifact.getDependencies()
            .stream()
            .map(this::toResource)
            .collect(toList());

        return new ArtifactResource(
            artifact.getOrganization(),
            artifact.getName(),
            artifact.getVersion().getNumber(),
            dependencies);
    }

    private DependencyResource toResource(co.degraph.core.models.Dependency dependency) {
        String organization = dependency.getOrganization();
        String name = dependency.getName();
        String version = dependency.getVersion().getNumber();
        Set<String> configurations = dependency.getConfigurations();
        return new DependencyResource(organization, name, version, configurations);
    }

    public ArtifactVersions toResource(String organization, String name, Set<AvailableVersion> versions) {
        ArtifactResource artifactResource = new ArtifactResource(organization, name, null, Collections.emptyList());
        Set<AvailableVersionResource> availableVersion = versions.stream()
            .map(av -> toResource(av))
            .collect(toSet());
        return new ArtifactVersions(artifactResource, availableVersion);
    }

    private AvailableVersionResource toResource(AvailableVersion version) {
        return new AvailableVersionResource(version.getVersion().getNumber());
    }
}
