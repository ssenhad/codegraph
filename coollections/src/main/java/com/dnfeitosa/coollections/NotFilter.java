package com.dnfeitosa.coollections;

public class NotFilter<T> implements Filter<T> {

	private final Filter<T> filter;

	public NotFilter(Filter<T> filter) {
		this.filter = filter;
	}

	@Override
	public Boolean matches(T input) {
		return !filter.matches(input);
	}

	public static <T> Filter<T> not(Filter<T> filter) {
		return new NotFilter<T>(filter);
	}
}