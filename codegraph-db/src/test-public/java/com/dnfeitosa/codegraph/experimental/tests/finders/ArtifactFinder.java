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
package com.dnfeitosa.codegraph.experimental.tests.finders;

import com.dnfeitosa.codegraph.core.models.Artifact;

import java.util.function.Function;

public class ArtifactFinder extends Finder<Artifact, ArtifactResult> {

    public ArtifactFinder(String value, Function<Artifact, String> getValue) {
        super(value, getValue);
    }

    @Override
    public ArtifactResult createResult(Artifact value) {
        return new ArtifactResult(value);
    }
}