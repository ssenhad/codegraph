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




