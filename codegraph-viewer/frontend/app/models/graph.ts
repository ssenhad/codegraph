///
/// Copyright (C) 2015-2018 Diego Feitosa [dnfeitosa@gmail.com]
///
/// This program is free software: you can redistribute it and/or modify
/// it under the terms of the GNU Affero General Public License as published by
/// the Free Software Foundation, either version 3 of the License, or
/// (at your option) any later version.
///
/// This program is distributed in the hope that it will be useful,
/// but WITHOUT ANY WARRANTY; without even the implied warranty of
/// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
/// GNU Affero General Public License for more details.
///
/// You should have received a copy of the GNU Affero General Public License
/// along with this program.  If not, see <http://www.gnu.org/licenses/>.
///

import {Artifact, Dependency} from "./core";

export class Graph {

    readonly nodes: Node<any>[];
    readonly edges: Array<Edge>;

    constructor(nodes: Node<any>[], edges: Edge[]) {
        this.nodes = nodes;
        this.edges = edges;
    }

    static fromData(data: any): Graph {
        return new Graph(data.nodes, data.edges);
    };
}

export class Node<T> {

    private value: T;

    constructor(value: T) {
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

export class Edge {

    readonly source: string;
    readonly target: string;
    readonly id: string;
    readonly attributes: { configurations: Array<string> };

    constructor(source: Node<any>, target: Node<any>) {
        this.id = `${source.id}:${target.id}`;
        this.source = source.id;
        this.target = target.id;
    }
}
