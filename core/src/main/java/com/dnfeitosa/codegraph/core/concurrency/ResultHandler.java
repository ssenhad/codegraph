package com.dnfeitosa.codegraph.core.concurrency;

public interface ResultHandler<I, V> {
	void handle(I input, V value);
}