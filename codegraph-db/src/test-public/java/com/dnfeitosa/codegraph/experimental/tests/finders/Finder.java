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

import java.util.Collection;
import java.util.function.Function;

import static java.lang.String.format;
import static org.junit.Assert.fail;

public abstract class Finder<T, R> {

    private final String value;
    private final Function<T, String> getValue;

    public Finder(String value, Function<T, String> getValue) {
        this.value = value;
        this.getValue = getValue;
    }

    public R in(Collection<T> elements) {
        T t = elements.stream()
            .filter(n -> value.equals(getValue.apply(n)))
            .findFirst()
            .orElseGet(() -> {
                fail(format("Element with %s value '%s' was not found", getValue, value));
                return null;
            });
        return createResult(t);
    }

    abstract R createResult(T value);
}
