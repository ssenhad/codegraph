package com.dnfeitosa.coollections.operations;

import java.util.ArrayList;
import java.util.List;

import com.dnfeitosa.coollections.Function;

public class Mapper {

	private final int resultSize;

	public Mapper() {
		this(10);
	}

	public Mapper(int resultSize) {
		this.resultSize = resultSize;
	}

	public <T, R> List<R> exec(Iterable<T> values, Function<T, R> function) {
		List<R> results = new ArrayList<R>(resultSize);
		for (T value : values) {
			results.add(function.apply(value));
		}
		return results;
	}
}
