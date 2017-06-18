package com.dnfeitosa.codegraph.db.nodes;

import java.util.HashSet;
import java.util.Set;

public class TypeNode {

    private Long id;

    private String qualifiedName;
    private String name;

    private String packageName;
    private String usage;
    private String type;

    private Set<TypeNode> referencedTypes;

    private Set<FieldNode> fields;

    private Set<MethodNode> methods;

    private TypeNode superclass;

    private Set<TypeNode> interfaces;

    TypeNode() {
    }

    public TypeNode(String name) {
        this(name, null);
    }

    public TypeNode(String name, String packageName) {
        this.name = name;
        this.packageName = packageName;
        this.qualifiedName = packageName != null ? packageName + "." + name : name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getQualifiedName() {
        return qualifiedName;
    }

    public String getPackageName() {
        return packageName;
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

    public void addMethod(MethodNode method) {
        getMethods().add(method);
    }

    public Set<MethodNode> getMethods() {
        if (methods == null) {
            methods = new HashSet<>();
        }
        return methods;
    }

    public void addField(FieldNode field) {
        getFields().add(field);
    }

    public Set<FieldNode> getFields() {
        if (fields == null) {
            fields = new HashSet<>();
        }
        return fields;
    }

    public void addInterface(TypeNode interface_) {
        getInterfaces().add(interface_);
    }

    public Set<TypeNode> getInterfaces() {
        if (interfaces == null) {
            interfaces = new HashSet<>();
        }
        return interfaces;
    }

    public void setSuperclass(TypeNode superclass) {
        this.superclass = superclass;
    }

    public TypeNode getSuperclass() {
        return superclass;
    }
}
