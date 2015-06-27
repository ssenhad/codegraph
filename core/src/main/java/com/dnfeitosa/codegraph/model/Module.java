package com.dnfeitosa.codegraph.model;

import com.dnfeitosa.coollections.Filter;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import static com.dnfeitosa.coollections.Coollections.$;
import static java.lang.String.format;

public class Module {

	private final String name;
	private final String location;
	private final List<Jar> dependencies;
	private final Set<ArtifactType> exportTypes;
	private Application application;

	public Module(String name) {
		this(name, null, Collections.<Jar> emptyList(), Collections.<ArtifactType> emptySet());
	}

	public Module(String name, String location, List<Jar> dependencies, Set<ArtifactType> exportTypes) {
		this.name = name;
		this.location = location;
		this.dependencies = dependencies;
		this.exportTypes = exportTypes;
	}

	public Application getApplication() {
		return application;
	}

	public String getName() {
		return this.name;
	}

	public List<Jar> getDependencies() {
		return dependencies;
	}

    public List<Jar> getDependencies(Filter<Jar> filter) {
        return $(dependencies).filter(filter);
    }

	public String getLocation() {
		return location;
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
		return format("Module{name='%s', application='%s'}", name, application.getName());
	}

	void setApplication(Application application) {
		this.application = application;
	}

	private Jar findDependency(final String dependencyName) {
		return $(getDependencies()).find(dependency -> dependency.getName().equals(dependencyName));
	}
}
