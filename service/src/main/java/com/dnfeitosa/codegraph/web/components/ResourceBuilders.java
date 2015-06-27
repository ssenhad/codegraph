package com.dnfeitosa.codegraph.web.components;

import com.dnfeitosa.codegraph.model.Dependency;
import com.dnfeitosa.codegraph.model.ImpactGraph;
import com.dnfeitosa.codegraph.model.Jar;
import com.dnfeitosa.codegraph.web.resources.ApplicationResource;
import com.dnfeitosa.codegraph.web.resources.DependenciesResource;
import com.dnfeitosa.codegraph.web.resources.ImpactGraphResource;
import com.dnfeitosa.codegraph.web.resources.ImpactResource;
import com.dnfeitosa.codegraph.web.resources.ModuleResource;
import com.dnfeitosa.coollections.Function;
import com.dnfeitosa.codegraph.model.Application;
import com.dnfeitosa.codegraph.model.Dependencies;
import com.dnfeitosa.codegraph.model.Impact;
import com.dnfeitosa.codegraph.model.ImpactZone;
import com.dnfeitosa.codegraph.model.Module;
import com.dnfeitosa.codegraph.web.resources.DependencyResource;
import com.dnfeitosa.codegraph.web.resources.ImpactZoneResource;
import com.dnfeitosa.codegraph.web.resources.JarResource;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static com.dnfeitosa.coollections.Coollections.$;

@Component
public class ResourceBuilders {

	public ApplicationResource toResource(final Application application) {
		return new ApplicationResource() {
			{
				setName(application.getName());
				setModules(getModuleNames(application));
			}
		};
	}

	private List<String> getModuleNames(Application application) {
        return application.getModules()
                .stream()
                .map(Module::getName)
                .sorted()
                .collect(Collectors.toList());
	}

	public ModuleResource toResource(final Module module) {
		return new ModuleResource() {
			{
				setName(module.getName());
				setApplication(module.getApplication().getName());
				setFromOrg(true);
			}
		};
	}

	public ImpactResource toResource(final Impact impact) {
		return new ImpactResource() {
			{
				setImpacted(toResource(impact.getImpacted()));
				setImpacting(toResource(impact.getImpacting()));
			}
		};
	}

	public ImpactZoneResource toResource(final ImpactZone impactZone) {
		return new ImpactZoneResource() {
			{
				setModule(toResource(impactZone.getModule()));
				setDirect(toModuleResources(impactZone.getDirect()));
				setIndirect(toModuleResources(impactZone.getIndirect()));
				setSameApplication(toModuleResources(impactZone.getFromSameApplication()));
			}
		};
	}

	private List<ModuleResource> toModuleResources(List<Impact> impacts) {
		return $(impacts).collect(new Function<Impact, ModuleResource>() {
			@Override
			public ModuleResource apply(Impact input) {
				return toResource(input.getImpacted());
			}
		});
	}

	private List<ImpactResource> toResources(List<Impact> impacts) {
		return $(impacts).collect(impactToResource());
	}

	private Function<Impact, ImpactResource> impactToResource() {
		return new Function<Impact, ImpactResource>() {
			@Override
			public ImpactResource apply(Impact input) {
				return toResource(input);
			}
		};
	}

	public ImpactGraphResource toResource(final ImpactGraph impactGraph) {
		return new ImpactGraphResource() {
			{
				setNodes(toResources(impactGraph.getNodes()));
				setPaths(toResources(impactGraph.getPaths()));
			}
		};
	}

	private List<ModuleResource> toResources(Collection<Module> nodes) {
		return $(new ArrayList<Module>(nodes)).collect(moduleToResource());
	}

	private Function<Module, ModuleResource> moduleToResource() {
		return new Function<Module, ModuleResource>() {
			@Override
			public ModuleResource apply(Module input) {
				return toResource(input);
			}
		};
	}

	public DependenciesResource toResource(final Dependencies dependencies) {
		return new DependenciesResource() {
			{
				setModule(toResource(dependencies.getModule()));
				setDirect(toDependencyResources(dependencies.getDirect()));
				setTransitive(toDependencyResources(dependencies.getTransitive()));
			}
		};
	}

	private List<DependencyResource> toDependencyResources(List<Dependency> dependencies) {
		return $(dependencies).collect(dependencyToResource());
	}

	private Function<Dependency, DependencyResource> dependencyToResource() {
		return new Function<Dependency, DependencyResource>() {
			@Override
			public DependencyResource apply(Dependency input) {
				return toResource(input);
			}
		};
	}

	private DependencyResource toResource(final Dependency dependency) {
		return new DependencyResource() {
			{
				setDependent(toResource(dependency.getDependent()));
				setDependency(toResource(dependency.getJar()));
			}
		};
	}

	private JarResource toResource(final Jar jar) {
		return new JarResource() {
			{
				setName(jar.getName());
				setVersion(jar.getVersion());
				setFromOrg(jar.fromOrg());
			}
		};
	}
}
