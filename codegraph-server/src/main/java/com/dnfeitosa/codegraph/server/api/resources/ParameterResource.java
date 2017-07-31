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
package com.dnfeitosa.codegraph.server.api.resources;

public class ParameterResource implements LinkableResource {

    private TypeResource type;
    private Integer order;

    ParameterResource() {
    }

    public ParameterResource(TypeResource type, Integer order) {
        this.type = type;
        this.order = order;
    }

    public TypeResource getType() {
        return type;
    }

    public Integer getOrder() {
        return order;
    }

    @Override
    public String getUri() {
        return null;
    }
}
