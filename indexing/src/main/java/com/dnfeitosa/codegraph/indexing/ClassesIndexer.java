package com.dnfeitosa.codegraph.indexing;

import static com.dnfeitosa.coollections.Coollections.$;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.dnfeitosa.codegraph.concurrency.Executor;
import com.dnfeitosa.codegraph.concurrency.ResultHandler;
import com.dnfeitosa.codegraph.db.graph.nodes.Class;
import com.dnfeitosa.codegraph.db.graph.repositories.GraphModuleRepository;
import com.dnfeitosa.codegraph.loaders.classes.ApplicationClassLoader;
import com.dnfeitosa.codegraph.loaders.finders.ApplicationsFinder;
import com.dnfeitosa.codegraph.loaders.finders.code.ClassFile;
import org.apache.log4j.Logger;
import org.neo4j.graphdb.GraphDatabaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.neo4j.support.Neo4jTemplate;
import org.springframework.stereotype.Service;

import com.dnfeitosa.coollections.Coollections;
import com.dnfeitosa.coollections.Function;
import com.dnfeitosa.codegraph.concurrency.ParallelProcessor;
import com.dnfeitosa.codegraph.db.graph.nodes.Module;
import com.dnfeitosa.codegraph.db.graph.repositories.GraphClassRepository;

@Service
public class ClassesIndexer {

	private static final Logger LOGGER = Logger.getLogger(ClassesIndexer.class);

	private final ApplicationClassLoader applicationClassLoader;
	private final ApplicationsFinder applicationsFinder;
	private final GraphModuleRepository moduleRepository;
	private final Neo4jTemplate template;

	private final GraphDatabaseService service;

	private final GraphClassRepository classRepository;

	@Autowired
	public ClassesIndexer(ApplicationsFinder applicationsFinder, ApplicationClassLoader applicationClassLoader,
			GraphModuleRepository moduleRepository, Neo4jTemplate template,
			@Qualifier("graphDatabaseService") GraphDatabaseService service, GraphClassRepository classRepository) {
		this.applicationsFinder = applicationsFinder;
		this.applicationClassLoader = applicationClassLoader;
		this.moduleRepository = moduleRepository;
		this.template = template;
		this.service = service;
		this.classRepository = classRepository;
	}

	public void index(final String codebaseRoot) {
//		Module module2 = new Module();
//		module2.setName("module");
//		Artifact artifact = new Artifact();
//		artifact.setName("artifact");
//		module2.setArtifacts(asSet(artifact));
//		moduleRepository.save(module2);
//
//		Class class2 = new Class();
//		class2.setFullName("com.package.Imported");
//		class2.setCanonicalName("Module:com.package.Imported");
//		class2.setPackageName("com.package");
//		class2.setType("SRC");
//		class2.setName("Imported");
//
//		Module anotherModule = new Module();
//		anotherModule.setName("anotherModule");
//		anotherModule.setClasses(asSet(class2));
//		moduleRepository.save(anotherModule);
//
//		Class imported = new Class();
//		imported.setFullName("com.package.Imported");
//
//		Class class1 = new Class();
//		class1.setName("Name");
//		class1.setCanonicalName("Module:com.package.Name");
//		class1.setPackageName("com.package");
//		class1.setType("SRC");
//		class1.setFullName("com.package.Name");
//		class1.setImports(asSet(imported));
//
//
//		Module module = new Module();
//		module.setName("module");
//		module.setClasses(asSet(class1));
//
//		moduleRepository.save(module);
//		template.save(class1);
//		template.save(class2);

		List<String> applications = applicationsFinder.findApplicationsIn(codebaseRoot);

		ParallelProcessor processor = new ParallelProcessor(30, 30);
		ModulesHandler handler = new ModulesHandler();
		fetchModuleClasses(processor, handler, codebaseRoot, applications);
		indexModuleClasses(processor, handler.getModules());
		indexClassImports(processor, handler.getModules());
	}

	private void indexClassImports(ParallelProcessor processor, List<Module> modules) {
		final Map<String, Set<String>> classesByModule = getClassesByModule(processor, modules);

		processor.process(modules, new Executor<Module, Void>() {

			@Override
			public Void execute(Module module) {
				LOGGER.info(String.format("Resolving class imports for '%s' classes.", module.getName()));
				List<Module> dependencies = moduleRepository.dependenciesOf(module.getName());

				for (com.dnfeitosa.codegraph.db.graph.nodes.Class clazz : module.getClasses()) {
					for (Class import_ : Coollections.notNull(clazz.getImports())) {
						for (Module dep : dependencies) {
							if (classesByModule.get(dep.getName()).contains(import_.getFullName())) {
								import_.setCanonicalName(dep.getName() + ":" + import_.getFullName());
							}
						}
					}

				}
				classRepository.save(module.getClasses());

				return null;
			}
		});
	}

	private Map<String, Set<String>> getClassesByModule(ParallelProcessor processor, List<Module> modules) {
		final Map<String, Set<String>> result = new ConcurrentHashMap<>();
		ResultHandler<Module, List<Class>> resultHandler = new ResultHandler<Module, List<Class>>() {

			@Override
			public void handle(Module input, List<Class> value) {
				result.put(input.getName(), $(value).collect(className()).toSet());
			}

			private Function<Class, String> className() {
				return new Function<Class, String>() {
					@Override
					public String apply(Class input) {
						return input.getFullName();
					}
				};
			}
		};
		Executor<Module, List<Class>> executor = new Executor<Module, List<Class>>() {
				@Override
				public List<Class> execute(Module input) {
					return new ArrayList<Class>(input.getClasses());
				}
			};
		processor.process(modules, executor, resultHandler);
		return result;
	}

	private void indexModuleClasses(ParallelProcessor processor, List<Module> modules) {
		processor.process(modules, new Executor<Module, Void>() {
			@Override
			public Void execute(Module module) {
				LOGGER.info(String.format("Indexing classes of '%s'", module.getName()));
				moduleRepository.save(module);
				return null;
			}
		});
	}

	private void fetchModuleClasses(ParallelProcessor processor, ModulesHandler handler, final String codebaseRoot,
			List<String> applications) {
		processor.process(applications, executor(codebaseRoot), handler);
	}

	public static class ModulesHandler implements ResultHandler<String, List<Module>> {

		private final List<Module> modules = Collections.synchronizedList(new ArrayList<Module>());

		@Override
		public void handle(String input, List<Module> value) {
			modules.addAll(value);
		}

		public List<Module> getModules() {
			return modules;
		}
	}

	private Executor<String, List<Module>> executor(final String codebaseRoot) {
		return new Executor<String, List<Module>>() {

			@Override
			public List<Module> execute(String application) {
				List<ClassFile> classes = applicationClassLoader.loadFor(codebaseRoot, application);

				Map<String, List<ClassFile>> byModule = $(classes).groupBy(byModule());

				List<Module> modules = new ArrayList<>(byModule.size());
				for (Entry<String,List<ClassFile>> entry : byModule.entrySet()) {
					String moduleName = entry.getKey();

					Module module = new Module();
					module.setName(moduleName);
					module.setClasses(toClasses(entry.getValue(), moduleName));

					modules.add(module);
				}

				return modules;
			}

			private Set<Class> toClasses(List<ClassFile> value, final String moduleName) {
				return $(value).collect(new Function<ClassFile, Class>() {
					@Override
					public Class apply(ClassFile input) {
						Class clazz = new Class();
						clazz.setCanonicalName(moduleName + ":" + input.getQualifiedName());
						clazz.setName(input.getName());
						clazz.setFullName(input.getQualifiedName());
						clazz.setPackageName(input.getPackageName());
						clazz.setType(input.getFileType().toString());
						return clazz;
					}

				}).toSet();
			}

			private Function<ClassFile, String> byModule() {
				return new Function<ClassFile, String>() {
					@Override
					public String apply(ClassFile input) {
						return input.getModuleName();
					}
				};
			}
		};
	}

}

