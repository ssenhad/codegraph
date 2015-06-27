package com.dnfeitosa.codegraph.concurrency;

public interface Executor<I, O> {
	O execute(I input);
}