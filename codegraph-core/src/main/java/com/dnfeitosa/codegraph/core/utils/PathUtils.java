package com.dnfeitosa.codegraph.core.utils;

import org.apache.commons.lang.StringUtils;

public class PathUtils {
    public static String join(String... paths) {
        return StringUtils.join(paths, "/");
    }
}
