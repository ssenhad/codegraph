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
package com.dnfeitosa.codegraph.server.api.controllers;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class EdgeResource {

    private final Map<String, Object> attributes = new HashMap<>();
    private final NodeReference start;
    private final NodeReference end;

    public EdgeResource(NodeReference start, NodeReference end, Set<String> configurations) {
        this.start = start;
        this.end = end;
        this.attributes.put("configurations", configurations);
    }

    public NodeReference getEnd() {
        return end;
    }

    public NodeReference getStart() {
        return start;
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }
}
