import {Graph} from "../../models/graph";
import {Artifact} from "../../models/core";
import * as _ from "lodash";

export class ArtifactView {
    graph: Graph;
    artifact: Artifact;

    constructor(graph: Graph, artifact: Artifact) {
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
    hideNodes(predicate) {
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
