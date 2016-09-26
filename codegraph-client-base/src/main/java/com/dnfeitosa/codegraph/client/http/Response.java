package com.dnfeitosa.codegraph.client.http;

import org.apache.http.client.methods.CloseableHttpResponse;

public class Response {
    private CloseableHttpResponse response;

    public Response(CloseableHttpResponse response) {
        this.response = response;
    }
}
