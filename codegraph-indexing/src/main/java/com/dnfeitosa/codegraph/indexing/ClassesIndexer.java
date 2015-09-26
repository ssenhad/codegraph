package com.dnfeitosa.codegraph.indexing;

import static com.dnfeitosa.coollections.Coollections.$;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.dnfeitosa.codegraph.core.concurrency.Executor;
import com.dnfeitosa.codegraph.core.concurrency.ResultHandler;
import com.dnfeitosa.codegraph.db.graph.nodes.ClassNode;
import com.dnfeitosa.codegraph.db.graph.nodes.ModuleNode;
import com.dnfeitosa.codegraph.db.graph.repositories.ModuleRepository;
import com.dnfeitosa.codegraph.core.loaders.classes.ApplicationClassLoader;
import com.dnfeitosa.codegraph.core.loaders.finders.ApplicationsFinder;
import com.dnfeitosa.codegraph.core.loaders.finders.code.ClassFile;
import org.apache.log4j.Logger;
import org.neo4j.graphdb.GraphDatabaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.neo4j.support.Neo4jTemplate;
import org.springframework.stereotype.Service;

import com.dnfeitosa.coollections.Coollections;
import com.dnfeitosa.coollections.Function;
import com.dnfeitosa.codegraph.core.concurrency.ParallelProcessor;
import com.dnfeitosa.codegraph.db.graph.repositories.ClassRepository;

@Service
public class ClassesIndexer {

	private static final Logger LOGGER = Logger.getLogger(ClassesIndexer.class);

	private final ApplicationClassLoader applicationClassLoader;
	private final ApplicationsFinder applicationsFinder;
	private final ModuleRepository moduleRepository;
	private final Neo4jTemplate template;

	private final GraphDatabaseService service;

	private final ClassRepository classRepository;

	@Autowired
	public ClassesIndexer(ApplicationsFinder applicationsFinder, ApplicationClassLoader applicationClassLoader,
			ModuleRepository moduleRepository, Neo4jTemplate template,
			@Qualifier("graphDatabaseService") GraphDatabaseService service, ClassRepository classRepository) {
		this.applicationsFinder = applicationsFinder;
		this.applicationClassLoader = applicationClassLoader;
		this.moduleRepository = moduleRepository;
		this.template = template;
		this.service = service;
		this.classRepository = classRepository;
	}

	public void index(final String codebaseRoot) {
//		ModuleNode module2 = new ModuleNode();
//		module2.setName("moduleNode");
//		ArtifactNode artifact = new ArtifactNode();
//		artifact.setName("artifact");
//		module2.setArtifacts(asSet(artifact));
//		moduleRepository.save(module2);
//
//		ClassNode class2 = new ClassNode();
//		class2.setFullName("com.package.Imported");
//		class2.setCanonicalName("ModuleNode:com.package.Imported");
//		class2.setPackageName("com.package");
//		class2.setType("SRC");
//		class2.setName("Imported");
//
//		ModuleNode anotherModule = new ModuleNode();
//		anotherModule.setName("anotherModule");
//		anotherModule.setClassNodes(asSet(class2));
//		moduleRepository.save(anotherModule);
//
//		ClassNode imported = new ClassNode();
//		imported.setFullName("com.package.Imported");
//
//		ClassNode class1 = new ClassNode();
//		class1.setName("Name");
//		class1.setCanonicalName("ModuleNode:com.package.Name");
//		class1.setPackageName("com.package");
//		class1.setType("SRC");
//		class1.setFullName("com.package.Name");
//		class1.setImports(asSet(imported));
//
//
//		ModuleNode moduleNode = new ModuleNode();
//		moduleNode.setName("moduleNode");
//		moduleNode.setClassNodes(asSet(class1));
//
//		moduleRepository.save(moduleNode);
//		template.save(class1);
//		template.save(class2);

		List<String> applications = applicationsFinder.findApplicationsIn(codebaseRoot);

		ParallelProcessor processor = new ParallelProcessor(30, 30);
		ModulesHandler handler = new ModulesHandler();
		fetchModuleClasses(processor, handler, codebaseRoot, applications);
		indexModuleClasses(processor, handler.getModules());
		indexClassImports(processor, handler.getModules());
	}

	private void indexClassImports(ParallelProcessor processor, List<ModuleNode> moduleNodes) {
		final Map<String, Set<String>> classesByModule = getClassesByModule(processor, moduleNodes);

		processor.process(moduleNodes, new Executor<ModuleNode, Void>() {

			@Override
			public Void execute(ModuleNode moduleNode) {
				LOGGER.info(String.format("Resolving class imports for '%s' classes.", moduleNode.getName()));
				List<ModuleNode> dependencies = moduleRepository.dependenciesOf(moduleNode.getName());

				for (ClassNode clazz : moduleNode.getClassNodes()) {
					for (ClassNode import_ : Coollections.notNull(clazz.getImports())) {
						for (ModuleNode dep : dependencies) {
							if (classesByModule.get(dep.getName()).contains(import_.getFullName())) {
								import_.setCanonicalName(dep.getName() + ":" + import_.getFullName());
							}
						}
					}

				}
				classRepository.save(moduleNode.getClassNodes());

				return null;
			}
		});
	}

	private Map<String, Set<String>> getClassesByModule(ParallelProcessor processor, List<ModuleNode> moduleNodes) {
		final Map<String, Set<String>> result = new ConcurrentHashMap<>();
		ResultHandler<ModuleNode, List<ClassNode>> resultHandler = new ResultHandler<ModuleNode, List<ClassNode>>() {

			@Override
			public void handle(ModuleNode input, List<ClassNode> value) {
				result.put(input.getName(), $(value).collect(className()).toSet());
			}

			private Function<ClassNode, String> className() {
				return new Function<ClassNode, String>() {
					@Override
					public String apply(ClassNode input) {
						return input.getFullName();
					}
				};
			}
		};
		Executor<ModuleNode, List<ClassNode>> executor = new Executor<ModuleNode, List<ClassNode>>() {
				@Override
				public List<ClassNode> execute(ModuleNode input) {
					return new ArrayList<ClassNode>(input.getClassNodes());
				}
			};
		processor.process(moduleNodes, executor, resultHandler);
		return result;
	}

	private void indexModuleClasses(ParallelProcessor processor, List<ModuleNode> moduleNodes) {
		processor.process(moduleNodes, new Executor<ModuleNode, Void>() {
			@Override
			public Void execute(ModuleNode moduleNode) {
				LOGGER.info(String.format("Indexing classes of '%s'", moduleNode.getName()));
				moduleRepository.save(moduleNode);
				return null;
			}
		});
	}

	private void fetchModuleClasses(ParallelProcessor processor, ModulesHandler handler, final String codebaseRoot,
			List<String> applications) {
		processor.process(applications, executor(codebaseRoot), handler);
	}

	public static class ModulesHandler implements ResultHandler<String, List<ModuleNode>> {

		private final List<ModuleNode> moduleNodes = Collections.synchronizedList(new ArrayList<ModuleNode>());

		@Override
		public void handle(String input, List<ModuleNode> value) {
			moduleNodes.addAll(value);
		}

		public List<ModuleNode> getModules() {
			return moduleNodes;
		}
	}

	private Executor<String, List<ModuleNode>> executor(final String codebaseRoot) {
		return new Executor<String, List<ModuleNode>>() {

			@Override
			public List<ModuleNode> execute(String application) {
				List<ClassFile> classes = applicationClassLoader.loadFor(codebaseRoot, application);

				Map<String, List<ClassFile>> byModule = $(classes).groupBy(byModule());

				List<ModuleNode> moduleNodes = new ArrayList<>(byModule.size());
				for (Entry<String,List<ClassFile>> entry : byModule.entrySet()) {
					String moduleName = entry.getKey();

					ModuleNode moduleNode = new ModuleNode();
					moduleNode.setName(moduleName);
					moduleNode.setClassNodes(toClasses(entry.getValue(), moduleName));

					moduleNodes.add(moduleNode);
				}

				return moduleNodes;
			}

			private Set<ClassNode> toClasses(List<ClassFile> value, final String moduleName) {
				return $(value).collect(new Function<ClassFile, ClassNode>() {
					@Override
					public ClassNode apply(ClassFile input) {
						ClassNode clazz = new ClassNode();
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

