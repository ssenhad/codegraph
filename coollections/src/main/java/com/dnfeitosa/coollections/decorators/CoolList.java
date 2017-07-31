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
package com.dnfeitosa.coollections.decorators;

import com.dnfeitosa.coollections.Filter;
import com.dnfeitosa.coollections.Function;
import com.dnfeitosa.coollections.Injector;
import com.dnfeitosa.coollections.operations.Mappable;
import com.dnfeitosa.coollections.operations.Mapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;

public class CoolList<T> implements List<T>, Mappable<CoolList<?>, T> {

	private final List<T> list;

	public CoolList(List<T> list) {
		this.list = list == null ? Collections.<T> emptyList() : list;
	}

	@Override
	public int size() {
		return list.size();
	}

	@Override
	public boolean isEmpty() {
		return list.isEmpty();
	}

	@Override
	public boolean contains(Object o) {
		return list.contains(o);
	}

	@Override
	public Iterator<T> iterator() {
		return list.iterator();
	}

	@Override
	public Object[] toArray() {
		return list.toArray();
	}

	@SuppressWarnings("hiding")
	@Override
	public <T> T[] toArray(T[] ts) {
		return list.toArray(ts);
	}

	@Override
	public boolean add(T t) {
		return list.add(t);
	}

	@Override
	public boolean remove(Object o) {
		return list.remove(o);
	}

	@Override
	public boolean containsAll(Collection<?> objects) {
		return list.containsAll(objects);
	}

	@Override
	public boolean addAll(Collection<? extends T> ts) {
		return list.addAll(ts);
	}

	@Override
	public boolean addAll(int i, Collection<? extends T> ts) {
		return list.addAll(i, ts);
	}

	@Override
	public boolean removeAll(Collection<?> objects) {
		return list.removeAll(objects);
	}

	@Override
	public boolean retainAll(Collection<?> objects) {
		return list.retainAll(objects);
	}

	@Override
	public void clear() {
		list.clear();
	}

	@Override
	public boolean equals(Object o) {
		return list.equals(o);
	}

	@Override
	public int hashCode() {
		return list.hashCode();
	}

	@Override
	public T get(int i) {
		return list.get(i);
	}

	@Override
	public T set(int i, T t) {
		return list.set(i, t);
	}

	@Override
	public void add(int i, T t) {
		list.add(i, t);
	}

	@Override
	public T remove(int i) {
		return list.remove(i);
	}

	@Override
	public int indexOf(Object o) {
		return list.indexOf(o);
	}

	@Override
	public int lastIndexOf(Object o) {
		return list.lastIndexOf(o);
	}

	@Override
	public ListIterator<T> listIterator() {
		return list.listIterator();
	}

	@Override
	public ListIterator<T> listIterator(int i) {
		return list.listIterator(i);
	}

	@Override
	public List<T> subList(int i, int i1) {
		return list.subList(i, i1);
	}

	@Override
	public <R> CoolList<R> map(Function<T, R> function) {
		Mapper mapper = new Mapper(list.size());
		return new CoolList<R>(mapper.exec(list, function));
	}

	public <R> CoolList<R> collect(Function<T, R> function) {
		return map(function);
	}

	public CoolList<T> filter(Filter<T> filter) {
		List<T> results = new ArrayList<T>(list.size());
		for (T value : list) {
			if (filter.matches(value)) {
				results.add(value);
			}
		}
		return new CoolList<>(results);
	}

	public T find(Filter<T> filter) {
		for (T value : list) {
			if (filter.matches(value)) {
				return value;
			}
		}
		return null;
	}

	public <I> I inject(I injected, Injector<I, T> injector) {
		for (T value : list) {
			injector.apply(injected, value);
		}
		return injected;
	}

	public <K> Map<K, T> mapBy(Function<T, K> mapper) {
		Map<K, T> map = new HashMap<>(list.size());
		for (T value : list) {
			map.put(mapper.apply(value), value);
		}
		return map;
	}

	public Set<T> toSet() {
		return inject(new HashSet<T>(), new Injector<Set<T>, T>() {
			@Override
			public Set<T> apply(Set<T> types, T type) {
				types.add(type);
				return types;
			}
		});
	}

	public <V extends Comparable<? super V>> CoolList<T> sortBy(final Function<T, V> function) {
		List<T> copy = new ArrayList<T>(list);
		Collections.sort(copy, (a, b) -> function.apply(a).compareTo(function.apply(b)));
		return new CoolList<T>(copy);
	}

	@Override
	public String toString() {
		return list.toString();
	}

	public List<T> toList() {
		return new ArrayList<>(list);
	}

	public <K> Map<K, List<T>> groupBy(Function<T, K> function) {
		Map<K, List<T>> map = new HashMap<>();
		for (T t : list) {
			K key = function.apply(t);
			if (!map.containsKey(key)) {
				map.put(key, new ArrayList<T>());
			}
			map.get(key).add(t);
		}
		return map;
	}

	public Set<T> unique() {
		Set<T> set = new HashSet<>();
		for (T t : list) {
			set.add(t);
		}
		return set;
	}
}
