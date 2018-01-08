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
package co.degraph.core.models;

import java.util.ArrayList;
import java.util.List;

public class Method {

    private final String name;
    private final List<Parameter> parameters;
    private final List<Type> returnTypes;

    public Method(String name, List<Parameter> parameters, List<Type> returnTypes) {
        this.name = name;
        this.parameters = parameters;
        this.returnTypes = returnTypes;
    }

    public String getName() {
        return name;
    }

    public List<Parameter> getParameters() {
        return new ArrayList<>(parameters);
    }

    public List<Type> getReturnTypes() {
        return returnTypes;
    }
}
