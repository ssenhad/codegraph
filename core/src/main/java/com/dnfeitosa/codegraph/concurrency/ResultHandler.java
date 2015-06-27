package com.dnfeitosa.codegraph.concurrency;

public interface ResultHandler<I, V> {
	void handle(I input, V value);
}