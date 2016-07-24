package com.dnfeitosa.codegraph.api.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;

@EnableAutoConfiguration
@ComponentScan("com.dnfeitosa.codegraph.*")
@ImportResource({ "classpath:/codegraph-db.xml" })
public class CodegraphApi {

    public static void main(String[] args) {
        SpringApplication.run(CodegraphApi.class, args);
    }
}
