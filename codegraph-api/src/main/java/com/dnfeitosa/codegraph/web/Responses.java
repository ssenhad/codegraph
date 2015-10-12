package com.dnfeitosa.codegraph.web;

import org.springframework.http.ResponseEntity;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;

public class Responses {
	public static <T> ResponseEntity<T> notFound() {
		return new ResponseEntity<>(NOT_FOUND);
	}

	public static <T> ResponseEntity<T> ok(T resource) {
		return new ResponseEntity<>(resource, OK);
	}
}