package com.dnfeitosa.codegraph.gradle.plugin.utils;

import java.util.HashSet;
import java.util.Set;

public class ArrayUtils {
    public static <T> Set<T> asSet(T... values) {
        Set<T> set = new HashSet<T>();
        for (T value : values) {
            set.add(value);
        }
        return set;
    }
}
