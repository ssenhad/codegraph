package com.dnfeitosa.codegraph.db.nodes;


import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.HashSet;
import java.util.Set;

@NodeEntity(label = "Type")
public class TypeNode {

    @GraphId
    private Long id;

    private String qualifiedName;
    private String name;
    private String packageName;
    private String usage;
    private String type;

    @Relationship(direction = Relationship.OUTGOING, type = "USES")
    private Set<TypeNode> referencedTypes;

    @Relationship(direction = Relationship.OUTGOING, type = "DECLARES")
    private Set<FieldNode> fields;

    @Relationship(direction = Relationship.OUTGOING, type = "DECLARES")
    private Set<MethodNode> methods;

    TypeNode() {
    }

    public TypeNode(String name, String packageName) {
        this.name = name;
        this.packageName = packageName;
        this.qualifiedName = packageName + "." + name;
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
}
