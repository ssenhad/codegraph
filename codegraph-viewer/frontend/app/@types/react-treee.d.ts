declare module '@dnfeitosa/react-treee' {
    import {Node} from '@dnfeitosa/react-treee/models';

    type NodeCallback = (node: Node) => void;

    interface TreeProperties {
        data: Node[] | {};
        onOpenNode: NodeCallback;
        onSelectNode: NodeCallback;
    }

    export class Treee extends React.Component<TreeProperties, any> {}
}
declare module '@dnfeitosa/react-treee/models' {
    export class Node {
        static fromData(value: any) : Node;

        readonly id: string;
        name: string;
        children: boolean | any[];
        parent: Node;
        loading: boolean;
        type: string;
    }
}
