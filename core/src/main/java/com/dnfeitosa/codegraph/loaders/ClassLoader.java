package com.dnfeitosa.codegraph.loaders;

import java.util.List;
import java.util.Map;

import com.dnfeitosa.codegraph.commandline.Terminal;
import com.dnfeitosa.codegraph.concurrency.Executor;
import com.dnfeitosa.codegraph.concurrency.HashMapResultHandler;
import com.dnfeitosa.codegraph.loaders.classes.ApplicationClassLoader;
import com.dnfeitosa.codegraph.loaders.finders.ApplicationsFinder;
import com.dnfeitosa.codegraph.loaders.finders.ImportsFinder;
import com.dnfeitosa.codegraph.loaders.finders.IvyFileFinder;
import com.dnfeitosa.codegraph.loaders.finders.code.ClassFile;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dnfeitosa.codegraph.concurrency.ParallelProcessor;

@Component
public class ClassLoader {

	private static final Logger LOGGER = Logger.getLogger(ClassLoader.class);

	private final Terminal terminal;
	private final ApplicationsFinder applicationsFinder;
	private final IvyFileFinder ivyFileFinder;
	// private final ClassFileLoader classFileLoader;
//	private final ImportsFinder classImportsFinder;

	private final ApplicationClassLoader applicationClassLoader;

	@Autowired
	public ClassLoader(ApplicationClassLoader applicationClassLoader, Terminal terminal,
			ApplicationsFinder applicationsFinder, IvyFileFinder ivyFileFinder, ClassFileLoader classFileLoader,
			ImportsFinder classImportsFinder
			 ) {
		this.applicationClassLoader = applicationClassLoader;
		this.terminal = terminal;
		this.applicationsFinder = applicationsFinder;
		this.ivyFileFinder = ivyFileFinder;
		// this.classFileLoader = classFileLoader;
//		this.classImportsFinder = classImportsFinder;
	}

	public Map<String, List<ClassFile>> load(final String codebaseRoot) {
		List<String> applications = applicationsFinder.findApplicationsIn(codebaseRoot);

		ParallelProcessor processor = new ParallelProcessor(5, 10);
		HashMapResultHandler<String, List<ClassFile>> handler = new HashMapResultHandler<>();
		processor.process(applications, executor(codebaseRoot), handler);

		return handler.getMap();
/*
		int sum = 0;
		for (Entry<String, List<ClassFile>> entry : results.entrySet()) {
			sum += entry.getValue().size();
		}
		System.out.println(sum);

		// List<String> applications = Arrays.asList("application2", "components",
		// "pacman", "application1");
		// Map<String, List<ClassFile>> classFiles =
		// getClassFiles(getIvyFiles(codebaseRoot, applications));
		Map<String, Map<String, List<ImportResult>>> importsByModule = new HashMap<>();

		Map<String, List<ClassFile>> classFiles = null;

		for (IvyFile ivyFile : getIvyFiles(codebaseRoot, applications)) {
			LOGGER.info(String.format("Fetching classes from '%s'", ivyFile.getModuleName()));
			List<ImportResult> imports = classImportsFinder.findImportsIn(ivyFile.getLocation());
			List<ImportResult> importResults = new ArrayList<ImportResult>();
			for (ImportResult anImport : imports) {
				if (anImport.getImported().needsResolution()) {
					importResults.addAll(resolve(ivyFile.getLocation(), anImport, classFiles));
				} else {
					importResults.add(anImport);
				}
			}
			importsByModule.put(ivyFile.getModuleName(), group(importResults));
		}

		return importsByModule;
 */
	}

	private Executor<String, List<ClassFile>> executor(final String codebaseRoot) {
		Executor<String, List<ClassFile>> executor = new Executor<String, List<ClassFile>>() {
			@Override
			public List<ClassFile> execute(String applicationName) {
				return applicationClassLoader.loadFor(codebaseRoot, applicationName);
			}
		};
		return executor;
	}
/*
	private Runnable loaderFor(final String codebaseRoot, final String applicationName, final Map<String, List<ClassFile>> results) {
		return new Runnable() {
			@Override
			public void run() {
				System.out.println(Thread.currentThread().getName() + " - Loading " + applicationName);
				results.put(applicationName, applicationClassLoader.loadFor(codebaseRoot, applicationName));
				System.out.println(Thread.currentThread().getName() + " - Finished " + applicationName);
			}
		};
	}

	private List<ImportResult> resolve(String location, ImportResult anImport, Map<String, List<ClassFile>> classFiles) {
		Import imported = anImport.getImported();
		if (imported instanceof PackageImport) {
			PackageImport packageImport = (PackageImport) imported;
			List<ClassFile> packageClasses = classFiles.get(packageImport.getPackageName());
			if (packageClasses == null) {
				System.err.println("Unknown ->" + packageImport.getPackageName());
				System.err.println(anImport);
			}
			CoolList<ClassFile> inUse = $(packageClasses).filter(inUseBy(location, anImport.getFileName()));
			return $(inUse).map(toImportResult(anImport.getFileName()));
		}
		if (imported instanceof StaticImport) {
			StaticImport staticImport = (StaticImport) imported;
			return asList(new ImportResult(anImport.getFileName(), new ClassImport(staticImport.getQualifiedName())));
		}
		return Collections.emptyList();
	}

	private Function<ClassFile, ImportResult> toImportResult(final String fileName) {
		return new Function<ClassFile, ImportResult>() {
			@Override
			public ImportResult apply(ClassFile input) {
				return new ImportResult(fileName, new ClassImport(input.getQualifiedName()));
			}
		};
	}

	private Filter<ClassFile> inUseBy(final String location, final String fileName) {
		return new Filter<ClassFile>() {
			@Override
			public Boolean matches(ClassFile input) {
				List<String> result = terminal.execute("grep", input.getName(), Path.join(location, fileName));
				return !result.isEmpty();
			}
		};
	}
 */

	// private Function<ClassFile, String> classNames() {
	// return new Function<ClassFile, String>() {
	// @Override
	// public String apply(ClassFile input) {
	// return input.getName();
	// }
	// };
	// }

//	private Map<String, List<ImportResult>> group(List<ImportResult> importResults) {
//		return $(importResults).groupBy(fileName());
//	}

	// private Map<String, List<ClassFile>> getClassFiles(List<IvyFile>
	// ivyFiles) {
	// List<ClassFile> classFiles = new ArrayList<ClassFile>(10000);
	// for (IvyFile ivyFile : ivyFiles) {
	// classFiles.addAll(classFileLoader.loadFor(ivyFile.getModuleName(),
	// ivyFile.getLocation()));
	// }
	// return $(classFiles).groupBy(packageName());
	// }

	// private Function<ClassFile, String> packageName() {
	// return new Function<ClassFile, String>() {
	// @Override
	// public String apply(ClassFile input) {
	// return input.getPackageName();
	// }
	// };
	// }

//	private List<IvyFile> getIvyFiles(String codebaseRoot, List<String> applications) {
//		List<IvyFile> ivyFiles = new ArrayList<IvyFile>();
//		for (String application : applications) {
//			ivyFiles.addAll(ivyFileFinder.findFilesIn(join(codebaseRoot, application)));
//		}
//		return ivyFiles;
//	}
//
//	private Function<ImportResult, String> fileName() {
//		return new Function<ImportResult, String>() {
//			@Override
//			public String apply(ImportResult input) {
//				return input.getFileName();
//			}
//		};
//	}
}