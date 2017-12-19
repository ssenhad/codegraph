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
package com.dnfeitosa.codegraph.core.models;

import java.util.HashMap;
import java.util.Map;

public class Artifacts {

    private final Map<String, Artifact> artifacts = new HashMap<>();

    public Artifact artifact(String organization, String name, Version version) {
        String id = Artifact.id(organization, name, version);
        if (!artifacts.containsKey(id)) {
            Artifact artifact = new Artifact(organization, name, version);
            artifacts.put(id, artifact);
            return artifact;
        }
        return artifacts.get(id);
    }

    public void clear() {
        artifacts.clear();
    }
}
