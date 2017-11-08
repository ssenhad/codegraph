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
package com.dnfeitosa.codegraph.server.acceptance.api.controllers;

import com.dnfeitosa.codegraph.server.api.controllers.ArtifactNodeResource;
import com.dnfeitosa.codegraph.server.api.controllers.NodeResource;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class NodeResourceChecks {

    private NodeResource node;

    public NodeResourceChecks(NodeResource node) {
        this.node = node;
    }

    public NodeResourceChecks isArtifact(String organization, String name, String version) {
        assertThat("Not an artifact node resource", getValue(), instanceOf(ArtifactNodeResource.class));

        ArtifactNodeResource artifactNode = asArtifact();
        assertThat(artifactNode.getOrganization(), is(organization));
        assertThat(artifactNode.getName(), is(name));
        assertThat(artifactNode.getVersion(), is(version));
        return this;
    }

    private ArtifactNodeResource asArtifact() {
        return (ArtifactNodeResource) getValue();
    }

    private NodeResource getValue() {
        return node;
    }
}
