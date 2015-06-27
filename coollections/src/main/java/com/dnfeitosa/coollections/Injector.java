package com.dnfeitosa.coollections;

public interface Injector<O, I> {
	O apply(O injected, I input);
}