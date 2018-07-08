/*
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
class Artifact {
    constructor(id, name, organization, version, dependencies) {
        this.id = id;
        this.name = name;
        this.organization = organization;
        this.version = version;
        this.dependencies = dependencies || [];
    }

    addDependency(dependency) {
        this.dependencies.push(dependency);
    }
}

class Dependency {
    constructor(artifact, configurations) {
        this.artifact = artifact;
        this.id = artifact.id;
        this.configurations = configurations;
    }
}

class Node {
    constructor(value) {
        this.value = value;
    }

    get id() {
        if (this.value instanceof Artifact) {
            return `${this.value.id}`;
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

export {
    Artifact,
    Dependency,
    Node,
    Edge,
    Graph
}
