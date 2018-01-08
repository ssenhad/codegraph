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
package co.degraph.server.api.resources;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Set;

public class DependencyResource {

    private final String organization;
    private final String name;
    private final String version;
    private final Set<String> configurations;

    @JsonCreator
    public DependencyResource(@JsonProperty("organization") String organization,
                              @JsonProperty("name") String name,
                              @JsonProperty("version") String version,
                              @JsonProperty("configurations") Set<String> configurations) {
        this.organization = organization;
        this.name = name;
        this.version = version;
        this.configurations = configurations;
    }

    public String getOrganization() {
        return organization;
    }

    public String getName() {
        return name;
    }

    public String getVersion() {
        return version;
    }

    public Set<String> getConfigurations() {
        return configurations;
    }

    @Override
    public String toString() {
        return String.format("%s:%s:%s(%s)", organization, name, version, configurations);
    }
}
