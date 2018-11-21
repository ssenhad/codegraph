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
