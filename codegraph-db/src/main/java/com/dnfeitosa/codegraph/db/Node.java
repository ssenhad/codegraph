package com.dnfeitosa.codegraph.db;

public class Node {

    public static String id(String organization, String name, String version) {
        return String.format("%s:%s:%s", organization, name, version);
    }
}
