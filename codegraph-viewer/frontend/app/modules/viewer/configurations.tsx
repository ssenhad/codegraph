import * as React from "react";
import {SidebarSection} from "../../components/page/sidebar";

export class Configurations extends React.Component<any, any> {
    render() {
        return (
            <SidebarSection>
                <div className="h6">Configurations</div>
                {this.props.view.dependenciesConfigurations().map((configuration: string, i: number) => (
                    <a href="#" onClick={() => this.props.toggle(configuration)}
                       className={`px-2 mx-1 badge badge-pill badge-color-${i + 1}`}
                       key={configuration}>{configuration}</a>
                ))}
            </SidebarSection>
        );
    }
}
