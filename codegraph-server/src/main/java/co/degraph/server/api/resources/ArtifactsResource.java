/**
 * Copyright (C) 2015-2018 Diego Feitosa [dnfeitosa@gmail.com]
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
package co.degraph.server.api.resources;

import java.util.List;

@Deprecated
public class ArtifactsResource {

    private List<ArtifactResource> artifacts;

    public ArtifactsResource() {
    }

    public ArtifactsResource(List<ArtifactResource> artifacts) {
        this.artifacts = artifacts;
    }

    public List<ArtifactResource> getArtifacts() {
        return artifacts;
    }
}
