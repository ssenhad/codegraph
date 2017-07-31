/*
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
class Artifact {
    constructor(name, organization, version, dependencies) {
        this.name = name;
        this.organization = organization;
        this.version = version;
        this.dependencies = dependencies || [];
    }
}

class Dependency {
    constructor(artifact, configurations, resolvedVersion) {
        this.artifact = artifact;
        this.configurations = configurations;
        this.resolvedVersion = resolvedVersion;
    }
}

class Node {
    constructor(value) {
        this.value = value;
    }

    get id() {
        if (this.value instanceof Artifact) {
            return `${this.value.organization}:${this.value.name}`;
        }
        if (this.value instanceof Dependency) {
            return `${this.value.artifact.organization}:${this.value.artifact.name}`;
        }
        throw "Not supposed to come here";
    }

    get label() {
        if (this.value instanceof Artifact) {
            return this.value.name;
        }
        if (this.value instanceof Dependency) {
            let artifact = this.value.artifact;
            return `${artifact.organization}:${artifact.name}`;
        }
        return 'n/a';
    }
}


class Edge {
    constructor(source, target) {
        this.source = source.id;
        this.target = target.id;
        this.id = `${source.id}:${target.id}`;
    }
}

class Graph {
    constructor(nodes, edges) {
        this.nodes = nodes;
        this.edges = edges;
    }
}




