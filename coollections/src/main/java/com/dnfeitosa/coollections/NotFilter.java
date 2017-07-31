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

public class NotFilter<T> implements Filter<T> {

	private final Filter<T> filter;

	public NotFilter(Filter<T> filter) {
		this.filter = filter;
	}

	@Override
	public Boolean matches(T input) {
		return !filter.matches(input);
	}

	public static <T> Filter<T> not(Filter<T> filter) {
		return new NotFilter<T>(filter);
	}
}