package com.dnfeitosa.codegraph.db.utils;

import org.springframework.data.neo4j.conversion.Result;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.StreamSupport.stream;

public class ResultUtils {
    public static <T> List<T> toList(Result<T> result) {
        return stream(result.spliterator(), false)
                .collect(Collectors.toList());
    }
}
