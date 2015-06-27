package com.dnfeitosa.coollections.operations;

import com.dnfeitosa.coollections.Function;

public interface Mappable<X, T> {
	<R> X map(Function<T, R> function);
}