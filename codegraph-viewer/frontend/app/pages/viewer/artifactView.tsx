import {Graph} from "../../models/graph";
import {Artifact, ArtifactIdentity} from "../../models/core";
import * as _ from "lodash";

export class ArtifactView {
    graph: Graph;
    artifact?: ArtifactIdentity;

    constructor(graph: Graph, artifact?: ArtifactIdentity) {
        this.graph = graph;
        this.artifact = artifact;
    }

    dependenciesConfigurations() {
        return _(this.graph.edges)
            .flatMap((edge) => edge.attributes.configurations)
            .uniq()
            .value();
    }

    organizations() {
        return _(this.graph.nodes)
        // @ts-ignore
            .flatMap((node) => node.organization)
            .value();
    }

    // @ts-ignore
    hideNodes(predicate: (node: Node<any>) => boolean) {
        this.graph.nodes.forEach((node) => {
            if (predicate(node)) {
                // @ts-ignore
                node.hidden = true;
            }
        });
    }

    // @ts-ignore
    hideEdges(predicate) {
        this.graph.edges.forEach((edge) => {
            if (predicate(edge)) {
                // @ts-ignore
                edge.hidden = true;
            }
        });
    }
}
