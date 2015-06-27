package com.dnfeitosa.coollections.decorators;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.dnfeitosa.coollections.Function;
import com.dnfeitosa.coollections.operations.Collectable;
import com.dnfeitosa.coollections.operations.Mappable;
import com.dnfeitosa.coollections.operations.Mapper;

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
}