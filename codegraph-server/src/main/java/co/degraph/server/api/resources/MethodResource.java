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

import java.util.ArrayList;
import java.util.List;

public class MethodResource implements LinkableResource {

    private String name;
    private List<TypeResource> returnTypes = new ArrayList<>();
    private List<ParameterResource> parameters = new ArrayList<>();


    MethodResource() {
    }

    public MethodResource(String name) {
        this.name = name;
    }

    @Override
    public String getUri() {
        return null;
    }

    public String getName() {
        return name;
    }

    public void setReturnTypes(List<TypeResource> returnTypes) {
        this.returnTypes = returnTypes;
    }

    public void setParameters(List<ParameterResource> parameters) {
        this.parameters = parameters;
    }

    public List<ParameterResource> getParameters() {
        return parameters;
    }

    public List<TypeResource> getReturnTypes() {
        return returnTypes;
    }
}
