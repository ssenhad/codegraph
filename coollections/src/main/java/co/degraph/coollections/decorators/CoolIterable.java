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
package co.degraph.coollections.decorators;

import co.degraph.coollections.Function;
import co.degraph.coollections.operations.Collectable;
import co.degraph.coollections.operations.Mappable;
import co.degraph.coollections.operations.Mapper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CoolIterable<T> implements Iterable<T>, Mappable<CoolIterable<?>, T>, Collectable<CoolIterable<?>, T> {
	private final Iterable<T> iterable;

	public CoolIterable(Iterable<T> iterable) {
		this.iterable = iterable;
	}

	@Override
	public Iterator<T> iterator() {
		return iterable.iterator();
	}

	@Override
	public <R> CoolIterable<R> map(Function<T, R> function) {
		Mapper mapper = new Mapper();
		return new CoolIterable<>(mapper.exec(iterable, function));
	}

	@Override
	public <R> CoolIterable<?> collect(Function<T, R> function) {
		return map(function);
	}

	public List<T> toList() {
		List<T> list = new ArrayList<>();
		for (T item : iterable) {
			list.add(item);
		}
		return list;
	}

    public T first() {
        Iterator<T> iterator = iterable.iterator();
        if (iterator.hasNext()) {
            return iterator.next();
        }
        return null;
    }
}
