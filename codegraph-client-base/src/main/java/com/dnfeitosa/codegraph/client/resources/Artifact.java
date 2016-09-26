package com.dnfeitosa.codegraph.client.resources;

import java.util.ArrayList;
import java.util.List;

public class Artifact {

    private String name;
    private String organization;
    private String version;
    private String extension;
    private String type;
    private List<Artifact> dependencies = new ArrayList<Artifact>();

    public void addDependency(Artifact dependency) {
        dependencies.add(dependency);
    }


}
