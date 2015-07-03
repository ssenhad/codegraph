package com.dnfeitosa.codegraph.core.loaders;

import static com.dnfeitosa.coollections.Coollections.$;
import static com.dnfeitosa.coollections.Coollections.notNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.dnfeitosa.codegraph.core.loaders.finders.ImportsFinder;
import com.dnfeitosa.codegraph.core.loaders.finders.JavaFileFinder;
import com.dnfeitosa.codegraph.core.loaders.finders.UsedClassesFinder;
import com.dnfeitosa.codegraph.core.loaders.finders.code.ClassFile;
import com.dnfeitosa.codegraph.core.loaders.finders.code.Import;
import com.dnfeitosa.codegraph.core.loaders.finders.code.ImportResult;
import com.dnfeitosa.codegraph.core.loaders.finders.code.UsageResult;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dnfeitosa.coollections.Function;
import com.dnfeitosa.coollections.Injector;
import com.dnfeitosa.coollections.decorators.CoolList;

@Component
public class ClassFileLoader {

	private static final Logger LOGGER = Logger.getLogger(ClassFileLoader.class);

	private final JavaFileFinder fileFinder;
	private final ImportsFinder importsFinder;
	private final UsedClassesFinder usedClassesFinder;

	@Autowired
	public ClassFileLoader(JavaFileFinder fileFinder, ImportsFinder importsFilder, UsedClassesFinder usedClassesFinder) {
		this.fileFinder = fileFinder;
		this.importsFinder = importsFilder;
		this.usedClassesFinder = usedClassesFinder;
	}

	public List<ClassFile> loadFor(String moduleName, String moduleLocation) {
		LOGGER.info(String.format("Loading classes for module '%s'", moduleLocation));

		List<ClassFile> files = findFiles(moduleName, moduleLocation);
		List<ImportResult> imports = findImports(moduleLocation);
		Set<UsageResult> usedClasses = findUsedClasses(moduleLocation);

		Map<String, List<String>> filesByPackage = $(files).inject(new HashMap<String, List<String>>(), classNameByPackage());
		Map<String, List<ImportResult>> importsByFile = $(imports).groupBy(fileName());
		Map<String, List<UsageResult>> usedClassesByFile = $(new ArrayList<>(usedClasses)).groupBy(_fileName());

		for (ClassFile file : files) {
			String filePath = file.getPath();
			assignImports(file, importsByFile.get(filePath), usedClassesByFile.get(filePath), filesByPackage.get(file.getPackageName()));
		}

		return files;
	}

	private void assignImports(ClassFile file, List<ImportResult> imports, List<UsageResult> usedClasses,
			List<String> packageClasses) {
		if (imports != null) {
			for (ImportResult importResult : imports) {
				file.addImport(importResult.getImported());
			}
		}
		for (UsageResult usageResult : notNull(usedClasses)) {
			if (usageResult.getClassName().equals(file.getName())) {
				continue;
			}
			if (packageClasses.contains(usageResult.getClassName())) {
				file.addImport(new Import(file.getPackageName() + "." + usageResult.getClassName()));
			}
		}
	}

	private Injector<Map<String, List<String>>, ClassFile> classNameByPackage() {
		return new Injector<Map<String,List<String>>, ClassFile>() {

			@Override
			public Map<String, List<String>> apply(Map<String, List<String>> injected, ClassFile classFile) {
				String packageName = classFile.getPackageName();
				if (!injected.containsKey(packageName)) {
					injected.put(packageName, new ArrayList<String>());
				}
				injected.get(packageName).add(classFile.getName());
				return injected;
			}
		};
	}

	private Set<UsageResult> findUsedClasses(String moduleLocation) {
		return usedClassesFinder.findUsedClassIn(moduleLocation);
	}

	private List<ImportResult> findImports(String moduleLocation) {
		return importsFinder.findImportsIn(moduleLocation);
	}

	private CoolList<ClassFile> findFiles(String moduleName, String moduleLocation) {
		return $(findFilesIn(moduleLocation)).map(toClassFile(moduleName));
	}

	private Function<UsageResult, String> _fileName() {
		return new Function<UsageResult, String>() {
			@Override
			public String apply(UsageResult input) {
				return input.getFileName();
			}
		};
	}

	private Function<ImportResult, String> fileName() {
		return new Function<ImportResult, String>() {
			@Override
			public String apply(ImportResult input) {
				return input.getFileName();
			}
		};
	}

	private Function<String, ClassFile> toClassFile(String moduleName) {
		return new PathToClassFileFunction(moduleName);
	}

	private List<String> findFilesIn(String location) {
		return fileFinder.findFilesIn(location);
	}
}
