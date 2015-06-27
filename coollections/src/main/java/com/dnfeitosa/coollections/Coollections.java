package com.dnfeitosa.coollections;

import static java.util.Arrays.asList;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.dnfeitosa.coollections.decorators.CoolIterable;
import com.dnfeitosa.coollections.decorators.CoolList;

public class Coollections {

	public static <T> CoolList<T> $(List<T> list) {
		return new CoolList<T>(list);
	}

	public static <T> CoolIterable<T> $(Iterable<T> iterable) {
		return new CoolIterable<T>(iterable);
	}

	public static <T> Set<T> notNull(Set<T> collection) {
		if (collection == null) {
			return java.util.Collections.emptySet();
		}
		return collection;
	}

	public static <T> List<T> notNull(List<T> collection) {
		if (collection == null) {
			return java.util.Collections.emptyList();
		}
		return collection;
	}

	@SuppressWarnings("unchecked")
	public static <T> Set<T> asSet(T... values) {
		return new HashSet<T>(asList(values));
	}
}
