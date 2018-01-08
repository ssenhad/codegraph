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
import java.util.Iterator;
import java.util.List;

public class TypesResource implements LinkableResource, Iterable<TypeResource> {

    private List<TypeResource> types = new ArrayList<>();;
    private ArtifactResource artifactResource;

    public TypesResource() {
    }

    public TypesResource(ArtifactResource artifactResource) {
        this.artifactResource = artifactResource;
    }

    public void add(TypeResource type) {
        getTypes().add(type);
    }

    public List<TypeResource> getTypes() {
        return types;
    }

    @Override
    public String getUri() {
        return null;
    }

    @Override
    public Iterator<TypeResource> iterator() {
        return types.iterator();
    }
}
