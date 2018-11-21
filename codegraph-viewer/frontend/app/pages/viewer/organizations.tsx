import * as React from "react";
import {SidebarSection} from "../../components/page/sidebar";
import {ArtifactView} from "./artifactView";

export class Organizations extends React.Component<{ view: ArtifactView }, {}> {
    render() {
        const {view} = this.props;

        return (
            <SidebarSection>
                <div className="h6">Organizations</div>
                {view.organizations().map((organization: string) => (
                    <div key={organization}><i className="fa fa-eye"/>{organization}</div>
                ))}
            </SidebarSection>
        );
    }
}
