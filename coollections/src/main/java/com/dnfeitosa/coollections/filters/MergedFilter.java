package com.dnfeitosa.coollections.filters;

import com.dnfeitosa.coollections.Filter;

public class MergedFilter<T> implements Filter<T> {

    private final Filter<T> filter1;
    private final Filter<T> filter2;

    public MergedFilter(Filter<T> filter1, Filter<T> filter2) {
        this.filter1 = filter1;
        this.filter2 = filter2;
    }

    @Override
    public Boolean matches(T input) {
        return filter1.matches(input) || filter2.matches(input);
    }
}
