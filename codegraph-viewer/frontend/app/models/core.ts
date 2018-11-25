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

export interface ArtifactIdentity {
    organization: string;
    name: string;
    version: string;
}

export class Artifact {

    readonly id: string;
    readonly name: string;
    readonly organization: string;
    readonly version: Version;
    readonly dependencies: Dependency[];

    constructor(name: string, organization: string, version: Version, dependencies: Dependency[]) {
        this.id = `${organization}:${name}:${version}`;
        this.name = name;
        this.organization = organization;
        this.version = version;
        this.dependencies = dependencies || [];
    }

    addDependency(dependency: Dependency) {
        this.dependencies.push(dependency);
    }
}

export class Version {
    value: string;

    constructor(version: string) {
        this.value = version;
    }

    toString(): string {
        return this.value;
    }
}

export class Dependency {

    readonly artifact: Artifact;
    readonly configurations: string[];

    constructor(artifact: Artifact, configurations: string[]) {
        this.artifact = artifact;
        this.configurations = configurations;
    }
}
