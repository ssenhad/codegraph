package com.dnfeitosa.codegraph.web.components;

import com.dnfeitosa.codegraph.core.model.Dependency;
import com.dnfeitosa.codegraph.core.model.ImpactGraph;
import com.dnfeitosa.codegraph.core.model.Jar;
import com.dnfeitosa.codegraph.web.resources.ApplicationResource;
import com.dnfeitosa.codegraph.web.resources.DependenciesResource;
import com.dnfeitosa.codegraph.web.resources.ImpactGraphResource;
import com.dnfeitosa.codegraph.web.resources.ImpactResource;
import com.dnfeitosa.codegraph.web.resources.ModuleResource;
import com.dnfeitosa.coollections.Function;
import com.dnfeitosa.codegraph.core.model.Application;
import com.dnfeitosa.codegraph.core.model.Dependencies;
import com.dnfeitosa.codegraph.core.model.Impact;
import com.dnfeitosa.codegraph.core.model.ImpactZone;
import com.dnfeitosa.codegraph.core.model.Module;
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
        ApplicationResource resource = new ApplicationResource(application.getName());
        resource.setModules(getModuleNames(application));
        return resource;
	}

	private List<ModuleResource> getModuleNames(Application application) {
        return application.getModules()
                .stream()
                .map(module -> new ModuleResource(new ApplicationResource(application.getName()), module.getName()))
//                .sorted((m1, m2) -> m1.getName().compareTo(m2.getName()))
                .collect(Collectors.toList());
	}

	public ModuleResource toResource(final Module module) {
        ApplicationResource application = new ApplicationResource(module.getApplication().getName());
        return new ModuleResource(application, module.getName());
	}

	public ImpactResource toResource(final Impact impact) {
        ImpactResource impactResource = new ImpactResource();
        impactResource.setImpacted(toResource(impact.getImpacted()));
        impactResource.setImpacting(toResource(impact.getImpacting()));
        return impactResource;
	}

	public ImpactZoneResource toResource(final ImpactZone impactZone) {
        ImpactZoneResource impactZoneResource = new ImpactZoneResource();
        impactZoneResource.setModule(toResource(impactZone.getModule()));
        impactZoneResource.setDirect(toModuleResources(impactZone.getDirect()));
        impactZoneResource.setIndirect(toModuleResources(impactZone.getIndirect()));
        impactZoneResource.setSameApplication(toModuleResources(impactZone.getFromSameApplication()));
        return impactZoneResource;
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
