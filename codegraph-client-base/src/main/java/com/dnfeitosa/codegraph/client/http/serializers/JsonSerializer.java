package com.dnfeitosa.codegraph.client.http.serializers;

import com.google.gson.Gson;

public class JsonSerializer implements EntitySerializer {

    public String serialize(Object object) {
        return new Gson().toJson(object);
    }
}
