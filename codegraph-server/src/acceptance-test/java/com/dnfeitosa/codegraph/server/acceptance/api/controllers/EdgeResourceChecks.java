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

import com.dnfeitosa.codegraph.server.api.controllers.EdgeResource;

import static org.hamcrest.Matchers.hasEntry;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

public class EdgeResourceChecks {
    private EdgeResource resource;

    public EdgeResourceChecks(EdgeResource resource) {
        this.resource = resource;
    }

    public EdgeResourceChecks hasAttribute(String name, Object value) {
        assertThat(getValue().getAttributes(), hasEntry(name, value));
        return this;
    }

    private EdgeResource getValue() {
        assertNotNull("EdgeResource is null", resource);
        return resource;
    }
}
