package com.dnfeitosa.codegraph.web.controllers;

import com.dnfeitosa.codegraph.web.Responses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class EndpointsController {

    private RequestMappingHandlerMapping urlMapping;

    @Autowired
    public EndpointsController(RequestMappingHandlerMapping urlMapping) {
        this.urlMapping = urlMapping;
    }

    @RequestMapping("/endpoints")
    public ResponseEntity<List<String>> endpoints() {
        Set<RequestMappingInfo> infos = urlMapping.getHandlerMethods().keySet();

        List<String> urls = infos.stream()
            .map(this::toUrls)
            .sorted()
            .collect(Collectors.toList());

        return Responses.ok(urls);
    }

    private String toUrls(RequestMappingInfo input) {
        return input.getPatternsCondition().getPatterns().toString();
    }
}