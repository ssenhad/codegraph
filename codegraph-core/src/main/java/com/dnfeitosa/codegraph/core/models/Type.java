package com.dnfeitosa.codegraph.core.models;

public class Type {

    private String name;
    private String packageName;
    private String usage;
    private String type;

    public Type(String name, String packageName, String usage, String type) {
        this.name = name;
        this.packageName = packageName;
        this.usage = usage;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getUsage() {
        return usage;
    }

    public void setUsage(String usage) {
        this.usage = usage;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
