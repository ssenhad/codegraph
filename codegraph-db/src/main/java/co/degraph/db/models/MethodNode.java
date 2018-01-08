/**
 * Copyright (C) 2015-2018 Diego Feitosa [dnfeitosa@gmail.com]
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
package co.degraph.db.models;

import java.util.HashSet;
import java.util.Set;

public class MethodNode {

    private Long id;

    private String name;

    private Set<TypeNode> returnTypes;

    private Set<ParameterNode> parameters;

    MethodNode() {
    }

    public MethodNode(String name) {
        this.name = name;
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

    public void setName(String name) {
        this.name = name;
    }

    public void addParameter(ParameterNode parameter) {
        getParameters().add(parameter);
    }

    public Set<ParameterNode> getParameters() {
        if (parameters == null) {
            parameters = new HashSet<>();
        }
        return parameters;
    }

    public void addReturnType(TypeNode returnType) {
        getReturnTypes().add(returnType);
    }

    public Set<TypeNode> getReturnTypes() {
        if (returnTypes == null) {
            returnTypes = new HashSet<>();
        }
        return returnTypes;
    }
}
