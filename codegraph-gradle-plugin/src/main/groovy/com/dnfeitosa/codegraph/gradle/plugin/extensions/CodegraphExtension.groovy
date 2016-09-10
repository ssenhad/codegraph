package com.dnfeitosa.codegraph.gradle.plugin.extensions

public class CodegraphExtension {

    private String url

    void setUrl(String url) {
        this.url = url;
    }

    String getUrl() {
        return url;
    }
}
