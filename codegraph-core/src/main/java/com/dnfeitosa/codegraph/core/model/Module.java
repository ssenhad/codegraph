package com.dnfeitosa.codegraph.core.model;

import com.dnfeitosa.coollections.Filter;

import java.util.List;
import java.util.Set;

import static com.dnfeitosa.coollections.Coollections.$;
import static java.lang.String.format;

public class Module {

	private final String name;
    private final String organization;
    private final List<Jar> dependencies;
	private final Set<ArtifactType> exportTypes;
	private Application application;

    public Module(String name, String organization, List<Jar> dependencies, Set<ArtifactType> exportTypes) {
		this.name = name;
        this.organization = organization;
        this.dependencies = dependencies;
		this.exportTypes = exportTypes;
	}

    @Deprecated
	public Application getApplication() {
		return application;
	}

	public String getName() {
		return this.name;
	}

    public String getOrganization() {
        return organization;
    }

	public List<Jar> getDependencies() {
		return dependencies;
	}

    public List<Jar> getDependencies(Filter<Jar> filter) {
        return $(dependencies).filter(filter);
    }

    public Set<ArtifactType> getExportTypes() {
		return exportTypes;
	}

	public boolean dependsOn(final String dependencyName) {
		return findDependency(dependencyName) != null;
	}

	public boolean exports(ArtifactType artifactType) {
		return exportTypes.contains(artifactType);
	}

	@Override
	public String toString() {
		return format("Module{name: '%s', application: '%s'}", name, application.getName());
	}

    @Deprecated
	public void setApplication(Application application) {
		this.application = application;
	}

	private Jar findDependency(final String dependencyName) {
		return $(getDependencies()).find(dependency -> dependency.getName().equals(dependencyName));
	}
}
