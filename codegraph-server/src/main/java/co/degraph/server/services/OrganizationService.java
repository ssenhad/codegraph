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
package co.degraph.server.services;

import co.degraph.db.repositories.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

import static java.util.stream.Collectors.toSet;
import static org.apache.commons.lang3.StringUtils.isEmpty;

@Service
public class OrganizationService {

    private final OrganizationRepository organizationRepository;

    @Autowired
    public OrganizationService(OrganizationRepository organizationRepository) {
        this.organizationRepository = organizationRepository;
    }

    public Set<Organization> getOrganizations(String parent) {
        if (isEmpty(parent)) {
            return organizationRepository.getAllOrganizations().stream()
                .map(org -> {
                    String[] names = org.split("\\.");
                    return new Organization(null, names.length > 1 ? names[0] : org);
                }).collect(toSet());
        }
        return organizationRepository.getOrganizations(parent).stream()
            .map(org -> {
                int beginIndex = parent.length() + 1;
                int endIndex = org.indexOf('.', beginIndex);
                String name = endIndex != -1
                    ? org.substring(beginIndex, endIndex)
                    : org.substring(beginIndex);
                return new Organization(parent, name);
            }).collect(toSet());
    }
}
