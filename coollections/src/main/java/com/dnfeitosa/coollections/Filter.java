package com.dnfeitosa.coollections;

public interface Filter<I> {

	Boolean matches(I input);

    static <T> Filter<T> empty() {
        return input -> false;
    }
}
