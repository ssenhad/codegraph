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
package co.degraph.server.api.resources;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class TypeResource implements LinkableResource {

    private String name;

    @JsonProperty("package")
    private String packageName;
    private String type;
    private String usage;

    private List<MethodResource> methods = new ArrayList<>();
    private List<FieldResource> fields = new ArrayList<>();
    private TypeResource superclass;
    private List<TypeResource> interfaces = new ArrayList<>();

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUsage() {
        return usage;
    }

    public void setUsage(String usage) {
        this.usage = usage;
    }

    @Override
    public String getUri() {
        return null;
    }

    public void setMethods(List<MethodResource> methods) {
        this.methods = methods;
    }

    public void setFields(List<FieldResource> fields) {
        this.fields = fields;
    }

    public List<MethodResource> getMethods() {
        return methods;
    }

    public List<FieldResource> getFields() {
        return fields;
    }

    public TypeResource getSuperclass() {
        return superclass;
    }

    public List<TypeResource> getInterfaces() {
        return interfaces;
    }

    public void setSuperclass(TypeResource superclass) {
        this.superclass = superclass;
    }

    public void setInterfaces(List<TypeResource> interfaces) {
        this.interfaces = interfaces;
    }
}
