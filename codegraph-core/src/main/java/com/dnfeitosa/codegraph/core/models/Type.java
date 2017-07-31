/**
 * Copyright (C) 2015-2017 Diego Feitosa [dnfeitosa@gmail.com]
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.dnfeitosa.codegraph.core.models;

import java.util.ArrayList;
import java.util.List;

public class Type {

    private String name;
    private String packageName;
    private String usage;
    private String type;
    private List<Method> methods = new ArrayList<>();
    private List<Field> fields = new ArrayList<>();
    private Type superclass;
    private List<Type> interfaces = new ArrayList<>();

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

    public List<Method> getMethods() {
        return methods;
    }

    public void addMethod(Method method) {
        methods.add(method);
    }

    public void addField(Field field) {
        fields.add(field);
    }

    public List<Field> getFields() {
        return fields;
    }

    public Type getSuperclass() {
        return superclass;
    }

    public List<Type> getInterfaces() {
        return interfaces;
    }

    public void setSuperclass(Type superclass) {
        this.superclass = superclass;
    }

    public void addInterface(Type type) {
        interfaces.add(type);
    }

    public void setInterfaces(List<Type> interfaces) {
        this.interfaces = interfaces;
    }
}
