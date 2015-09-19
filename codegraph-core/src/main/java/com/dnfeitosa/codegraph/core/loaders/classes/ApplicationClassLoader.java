package com.dnfeitosa.codegraph.core.loaders.classes;

import com.dnfeitosa.codegraph.core.loaders.ClassFileLoader;
import com.dnfeitosa.codegraph.core.loaders.finders.IvyFileFinder;
import com.dnfeitosa.codegraph.core.loaders.finders.code.ClassFile;
import com.dnfeitosa.codegraph.core.descriptors.ModuleDescriptor;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static com.dnfeitosa.codegraph.core.filesystem.Path.join;

@Component
public class ApplicationClassLoader {

	private static final Logger LOGGER = Logger.getLogger(ApplicationClassLoader.class);

	private final IvyFileFinder ivyFileFinder;
	private final ClassFileLoader classFileLoader;

	@Autowired
	public ApplicationClassLoader(IvyFileFinder ivyFileFinder, ClassFileLoader classFileLoader) {
		this.ivyFileFinder = ivyFileFinder;
		this.classFileLoader = classFileLoader;
	}

	public List<ClassFile> loadFor(String codebaseRoot, String applicationName) {
		LOGGER.info(String.format("Loading classes for application '%s'", applicationName));

		List<ClassFile> classFiles = new ArrayList<>(10000);
		for (ModuleDescriptor ivyFile : getModuleDefinitions(codebaseRoot, applicationName)) {
			classFiles.addAll(classFileLoader.loadFor(ivyFile.getName(), ivyFile.getLocation()));
		}
		return classFiles;
	}

	private List<ModuleDescriptor> getModuleDefinitions(String codebaseRoot, String applicationName) {
		return ivyFileFinder.findFilesIn(join(codebaseRoot, applicationName));
	}
}
