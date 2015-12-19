package com.dnfeitosa.codegraph.db.graph.nodes.builders;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toSet;

public class Builders {
    private static Map<String, ApplicationNodeBuilder> applications = new HashMap<>();
    private static Map<String, ModuleNodeBuilder> modules = new HashMap<>();

    public static ModuleNodeBuilder module(String name) {
        if (!modules.containsKey(name)) {
            modules.put(name, new ModuleNodeBuilder(name));
        }
        return modules.get(name);
    }

    public static ModuleNodeBuilders modules(String... names) {
        return new ModuleNodeBuilders(Stream.of(names)
                .map(Builders::module)
                .collect(toSet()));
    }

    public static ApplicationNodeBuilder application(String name) {
        if (!applications.containsKey(name)) {
            applications.put(name, new ApplicationNodeBuilder(name));
        }
        return applications.get(name);
    }

    public static ApplicationNodeBuilders applications(String... names) {
        Set<ApplicationNodeBuilder> builders = Stream.of(names)
                .map(Builders::application)
                .collect(toSet());

        return new ApplicationNodeBuilders(builders);
    }
}
