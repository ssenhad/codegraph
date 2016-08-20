package com.dnfeitosa.codegraph.core.models;

public class Artifact {

    private String name;
    private String type;
    private String extension;
    private Version version;

    public Artifact(String name, String type, String extension, Version version) {
        this.name = name;
        this.type = type;
        this.extension = extension;
        this.version = version;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getExtension() {
        return extension;
    }

    public Version getVersion() {
        return version;
    }
}
