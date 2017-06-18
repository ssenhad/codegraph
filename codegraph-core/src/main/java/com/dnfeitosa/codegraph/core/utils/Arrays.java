package com.dnfeitosa.codegraph.core.utils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Arrays {

    public static <T> List<T> asList(T... items) {
        return java.util.Arrays.asList(items);
    }

    public static <T> Set<T> asSet(T... items) {
        return new HashSet<>(asList(items));
    }
}
