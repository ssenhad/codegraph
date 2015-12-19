package com.dnfeitosa.coollections;

import java.util.stream.Stream;

import static java.util.stream.Stream.empty;

public class Streams {

    public static <T> Stream<T> concat(Stream<T>... streams) {
        return Stream.of(streams)
                .reduce(empty(), Stream::concat);
    }
}
