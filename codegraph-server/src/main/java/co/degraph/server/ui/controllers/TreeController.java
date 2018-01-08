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
package co.degraph.server.ui.controllers;

import co.degraph.core.models.Artifact;
import co.degraph.server.services.ArtifactService;
import co.degraph.server.services.Organization;
import co.degraph.server.services.OrganizationService;
import co.degraph.server.ui.resources.TreeNode;
import co.degraph.server.ui.resources.TreeNodes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toSet;
import static org.apache.commons.collections4.CollectionUtils.collate;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
public class TreeController {

    private final ArtifactService artifactService;
    private final OrganizationService organizationService;

    @Autowired
    public TreeController(ArtifactService artifactService, OrganizationService organizationService) {
        this.artifactService = artifactService;
        this.organizationService = organizationService;
    }

    @RequestMapping(path = "/ui/tree/nodes", method = GET)
    public ResponseEntity<TreeNodes> getTreeNodes(
        @RequestParam(value = "parent", required = false) String parent) {

        Set<Organization> organizations = organizationService.getOrganizations(parent);
        Set<Artifact> artifacts = artifactService.getArtifactsFromOrganization(parent);

        List<TreeNode> allNodes = collate(
                organiztionToNode(organizations),
                artifactToNode(artifacts),
                false);

        return new ResponseEntity<>(new TreeNodes(new HashSet<>(allNodes)), OK);
    }

    private Collection<TreeNode> artifactToNode(Set<Artifact> artifacts) {
        return artifacts.stream()
            .map(artifact -> new TreeNode(artifact.getOrganization(), artifact.getName(), "artifact"))
            .collect(toSet());
    }

    private Collection<TreeNode> organiztionToNode(Set<Organization> organizations) {
        return organizations.stream()
            .map(organization -> new TreeNode(organization.getParent(), organization.getName(), "organization"))
            .collect(toSet());
    }
}
