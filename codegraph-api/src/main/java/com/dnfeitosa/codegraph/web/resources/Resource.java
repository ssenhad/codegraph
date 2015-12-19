package com.dnfeitosa.codegraph.web.resources;

import com.fasterxml.jackson.annotation.JsonInclude;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@JsonInclude(NON_NULL)
public interface Resource {

    String getUri();

}
