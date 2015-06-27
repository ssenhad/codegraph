package com.dnfeitosa.coollections.operations;

import com.dnfeitosa.coollections.Function;

public interface Collectable<X, T> {
	<R> X collect(Function<T, R> function);
}