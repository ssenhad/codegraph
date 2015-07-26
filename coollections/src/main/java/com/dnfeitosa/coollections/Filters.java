package com.dnfeitosa.coollections;

import com.dnfeitosa.coollections.filters.MergedFilter;

public class Filters {

    public static <T> Filter<T> merge(Filter<T> filter1, Filter<T> filter2) {
        return new MergedFilter<>(filter1, filter2);
    }
}
