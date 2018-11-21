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
