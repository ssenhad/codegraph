package com.dnfeitosa.codegraph.core.concurrency;

public interface Executor<I, O> {
	O execute(I input);
}