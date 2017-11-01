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
package com.dnfeitosa.codegraph.experimental.tests.checks;

import com.dnfeitosa.codegraph.db.models.ArtifactNode;
import com.dnfeitosa.codegraph.db.repositories.ArtifactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StorageChecks {

    private ArtifactRepository artifactRepository;

    @Autowired
    public StorageChecks(ArtifactRepository artifactRepository) {
        this.artifactRepository = artifactRepository;
    }

    public void hasArtifact(String organization, String name, String version) {
        new ArtifactNodeCheck(artifactRepository, new ArtifactNode(organization, name, version)).exists();
    }

    public ArtifactNodeCheck artifact(String organization, String name, String version) {
        return new ArtifactNodeCheck(artifactRepository, new ArtifactNode(organization, name, version));
    }
}
