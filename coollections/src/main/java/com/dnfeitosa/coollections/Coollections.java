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
package com.dnfeitosa.coollections;

import com.dnfeitosa.coollections.decorators.CoolIterable;
import com.dnfeitosa.coollections.decorators.CoolList;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.Arrays.asList;

public class Coollections {

	public static <T> CoolList<T> $(List<T> list) {
		return new CoolList<T>(list);
	}

	public static <T> CoolIterable<T> $(Iterable<T> iterable) {
		return new CoolIterable<T>(iterable);
	}

	public static <T> Set<T> notNull(Set<T> collection) {
		if (collection == null) {
			return java.util.Collections.emptySet();
		}
		return collection;
	}

	public static <T> List<T> notNull(List<T> collection) {
		if (collection == null) {
			return java.util.Collections.emptyList();
		}
		return collection;
	}


	public static <T> Set<T> asSet(T... values) {
		return new HashSet<>(asList(values));
	}
}
