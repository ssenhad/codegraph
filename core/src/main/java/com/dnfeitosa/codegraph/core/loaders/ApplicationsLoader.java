package com.dnfeitosa.codegraph.core.loaders;

import com.dnfeitosa.codegraph.core.loaders.finders.ApplicationsFinder;
import com.dnfeitosa.codegraph.core.model.Application;
import com.dnfeitosa.coollections.Function;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.dnfeitosa.coollections.Coollections.$;

@Component
public class ApplicationsLoader {

	private ApplicationsFinder finder;
	private ApplicationLoader loader;

    @Autowired
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
        return null;
//		return applicationName -> loader.load(codebaseRoot, applicationName);
	}
}