package com.dnfeitosa.codegraph.loaders;

import com.dnfeitosa.coollections.Function;
import com.dnfeitosa.codegraph.loaders.finders.ApplicationsFinder;
import com.dnfeitosa.codegraph.model.Application;

import java.util.List;

import static com.dnfeitosa.coollections.Coollections.$;

public class ApplicationsLoader {

	private ApplicationsFinder finder;
	private ApplicationLoader loader;

	public ApplicationsLoader(ApplicationsFinder finder, ApplicationLoader loader) {
		this.finder = finder;
		this.loader = loader;
	}

	public List<Application> loadFrom(String codebaseRoot) {
		return $(applicationsIn(codebaseRoot)).collect(applicationLoader(codebaseRoot));
	}

	private List<String> applicationsIn(String codebaseRoot) {
		return finder.findApplicationsIn(codebaseRoot);
	}

	private Function<String, Application> applicationLoader(final String codebaseRoot) {
		return new Function<String, Application>() {
			public Application apply(String applicationName) {
				return loader.load(codebaseRoot, applicationName);
			}
		};
	}
}